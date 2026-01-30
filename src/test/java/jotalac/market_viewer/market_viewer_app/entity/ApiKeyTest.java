package jotalac.market_viewer.market_viewer_app.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiKeyTest {

    @Test
    @DisplayName("ApiKey should hold values correctly")
    void shouldHandleApiKeyValues() {
        ApiKey apiKey = new ApiKey();
        User user = new User();
        
        apiKey.setValue("encrypted_secret");
        apiKey.setEndpoint(ApiKeyProvider.COINGECKO); // Assuming enum value exists, need to verify or use generic
        apiKey.setUser(user);

        assertEquals("encrypted_secret", apiKey.getValue());
        assertEquals(ApiKeyProvider.COINGECKO, apiKey.getEndpoint());
        assertEquals(user, apiKey.getUser());
    }
    
    @Test
    @DisplayName("ApiKey constructor should initialize fields")
    void shouldInitializeViaConstructor() {
        User user = new User();
        ApiKey apiKey = new ApiKey("secret", ApiKeyProvider.TWELVE_DATA, user);
        
        assertEquals("secret", apiKey.getValue());
        assertEquals(ApiKeyProvider.TWELVE_DATA, apiKey.getEndpoint());
        assertEquals(user, apiKey.getUser());
    }
}
