package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.auth.RegisterRequestDto;
import jotalac.market_viewer.market_viewer_app.dto.auth.RegisterResponseDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.entity.User;
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
}
