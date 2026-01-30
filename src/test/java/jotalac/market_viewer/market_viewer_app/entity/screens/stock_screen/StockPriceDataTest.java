package jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockPriceDataTest {

    @Test
    @DisplayName("StockPriceData should store values correctly")
    void shouldStoreValues() {
        StockPriceData data = new StockPriceData();
        data.setName("Apple Inc.");
        data.setCurrency("USD");
        data.setPrice(150.0);
        data.setPriceChange(1.5);
        data.setGraphData(List.of(140.0, 150.0));
        data.setLastFetchTime(LocalDateTime.now());
        data.setIsMarketOpen(true);

        assertEquals("Apple Inc.", data.getName());
        assertEquals("USD", data.getCurrency());
        assertEquals(150.0, data.getPrice());
        assertEquals(1.5, data.getPriceChange());
        assertEquals(2, data.getGraphData().size());
        assertNotNull(data.getLastFetchTime());
        assertTrue(data.getIsMarketOpen());
    }
}
