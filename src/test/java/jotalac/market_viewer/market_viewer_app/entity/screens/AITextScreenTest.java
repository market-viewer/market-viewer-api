package jotalac.market_viewer.market_viewer_app.entity.screens;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AITextScreenTest {

    @Test
    @DisplayName("AITextScreen should have default values")
    void shouldHaveDefaultValues() {
        AITextScreen screen = new AITextScreen();
        
        assertEquals("", screen.getPrompt());
        assertEquals("", screen.getDisplayText());
        assertEquals(5, screen.getFetchIntervalHours());
        assertEquals(ScreenType.AI_TEXT, screen.getScreenType());
    }

    @Test
    @DisplayName("AITextScreen needsUpdate should be true if text is empty")
    void shouldNeedUpdateIfTextEmpty() {
        AITextScreen screen = new AITextScreen();
        screen.setDisplayText("");
        // even if lastFetchTime is now, if text is empty, it might need update? 
        // Logic says: if (displayText.isEmpty()) return true;
        screen.setLastFetchTime(LocalDateTime.now());
        
        assertTrue(screen.needsUpdate());
    }

    @Test
    @DisplayName("AITextScreen needsUpdate should be true if time expired")
    void shouldNeedUpdateIfTimeExpired() {
        AITextScreen screen = new AITextScreen();
        screen.setDisplayText("Some text");
        screen.setFetchIntervalHours(5);
        
        // 6 hours ago
        screen.setLastFetchTime(LocalDateTime.now().minusHours(6));
        
        assertTrue(screen.needsUpdate());
    }
    
    @Test
    @DisplayName("AITextScreen needsUpdate should be false if fresh")
    void shouldNotNeedUpdateIfFresh() {
        AITextScreen screen = new AITextScreen();
        screen.setDisplayText("Some text");
        screen.setFetchIntervalHours(5);
        
        // 1 hour ago
        screen.setLastFetchTime(LocalDateTime.now().minusHours(1));
        
        assertFalse(screen.needsUpdate());
    }
}
