package jotalac.market_viewer.market_viewer_app.service;

import jakarta.transaction.Transactional;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDtoMapper;
import jotalac.market_viewer.market_viewer_app.dto.user.UserCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.exception.user.UserAlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.repository.UserRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyDtoMapper apiKeyDtoMapper;
    private final CryptoDataProvider cryptoDataProvider;

    @Transactional
    public UserDto createUser(UserCreateDto userCreateDto) {
        //check if the user already exists
        validateUserExistence(userCreateDto.username(), userCreateDto.email());

        String hashedPassword = passwordEncoder.encode(userCreateDto.password());
        User newUser = new User(userCreateDto.username(), userCreateDto.email(), hashedPassword);

        userRepository.save(newUser);
        return userDtoMapper.toDto(newUser);
    }

    @Transactional
    public boolean saveUserApiKey(ApiKeyCreateDto apiKeyCreateDto, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        AtomicBoolean keyCreated = new AtomicBoolean(false);

        ApiKey apiKey = apiKeyRepository
                .findByEndpointAndUser(apiKeyCreateDto.endpoint(), user)
                .orElseGet(() -> {
                    keyCreated.set(true);
                    return new ApiKey(apiKeyCreateDto.keyValue(), apiKeyCreateDto.endpoint(), user);
                });

        //validate if the key is valid
        cryptoDataProvider.validateApiKey(apiKeyCreateDto.keyValue());

        apiKey.setValue(apiKeyCreateDto.keyValue());
        apiKeyRepository.save(apiKey);

        return keyCreated.get();
    }

    @Transactional
    public void deleteUserApiKey(ApiKeyProvider apiKeyProvider, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        ApiKey apiKey = apiKeyRepository
                .findByEndpointAndUser(apiKeyProvider, user)
                .orElseThrow(() -> new NotFoundException("ApiKey not found"));

        apiKeyRepository.delete(apiKey);
    }

    @Transactional
    public List<ApiKeyDto> getUserApiKeys(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        List<ApiKey> userApiKeys = apiKeyRepository.findByUser(user);

        return apiKeyDtoMapper.toDtoList(userApiKeys);
    }

    private void validateUserExistence(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username - '" + username + "', already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email : " + email + " already exists");
        }
    }





}
