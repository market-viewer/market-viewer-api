package jotalac.market_viewer.market_viewer_app.service;

import jakarta.transaction.Transactional;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateResponse;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.*;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper.AITextScreenUpdateMapper;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper.ClockScreenUpdateMapper;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper.CryptoScreenUpdateMapper;
import jotalac.market_viewer.market_viewer_app.dto.screen.update.mapper.StockScreenUpdateMapper;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import jotalac.market_viewer.market_viewer_app.exception.AlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.exception.device.DeviceScreenLimitExceeded;
import jotalac.market_viewer.market_viewer_app.repository.DeviceRepository;
import jotalac.market_viewer.market_viewer_app.repository.ScreenRepository;
import jotalac.market_viewer.market_viewer_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static jotalac.market_viewer.market_viewer_app.config.ValidationConstants.DEVICE_MAX_SCREENS;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final ScreenRepository screenRepository;
    private final UserService userService;
    private final AITextScreenUpdateMapper aITextScreenUpdateMapper;
    private final ClockScreenUpdateMapper clockScreenUpdateMapper;
    private final CryptoScreenUpdateMapper cryptoScreenUpdateMapper;
    private final StockScreenUpdateMapper stockScreenUpdateMapper;

    @Transactional
    public DeviceCreateResponse createDevice(DeviceCreateRequest deviceCreateRequest, String username) {

        User user = userRepository.findByUsername(username);

        if  (user == null) {
            throw new NotFoundException("User not found");
        }

        //check new device name doesnt already exists
        if (deviceRepository.existsByUserIdAndName(user.getId(), deviceCreateRequest.name())) {
            throw new AlreadyExistsException("Device with name - '" + deviceCreateRequest.name() + "' already exists on your account");
        }

        Device newDevice = new Device(deviceCreateRequest.name(), user);

        deviceRepository.save(newDevice);

        return new DeviceCreateResponse(newDevice.getId(), newDevice.getDeviceHash());
    }

    @Transactional
    public ScreenDto addScreen(Integer deviceId, ScreenType screenType, String username) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device with id - " + deviceId + " not found"));
        User user = userRepository.findByUsername(username);
        if  (user == null) {
            throw new NotFoundException("User not found");
        }

        if (!deviceBelongsToUser(device, user)) {
            throw new IllegalStateException("User doesn't own this device");
        }

        Integer deviceScreenCount = screenRepository.countScreensByDevice(device);
        if (deviceScreenCount >= DEVICE_MAX_SCREENS) {
            throw new DeviceScreenLimitExceeded("Screen limit exceeded");
        }

        Screen newScreen = switch (screenType) {
            case CRYPTO -> new CryptoScreen();
            case STOCK ->  new StockScreen();
            case CLOCK ->  new ClockScreen();
            case AI_TEXT -> new AITextScreen();
            default -> throw new IllegalArgumentException("Invalid screen type");
        };

        newScreen.setDevice(device);
        newScreen.setPosition(deviceScreenCount);

        screenRepository.save(newScreen);
        return new ScreenDto(newScreen.getId(), screenType);
    }

    @Transactional
    public ScreenDto updateScreen(Integer deviceId, Integer screenId, ScreenUpdateRequest screenUpdateRequest, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFoundException("User not found");
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device not found"));

        if (!deviceBelongsToUser(device, user)) {
            throw new IllegalStateException("User doesn't own this device");
        }

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new NotFoundException("Screen not found"));

        if (screen instanceof AITextScreen aiTextScreen  && screenUpdateRequest instanceof AITextScreenUpdateRequest aiScreenRequest) {
            aITextScreenUpdateMapper.updateEntityFromDto(aiScreenRequest, aiTextScreen);
        }
        else if (screen instanceof ClockScreen clockScreen && screenUpdateRequest instanceof ClockScreenUpdateRequest clockScreenRequest) {
            clockScreenUpdateMapper.updateEntityFromDto(clockScreenRequest, clockScreen);
        }
        else if (screen instanceof CryptoScreen cryptoScreen && screenUpdateRequest instanceof CryptoScreenUpdateRequest cryptoScreenRequest) {
            cryptoScreenUpdateMapper.updateEntityFromDto(cryptoScreenRequest, cryptoScreen);
        }
        else if (screen instanceof StockScreen stockScreen && screenUpdateRequest instanceof StockScreenUpdateRequest stockScreenUpdateRequest) {
            stockScreenUpdateMapper.updateEntityFromDto(stockScreenUpdateRequest, stockScreen);
        } else {
            throw new IllegalArgumentException("Request type doesn't match the screen type");
        }

        return new ScreenDto(screen.getId(), screen.getScreenType());
    }


    @Transactional
    public void removeScreen(Integer deviceId, Integer screenId, String username) {
        User user  = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device with id - " + deviceId + " not found"));

        if (device.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("User doesn't own this device");
        }

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new NotFoundException("Screen with id - " + screenId + " not found"));

        Integer screenIndex = screen.getPosition();
        screenRepository.delete(screen);
        screenRepository.changeIndicesAfterDelete(deviceId, screenIndex);
    }

    private Boolean canAddNewScreen(Device device) {
        return screenRepository.countScreensByDevice(device) < DEVICE_MAX_SCREENS;
    }

    private Boolean deviceBelongsToUser(Device device, User user) {
        return device.getUser().getUsername().equals(user.getUsername());
    }

    @Transactional
    public List<Screen> getAllScreensForDevice(Integer deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device not found"));
        List<Screen> deviceScreens = screenRepository.getScreensByDevice(device);

        return deviceScreens;
    }
}
