package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.MessageResponse;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceCreateResponse;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenCreateRequest;
import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/device")
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

    @PostMapping("/{deviceId}/screen")
    public ResponseEntity<ScreenDto> addScreen(@PathVariable Integer deviceId, @Valid @RequestBody ScreenCreateRequest screenCreateRequest) {
        ScreenDto newScreen = deviceService.addScreen(deviceId, screenCreateRequest.type(), "test");
        return ResponseEntity.status(HttpStatus.CREATED).body(newScreen);
    }

    @DeleteMapping("/{deviceId}/screen/{screenId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResponse> removeScreen(@PathVariable Integer deviceId, @PathVariable Integer screenId, Principal principal) {
        deviceService.removeScreen(deviceId, screenId, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Screen removed successfully"));
    }
}
