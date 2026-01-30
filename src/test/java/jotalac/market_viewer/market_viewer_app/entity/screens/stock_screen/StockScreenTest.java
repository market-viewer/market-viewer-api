package jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen;

import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_DATA_LIFETIME_MINUTES;
import static org.junit.jupiter.api.Assertions.*;

class StockScreenTest {

    @Test
    @DisplayName("StockScreen should have default values")
    void shouldHaveDefaultValues() {
        StockScreen screen = new StockScreen();
        
        assertEquals("AAPL", screen.getSymbol());
        assertEquals(StockTimeFrame.DAY, screen.getTimeFrame());
        assertEquals(true, screen.getDisplayGraph());
        assertEquals(GraphType.LINE, screen.getGraphType());
        assertEquals(ScreenType.STOCK, screen.getScreenType());
        assertNotNull(screen.getPriceData());
    }

    @Test
    @DisplayName("StockScreen needsUpdate should return true when no data")
    void shouldNeedUpdateWhenNoData() {
        StockScreen screen = new StockScreen();
        screen.getPriceData().setLastFetchTime(null);
        
        assertTrue(screen.needsUpdate());
    }

    @Test
    @DisplayName("StockScreen needsUpdate should return false when data is fresh")
    void shouldNotNeedUpdateWhenFresh() {
        StockScreen screen = new StockScreen();
        // Set time to now
        screen.getPriceData().setLastFetchTime(LocalDateTime.now());
        
        assertFalse(screen.needsUpdate());
    }

    @Test
    @DisplayName("StockScreen needsUpdate should return true when data is stale")
    void shouldNeedUpdateWhenStale() {
        StockScreen screen = new StockScreen();
        // Set time to (lifetime + 1) minutes ago
        LocalDateTime staleTime = LocalDateTime.now().minusMinutes(PRICE_DATA_LIFETIME_MINUTES + 1);
        screen.getPriceData().setLastFetchTime(staleTime);
        
        assertTrue(screen.needsUpdate());
    }
    
    @Test
    @DisplayName("StockScreen setters should work")
    void shouldUpdateFields() {
        StockScreen screen = new StockScreen();
        screen.setSymbol("GOOG");
        screen.setTimeFrame(StockTimeFrame.WEEK);
        screen.setGraphType(GraphType.CANDLE);
        
        assertEquals("GOOG", screen.getSymbol());
        assertEquals(StockTimeFrame.WEEK, screen.getTimeFrame());
        assertEquals(GraphType.CANDLE, screen.getGraphType());
    }
}
