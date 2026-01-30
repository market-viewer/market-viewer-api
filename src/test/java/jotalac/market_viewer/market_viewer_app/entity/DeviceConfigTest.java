package jotalac.market_viewer.market_viewer_app.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceConfigTest {

    @Test
    @DisplayName("DeviceConfig should have default values or be settable")
    void shouldHandleDeviceConfig() {
        DeviceConfig config = new DeviceConfig();
        
        // Assuming default is null if not initialized in field declaration, 
        // but in Device constructor it is initialized. 
        // Let's check the class definition: "private Boolean playSounds = true;" 
        // So default constructor should result in playSounds = true if the field has initializer.
        
        // Wait, looking at DeviceConfig.java:
        // @NoArgsConstructor
        // @AllArgsConstructor
        // private Boolean playSounds = true;
        
        // If the field is initialized at declaration, the NoArgsConstructor respects it.
        assertTrue(config.getPlaySounds(), "playSounds should default to true");
        
        config.setPlaySounds(false);
        assertFalse(config.getPlaySounds());
    }
    
    @Test
    @DisplayName("DeviceConfig all-args constructor")
    void shouldCreateConfigWithAllArgs() {
        DeviceConfig config = new DeviceConfig(false);
        assertFalse(config.getPlaySounds());
    }
}
