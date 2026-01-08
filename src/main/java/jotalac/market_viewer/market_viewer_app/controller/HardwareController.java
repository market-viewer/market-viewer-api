package jotalac.market_viewer.market_viewer_app.controller;

import jotalac.market_viewer.market_viewer_app.dto.screen.ScreenDto;
import jotalac.market_viewer.market_viewer_app.dto.screen_data.ScreenDataDto;
import jotalac.market_viewer.market_viewer_app.service.HardwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareService hardwareService;

    private UUID convertStringToDeviceHash(String deviceHash) {
        try {
            return UUID.fromString(deviceHash);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid device hash");
        }
    }

    @GetMapping("/{deviceHash}/sync")
    public ResponseEntity<List<ScreenDto>> syncScreens(@PathVariable String deviceHash) {
        UUID deviceHashUUID = convertStringToDeviceHash(deviceHash);
        List<ScreenDto> deviceScreens = hardwareService.getScreensForDevice(deviceHashUUID);

        return ResponseEntity.ok(deviceScreens);
    }

    @GetMapping("{deviceHash}/screen")
    public ResponseEntity<ScreenDataDto> getScreenData(@PathVariable String deviceHash, @RequestParam Integer position) {
        UUID deviceHashUUID = convertStringToDeviceHash(deviceHash);
        ScreenDataDto screenData = hardwareService.getScreenData(deviceHashUUID, position);
        return ResponseEntity.ok(screenData);
    }



}
