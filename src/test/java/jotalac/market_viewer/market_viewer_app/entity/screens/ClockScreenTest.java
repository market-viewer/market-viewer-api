package jotalac.market_viewer.market_viewer_app.entity.screens;

import jotalac.market_viewer.market_viewer_app.entity.screens.clock_screen.ClockScreen;
import jotalac.market_viewer.market_viewer_app.entity.screens.clock_screen.TimeFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClockScreenTest {

    @Test
    @DisplayName("ClockScreen should have default values")
    void shouldHaveDefaultValues() {
        ClockScreen screen = new ClockScreen();
        
        assertEquals("Europe/London", screen.getTimezone());
        assertEquals(TimeFormat.TWENTY_FOUR_HOUR, screen.getTimeFormat());
        assertEquals(ScreenType.CLOCK, screen.getScreenType());
    }

    @Test
    @DisplayName("ClockScreen setters should work")
    void shouldUpdateFields() {
        ClockScreen screen = new ClockScreen();
        screen.setTimezone("America/New_York");
        screen.setTimeFormat(TimeFormat.TWELVE_HOUR);
        
        assertEquals("America/New_York", screen.getTimezone());
        assertEquals(TimeFormat.TWELVE_HOUR, screen.getTimeFormat());
    }
}
