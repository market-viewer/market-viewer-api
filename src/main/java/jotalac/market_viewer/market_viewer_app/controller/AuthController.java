package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.MessageResponse;
import jotalac.market_viewer.market_viewer_app.dto.auth.*;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.service.UserService;
import jotalac.market_viewer.market_viewer_app.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto registerResponse = authService.register(registerRequestDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponse = authService.login(loginRequestDto);

        return  ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/recover")
    public ResponseEntity<MessageResponse> recover(@Valid @RequestBody RecoverRequestDto recoverRequestDto) {
        authService.recoverAccount(recoverRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password updated successfully"));
    }

}
