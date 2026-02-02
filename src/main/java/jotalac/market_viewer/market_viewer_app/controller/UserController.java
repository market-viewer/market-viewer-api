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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    //TODO add user auth
    @PostMapping("/{userId}/apiKey")
    public ResponseEntity<MessageResponse> addApiKey(@PathVariable Integer userId, @Valid @RequestBody ApiKeyCreateDto apiKeyCreateDto) {
        boolean created = userService.saveUserApiKey(apiKeyCreateDto, userId);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("API key added"));
        } else  {
            return ResponseEntity.ok(new MessageResponse("API key updated"));
        }
    }

    //TODO add user auth
    @GetMapping("/{userId}/apiKey")
    public ResponseEntity<List<ApiKeyDto>> getApiKey(@PathVariable Integer userId) {
        List<ApiKeyDto> apiKeyList = userService.getUserApiKeys(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyList);
    }

    //TODO add user auth
    @DeleteMapping("/{userId}/apiKey")
    public ResponseEntity<MessageResponse> removeApiKey(@PathVariable Integer userId,@Valid @RequestBody ApiKeyDeleteDto apiKeyDeleteDto) {
        userService.deleteUserApiKey(apiKeyDeleteDto.endpoint(), userId);
        return ResponseEntity.ok(new MessageResponse("API Key deleted"));
    }

}
