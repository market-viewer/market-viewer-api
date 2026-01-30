package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_DATA_LIFETIME_MINUTES;
import static org.junit.jupiter.api.Assertions.*;

class CryptoScreenTest {

    @Test
    @DisplayName("CryptoScreen should have default values")
    void shouldHaveDefaultValues() {
        CryptoScreen screen = new CryptoScreen();
        
        assertEquals("bitcoin", screen.getAssetName());
        assertEquals("USD", screen.getCurrency());
        assertEquals(CryptoTimeFrame.DAY, screen.getTimeFrame());
        assertEquals(true, screen.getDisplayGraph());
        assertEquals(GraphType.LINE, screen.getGraphType());
        assertEquals(ScreenType.CRYPTO, screen.getScreenType());
        assertNotNull(screen.getPriceData());
    }

    @Test
    @DisplayName("CryptoScreen needsUpdate should return true when no data")
    void shouldNeedUpdateWhenNoData() {
        CryptoScreen screen = new CryptoScreen();
        screen.getPriceData().setFetchTimePrice(null);
        
        assertTrue(screen.needsUpdate());
    }

    @Test
    @DisplayName("CryptoScreen needsUpdate should return false when data is fresh")
    void shouldNotNeedUpdateWhenFresh() {
        CryptoScreen screen = new CryptoScreen();
        screen.getPriceData().setFetchTimePrice(LocalDateTime.now());
        
        assertFalse(screen.needsUpdate());
    }

    @Test
    @DisplayName("CryptoScreen needsUpdate should return true when data is stale")
    void shouldNeedUpdateWhenStale() {
        CryptoScreen screen = new CryptoScreen();
        LocalDateTime staleTime = LocalDateTime.now().minusMinutes(PRICE_DATA_LIFETIME_MINUTES + 1);
        screen.getPriceData().setFetchTimePrice(staleTime);
        
        assertTrue(screen.needsUpdate());
    }
}
