package jotalac.market_viewer.market_viewer_app.service.auth;


import jotalac.market_viewer.market_viewer_app.dto.auth.RegisterRequestDto;
import jotalac.market_viewer.market_viewer_app.dto.auth.RegisterResponseDto;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.exception.auth.LoginException;
import jotalac.market_viewer.market_viewer_app.exception.user.UserAlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.repository.RecoveryCodeRepository;
import jotalac.market_viewer.market_viewer_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecoveryCodeRepository recoveryCodeRepository;
    private final RecoveryCodeService recoveryCodeService;
    AuthenticationManager authenticationManager;
    JwtService jwtService;


    @Transactional
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        //check if passwords match
        if (!registerRequestDto.password().equals(registerRequestDto.passwordRepeat())) {
            throw new LoginException("Passwords do not match");
        }

        //check if the user already exists
        checkUserExists(registerRequestDto.username());

        //save the user
        String hashedPassword = passwordEncoder.encode(registerRequestDto.password());
        User newUser = new User(registerRequestDto.username(), hashedPassword);
        userRepository.save(newUser);

        List<String> recoveryCodes = recoveryCodeService.generateAndSaveCodes(newUser);

        return new RegisterResponseDto("Registration successful", recoveryCodes);
    }

    private void checkUserExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists");
        }
    }

//    public LoginResponseDto login(LoginRequestDto request) {
//        //do some staff
//    }
}
