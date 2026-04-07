package jotalac.market_viewer.market_viewer_app.service;

import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyCreateDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDto;
import jotalac.market_viewer.market_viewer_app.dto.api_key.ApiKeyDtoMapper;
import jotalac.market_viewer.market_viewer_app.dto.device.DeviceDto;
import jotalac.market_viewer.market_viewer_app.dto.user.UserDtoMapper;
import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.repository.DeviceRepository;
import jotalac.market_viewer.market_viewer_app.repository.ScreenRepository;
import jotalac.market_viewer.market_viewer_app.repository.UserRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.AIGenerationProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyDtoMapper apiKeyDtoMapper;
    private final CryptoDataProvider cryptoDataProvider;
    private final StockDataProvider stockDataProvider;
    private final AIGenerationProvider aiGenerationProvider;
    private final DeviceRepository deviceRepository;
    private final ScreenRepository screenRepository;

    private final String userNotFoundMsg = "User not found";

    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(userNotFoundMsg));
        userRepository.delete(user);
    }

    @Transactional
    public boolean saveUserApiKey(ApiKeyCreateDto apiKeyCreateDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(userNotFoundMsg));

        //validate if the key is valid
        validateApiKey(apiKeyCreateDto);

        AtomicBoolean keyCreated = new AtomicBoolean(false);
        ApiKey apiKey = apiKeyRepository
                .findByEndpointAndUser(apiKeyCreateDto.endpoint(), user)
                .orElseGet(() -> {
                    keyCreated.set(true);
                    return new ApiKey(apiKeyCreateDto.keyValue(), apiKeyCreateDto.endpoint(), user);
                });

        apiKey.setValue(apiKeyCreateDto.keyValue());
        apiKeyRepository.save(apiKey);

        return keyCreated.get();
    }

    @Transactional
    public void deleteUserApiKey(ApiKeyProvider apiKeyProvider, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(userNotFoundMsg));

        ApiKey apiKey = apiKeyRepository
                .findByEndpointAndUser(apiKeyProvider, user)
                .orElseThrow(() -> new NotFoundException("ApiKey not found"));

        apiKeyRepository.delete(apiKey);
    }

    @Transactional()
    public List<ApiKeyDto> getUserApiKeys(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(userNotFoundMsg));

        List<ApiKey> userApiKeys = apiKeyRepository.findByUser(user);

        return apiKeyDtoMapper.toDtoList(userApiKeys);
    }

    private void validateApiKey(ApiKeyCreateDto apiKeyCreateDto) {
        switch (apiKeyCreateDto.endpoint()) {
            case COINGECKO -> {
                cryptoDataProvider.validateApiKey(apiKeyCreateDto.keyValue());
            }
            case TWELVE_DATA -> {
                stockDataProvider.validateApiKey(apiKeyCreateDto.keyValue());
            }
            case GEMINI -> {
                aiGenerationProvider.validateApiKey(apiKeyCreateDto.keyValue());
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(userNotFoundMsg));
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
