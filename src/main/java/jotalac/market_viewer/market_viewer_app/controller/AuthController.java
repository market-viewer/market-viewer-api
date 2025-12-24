package jotalac.market_viewer.market_viewer_app.controller;

import jakarta.validation.Valid;
import jotalac.market_viewer.market_viewer_app.dto.user.UserCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto createdUser = userService.createUser(userCreateDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
