package jotalac.market_viewer.market_viewer_app.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    @Test
    @DisplayName("Device constructor should initialize fields and generate UUID")
    void shouldInitializeDeviceCorrectly() {
        User user = new User("owner", "owner@example.com", "pass");
        String deviceName = "Living Room";

        Device device = new Device(deviceName, user);

        assertNotNull(device.getDeviceHash(), "Device hash should be generated");
        assertNotNull(device.getDeviceConfig(), "Device config should be initialized");
        assertEquals(deviceName, device.getName());
        assertEquals(user, device.getUser());
        
        // Verify default config
        assertTrue(device.getDeviceConfig().getPlaySounds(), "Default device config should have playSounds=true");
    }


}
