package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.MessageResponse;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDeleteDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDto;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/apiKey")
    public ResponseEntity<MessageResponse> addApiKey(@Valid @RequestBody ApiKeyCreateDto apiKeyCreateDto, Principal principal) {
        boolean created = userService.saveUserApiKey(apiKeyCreateDto, principal.getName());

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("API key added"));
        } else  {
            return ResponseEntity.ok(new MessageResponse("API key updated"));
        }
    }

    @GetMapping("/apiKey")
    public ResponseEntity<List<ApiKeyDto>> getApiKey(Principal principal) {
        List<ApiKeyDto> apiKeyList = userService.getUserApiKeys(principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyList);
    }

    @DeleteMapping("/apiKey")
    public ResponseEntity<MessageResponse> removeApiKey(@Valid @RequestBody ApiKeyDeleteDto apiKeyDeleteDto, Principal principal) {
        userService.deleteUserApiKey(apiKeyDeleteDto.endpoint(), principal.getName());
        return ResponseEntity.ok(new MessageResponse("API Key deleted"));
    }

    @GetMapping("/device")
    public ResponseEntity<List<DeviceDto>> getAllDevices(Principal principal) {
        List<DeviceDto> userDevices = userService.getUserDevices(principal.getName());
        return ResponseEntity.ok(userDevices);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteAccount(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseEntity.ok(new MessageResponse("Account deleted"));
    }

}
