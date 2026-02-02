package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.MessageResponse;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateResponse;
import jotalac.market_viewer.market_viewer_app.dto.device.ReorderScreensRequest;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceCreateResponse> createDevice(@Valid @RequestBody DeviceCreateRequest deviceCreateRequest) {
        DeviceCreateResponse deviceCreateResponse = deviceService.createDevice(deviceCreateRequest, "test");
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceCreateResponse);
    }

    @DeleteMapping("{deviceId}")
    public ResponseEntity<String> removeDevice(@PathVariable Integer deviceId) {
        deviceService.deleteDevice(deviceId, "test");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO list user devices - for front end
    @GetMapping()
    public Object listUserDevices(Principal principal) {
        return null;
    }

    @PostMapping("/{deviceId}/screen")
    public ResponseEntity<ScreenDto> addScreen(@PathVariable Integer deviceId, @Valid @RequestBody ScreenDto screenDto) {
        ScreenDto newScreen = deviceService.addScreen(deviceId, screenDto, "test");
        return ResponseEntity.status(HttpStatus.CREATED).body(newScreen);
    }

    @PutMapping("{deviceId}/screen/{screenId}")
    public ResponseEntity<ScreenDto> updateScreen(@PathVariable Integer deviceId, @PathVariable Integer screenId, @Valid @RequestBody ScreenDto screenDto) {
        ScreenDto updatedScreen = deviceService.updateScreen(deviceId, screenId, screenDto, "test");
        return ResponseEntity.status(HttpStatus.OK).body(updatedScreen);
    }

    @DeleteMapping("/{deviceId}/screen/{screenId}")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResponse> removeScreen(@PathVariable Integer deviceId, @PathVariable Integer screenId, Principal principal) {
        deviceService.removeScreen(deviceId, screenId, "test");
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Screen removed successfully"));
    }

    @GetMapping("/{deviceId}/screen")
    public ResponseEntity<List<ScreenDto>> getDeviceScreens(@PathVariable Integer deviceId) {
        List<ScreenDto> deviceScreens = deviceService.getAllScreensForDevice(deviceId, "test");

        return ResponseEntity.status(HttpStatus.OK).body(deviceScreens);
    }

    @PatchMapping("{deviceId}/screen-order")
    public ResponseEntity<MessageResponse> reorderScreens(@PathVariable Integer deviceId, @Valid @RequestBody ReorderScreensRequest newScreenOrder) {
        deviceService.reorderDeviceScreens(deviceId, "test", newScreenOrder);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Screen order set successfully"));
    }
}
