package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.MessageResponse;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDeleteDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/{userId}/apiKey")
    public ResponseEntity<MessageResponse> addApiKey(@PathVariable Integer userId, @Valid @RequestBody ApiKeyCreateDto apiKeyCreateDto) {
        userService.saveUserApiKey(apiKeyCreateDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("API key added"));
    }

    @GetMapping("/{userId}/apiKey")
    public ResponseEntity<List<ApiKeyDto>> getApiKey(@PathVariable Integer userId) {
        List<ApiKeyDto> apiKeyList = userService.getUserApiKeys(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyList);
    }

    @DeleteMapping("/{userId}/apiKey")
    public ResponseEntity<MessageResponse> removeApiKey(@PathVariable Integer userId,@Valid @RequestBody ApiKeyDeleteDto apiKeyDeleteDto) {
        userService.deleteUserApiKey(apiKeyDeleteDto.endpoint(), userId);
        return ResponseEntity.ok(new MessageResponse("API Key deleted"));
    }

}
