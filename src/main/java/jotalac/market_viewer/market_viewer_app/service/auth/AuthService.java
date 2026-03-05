package jotalac.market_viewer.market_viewer_app.service.auth;


import jotalac.market_viewer.market_viewer_app.dto.auth.*;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.exception.auth.AccountRecoverException;
import jotalac.market_viewer.market_viewer_app.exception.auth.LoginException;
import jotalac.market_viewer.market_viewer_app.exception.auth.RegisterException;
import jotalac.market_viewer.market_viewer_app.exception.user.UserAlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.repository.RecoveryCodeRepository;
import jotalac.market_viewer.market_viewer_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecoveryCodeRepository recoveryCodeRepository;
    private final RecoveryCodeService recoveryCodeService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Transactional
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        //check if passwords match
        if (!registerRequestDto.password().equals(registerRequestDto.passwordRepeat())) {
            throw new RegisterException("Passwords do not match");
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

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            if (!authentication.isAuthenticated()) {
                throw new LoginException("Invalid username or password");
            }

            String token = jwtService.generateToken(request.username());
            return new LoginResponseDto(token);
        } catch(AuthenticationException e) {
            log.info(e.getMessage());
            throw new LoginException("Invalid username or password");
        }
    }

    @Transactional
    public void recoverAccount(RecoverRequestDto recoverDto) {
        //check if password match
        if (!recoverDto.newPassword().equals(recoverDto.newPasswordRepeat())) {
            throw new AccountRecoverException("Passwords do not match");
        }

        //check if the recovery code is valid
        User user = userRepository.findByUsername(recoverDto.username()).orElseThrow(() -> new NotFoundException("Invalid username or recovery code"));

        if (!recoveryCodeService.validateAndUseCode(user, recoverDto.recoverCode())) {
            throw new AccountRecoverException("Invalid username or recovery code");
        }

        //change the password
        String hashedPassword = passwordEncoder.encode(recoverDto.newPassword());
        user.setPassword(hashedPassword);
    }

    private void checkUserExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists");
        }
    }

}
