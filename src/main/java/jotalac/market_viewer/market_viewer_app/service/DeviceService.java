package jotalac.market_viewer.market_viewer_app.service;

import jakarta.transaction.Transactional;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateResponse;
import jotalac.market_viewer.market_viewer_app.dto.device.ReorderScreensRequest;
import jotalac.market_viewer.market_viewer_app.dto.screen.CryptoScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDtoMapper;
import jotalac.market_viewer.market_viewer_app.dto.screen.StockScreenDto;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import jotalac.market_viewer.market_viewer_app.exception.AlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.exception.device.DeviceScreenLimitExceeded;
import jotalac.market_viewer.market_viewer_app.exception.device.ScreenReorderException;
import jotalac.market_viewer.market_viewer_app.exception.screen.ScreenDoesntBelongToDeviceException;
import jotalac.market_viewer.market_viewer_app.exception.user.MissingApiKey;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.repository.DeviceRepository;
import jotalac.market_viewer.market_viewer_app.repository.ScreenRepository;
import jotalac.market_viewer.market_viewer_app.repository.UserRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static jotalac.market_viewer.market_viewer_app.config.Constants.DEVICE_MAX_SCREENS;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final ScreenRepository screenRepository;
    private final UserService userService;
    private final ScreenDtoMapper screenDtoMapper;
    private final ApiKeyRepository apiKeyRepository;
    private final CryptoDataProvider cryptoDataProvider;
    private final StockDataProvider stockDataProvider;

    private boolean userHasRequiredApiKeys(User user, ScreenType screenType) {
        return switch (screenType) {
            case CRYPTO -> apiKeyRepository.existsByEndpointAndUser(ApiKeyProvider.COINGECKO, user);
            case STOCK -> apiKeyRepository.existsByEndpointAndUser(ApiKeyProvider.TWELVE_DATA, user);
            case AI_TEXT -> apiKeyRepository.existsByEndpointAndUser(ApiKeyProvider.GEMINI, user);
            default -> true;
        };
    }

    private void validateAssetName(ScreenDto screenDto, User user) {
        if (screenDto instanceof CryptoScreenDto cryptoScreenDto) {
            if (cryptoScreenDto.getAssetName() == null) {return;}
            String apiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.COINGECKO, user)
                    .orElseThrow(() -> new MissingApiKey("Missing API key for crypto data"))
                    .getValue();

            cryptoDataProvider.validateCoinName(cryptoScreenDto.getAssetName().toLowerCase(), apiKey);
            return;
        }

        if (screenDto instanceof StockScreenDto stockScreenDto) {
            if (stockScreenDto.getSymbol() == null) {return;}
            String apiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.TWELVE_DATA, user)
                    .orElseThrow(() -> new MissingApiKey("Missing API key for stock data"))
                    .getValue();

            stockDataProvider.validateAssetSymbol(stockScreenDto.getSymbol(), apiKey);
        }
    }

    @Transactional
    public DeviceCreateResponse createDevice(DeviceCreateRequest deviceCreateRequest, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        //check new device name doesn't already exist
        if (deviceRepository.existsByUserIdAndName(user.getId(), deviceCreateRequest.name())) {
            throw new AlreadyExistsException("Device with name - '" + deviceCreateRequest.name() + "' already exists on your account");
        }

        Device newDevice = new Device(deviceCreateRequest.name(), user);
        deviceRepository.save(newDevice);

        return new DeviceCreateResponse(newDevice.getId(), newDevice.getDeviceHash());
    }

    @Transactional
    public void deleteDevice(Integer deviceId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceRepository.findByIdAndUser(deviceId, user).orElseThrow(() -> new NotFoundException("Device not found"));

        // delete all screens depending on the device
        screenRepository.deleteByDevice(device);

        deviceRepository.delete(device);
    }

    @Transactional
    public ScreenDto addScreen(Integer deviceId, ScreenDto screenDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceRepository.findByIdAndUser(deviceId, user).orElseThrow(() -> new NotFoundException("Device with id - " + deviceId + " not found"));

        Integer deviceScreenCount = screenRepository.countScreensByDevice(device);
        if (deviceScreenCount >= DEVICE_MAX_SCREENS) {
            throw new DeviceScreenLimitExceeded("Screen limit exceeded");
        }

        if (!userHasRequiredApiKeys(user, screenDto.getScreenType())) {
            throw new MissingApiKey("Missing required api key for: " + screenDto.getScreenType());
        }

        //validate the asset names if needed
        validateAssetName(screenDto, user);

        Screen newScreen = screenDtoMapper.toEntity(screenDto);

        newScreen.setDevice(device);
        newScreen.setPosition(deviceScreenCount);

        newScreen = screenRepository.save(newScreen);
        return screenDtoMapper.toDto(newScreen);
    }

    @Transactional
    public ScreenDto updateScreen(Integer deviceId, Integer screenId, ScreenDto screenDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceRepository.findByIdAndUser(deviceId, user).orElseThrow(() -> new NotFoundException("Device not found"));

        Screen screen = screenRepository.findByIdAndDevice(screenId, device).orElseThrow(() -> new NotFoundException("Screen not found"));

        validateAssetName(screenDto, user);

        screenDtoMapper.updateEntityFromDto(screenDto, screen);
        screen = screenRepository.save(screen);

        return screenDtoMapper.toDto(screen);
    }


    @Transactional
    public void removeScreen(Integer deviceId, Integer screenId, String username) {
        User user  = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceRepository.findByIdAndUser(deviceId, user).orElseThrow(() -> new NotFoundException("Device not found"));

        Screen screen = screenRepository.findByIdAndDevice(screenId, device).orElseThrow(() -> new NotFoundException("Screen with id - " + screenId + " not found"));

        Integer screenIndex = screen.getPosition();
        screenRepository.delete(screen);
        screenRepository.changeIndicesAfterDelete(deviceId, screenIndex);
    }


    private boolean screenBelongsToDevice(Screen screen, Device device) {
        return screen.getDevice().getId().equals(device.getId());
    }

    @Transactional
    public List<ScreenDto> getAllScreensForDevice(Integer deviceId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceRepository.findByIdAndUser(deviceId, user).orElseThrow(() -> new NotFoundException("Device not found"));

        List<Screen> deviceScreens = screenRepository.getScreensByDevice(device);

        return screenDtoMapper.toDtos(deviceScreens);
    }

    @Transactional
    public void reorderDeviceScreens(Integer deviceId, String username, ReorderScreensRequest newScreenOrder) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Device device = deviceRepository.findByIdAndUser(deviceId, user).orElseThrow(() -> new NotFoundException("Device not found"));

        List<Screen> existingScreens = screenRepository.getScreensByDevice(device);
        List<Integer> newOrderIds = newScreenOrder.newOrder();

        //check if the new order size matches the actual screen count
        if (existingScreens.size() != newScreenOrder.newOrder().size()) {
            throw new ScreenReorderException("Number of screens doesnt match");
        }

        Map<Integer, Screen> screenMap = existingScreens.stream()
                .collect(Collectors.toMap(Screen::getId, Function.identity()));

        //check if every screen id was provided (and nothing more or less)
        if (!screenMap.keySet().equals(new HashSet<>(newOrderIds))) {
            throw new ScreenReorderException("Provided screen IDs do not match the device's screen IDs");
        }

        for (int i = 0; i < newOrderIds.size(); i++) {
            Integer screenId = newOrderIds.get(i);
            Screen screen = screenMap.get(screenId);
            screen.setPosition(i);
        }
    }
}
