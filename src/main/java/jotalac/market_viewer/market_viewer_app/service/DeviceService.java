package jotalac.market_viewer.market_viewer_app.service;

import jakarta.transaction.Transactional;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateResponse;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenMapper;
import jotalac.market_viewer.market_viewer_app.entity.Device;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.entity.screens.*;
import jotalac.market_viewer.market_viewer_app.exception.AlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.exception.device.DeviceScreenLimitExceeded;
import jotalac.market_viewer.market_viewer_app.exception.screen.ScreenDoesntBelongToDeviceException;
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
    private final ScreenMapper screenMapper;

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
    public ScreenDto addScreen(Integer deviceId, ScreenDto screenDto, String username) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device with id - " + deviceId + " not found"));
        User user = userRepository.findByUsername(username);
        if  (user == null) {
            throw new NotFoundException("User not found");
        }

        deviceBelongsToUser(device, user);

        Integer deviceScreenCount = screenRepository.countScreensByDevice(device);
        if (deviceScreenCount >= DEVICE_MAX_SCREENS) {
            throw new DeviceScreenLimitExceeded("Screen limit exceeded");
        }
//
//        Screen newScreen = switch (screenType) {
//            case CRYPTO -> new CryptoScreen();
//            case STOCK ->  new StockScreen();
//            case CLOCK ->  new ClockScreen();
//            case AI_TEXT -> new AITextScreen();
//            default -> throw new IllegalArgumentException("Invalid screen type");
//        };

        Screen newScreen = screenMapper.toEntity(screenDto);

        newScreen.setDevice(device);
        newScreen.setPosition(deviceScreenCount);

        newScreen = screenRepository.save(newScreen);
        return screenMapper.toDto(newScreen);
    }

    @Transactional
    public ScreenDto updateScreen(Integer deviceId, Integer screenId, ScreenDto screenDto, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFoundException("User not found");
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device not found"));

        deviceBelongsToUser(device, user);

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new NotFoundException("Screen not found"));
        if (!screenBelongsToDevice(screen, device)) {
            throw new ScreenDoesntBelongToDeviceException("Screen doesnt belong to this device");
        }

        screenMapper.updateEntityFromDto(screenDto, screen);
        screen = screenRepository.save(screen);

        return screenMapper.toDto(screen);
    }


    @Transactional
    public void removeScreen(Integer deviceId, Integer screenId, String username) {
        User user  = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device with id - " + deviceId + " not found"));
        deviceBelongsToUser(device, user);

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new NotFoundException("Screen with id - " + screenId + " not found"));

        Integer screenIndex = screen.getPosition();
        screenRepository.delete(screen);
        screenRepository.changeIndicesAfterDelete(deviceId, screenIndex);
    }

    private Boolean canAddNewScreen(Device device) {
        return screenRepository.countScreensByDevice(device) < DEVICE_MAX_SCREENS;
    }

    private void deviceBelongsToUser(Device device, User user) {
        if (!device.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalStateException("User doesn't own this device");
        }
    }

    private Boolean screenBelongsToDevice(Screen screen, Device device) {
        return screen.getDevice().getId().equals(device.getId());
    }

    @Transactional
    public List<ScreenDto> getAllScreensForDevice(Integer deviceId, String username) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException("Device not found"));

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        deviceBelongsToUser(device, user);

        List<Screen> deviceScreens = screenRepository.getScreensByDevice(device);

        return screenMapper.toDtos(deviceScreens);
    }
}
