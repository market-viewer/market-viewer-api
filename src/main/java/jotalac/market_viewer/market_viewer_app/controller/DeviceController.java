package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.MessageResponse;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateResponse;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceDto;
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
    public ResponseEntity<DeviceCreateResponse> createDevice(@Valid @RequestBody DeviceCreateRequest deviceCreateRequest, Principal principal) {
        DeviceCreateResponse deviceCreateResponse = deviceService.createDevice(deviceCreateRequest, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceCreateResponse);
    }

    @DeleteMapping("{deviceId}")
    public ResponseEntity<String> removeDevice(@PathVariable Integer deviceId, Principal principal) {
        deviceService.deleteDevice(deviceId, principal.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{deviceId}/screen")
    public ResponseEntity<ScreenDto> addScreen(@PathVariable Integer deviceId, @Valid @RequestBody ScreenDto screenDto, Principal principal) {
        ScreenDto newScreen = deviceService.addScreen(deviceId, screenDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(newScreen);
    }

    @PutMapping("{deviceId}/screen/{screenId}")
    public ResponseEntity<ScreenDto> updateScreen(@PathVariable Integer deviceId, @PathVariable Integer screenId, @Valid @RequestBody ScreenDto screenDto, Principal principal) {
        ScreenDto updatedScreen = deviceService.updateScreen(deviceId, screenId, screenDto, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(updatedScreen);
    }

    @DeleteMapping("/{deviceId}/screen/{screenId}")
    public ResponseEntity<MessageResponse> removeScreen(@PathVariable Integer deviceId, @PathVariable Integer screenId, Principal principal) {
        deviceService.removeScreen(deviceId, screenId, principal.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{deviceId}/screen")
    public ResponseEntity<List<ScreenDto>> getDeviceScreens(@PathVariable Integer deviceId, Principal principal) {
        List<ScreenDto> deviceScreens = deviceService.getAllScreensForDevice(deviceId, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(deviceScreens);
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices(Principal principal) {
        List<DeviceDto> userDevices = deviceService.getAllDevices(principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(userDevices);
    }

    @PatchMapping("{deviceId}/screen/order")
    public ResponseEntity<MessageResponse> reorderScreens(@PathVariable Integer deviceId, @Valid @RequestBody ReorderScreensRequest newScreenOrder, Principal principal) {
        deviceService.reorderDeviceScreens(deviceId, principal.getName(), newScreenOrder);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Screen order set successfully"));
    }
}
