package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDto;
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
    public ResponseEntity<String> addApiKey(@PathVariable Integer userId, @Valid @RequestBody ApiKeyCreateDto apiKeyCreateDto) {
        userService.saveUserApiKey(apiKeyCreateDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}/apiKey")
    public ResponseEntity<List<ApiKeyDto>> addApiKey(@PathVariable Integer userId) {
        List<ApiKeyDto> apiKeyList = userService.getUserApiKeys(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyList);
    }

}
