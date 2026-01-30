package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CryptoPriceDataTest {

    @Test
    @DisplayName("CryptoPriceData should store values correctly")
    void shouldStoreValues() {
        CryptoPriceData data = new CryptoPriceData();
        data.setPrice(50000.0);
        data.setPriceChange(100.0);
        data.setAllTimeHigh(60000.0);
        data.setAllTimeHighChange(-10000.0);
        data.setGraphData(List.of(49000.0, 50000.0));
        data.setFetchTimePrice(LocalDateTime.now());
        data.setFetchTimeGraph(LocalDateTime.now());

        assertEquals(50000.0, data.getPrice());
        assertEquals(100.0, data.getPriceChange());
        assertEquals(60000.0, data.getAllTimeHigh());
        assertEquals(-10000.0, data.getAllTimeHighChange());
        assertEquals(2, data.getGraphData().size());
        assertNotNull(data.getFetchTimePrice());
        assertNotNull(data.getFetchTimeGraph());
    }
}
