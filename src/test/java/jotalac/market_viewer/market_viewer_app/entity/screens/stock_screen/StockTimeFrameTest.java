package jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class StockTimeFrameTest {

    @ParameterizedTest
    @CsvSource({
        "5min, FIVE_MINUTES",
        "1h, HOUR",
        "5h, FIVE_HOUR",
        "1day, DAY",
        "7day, WEEK",
        "14day, TWO_WEEKS",
        "30day, MONTH",
        "1y, YEAR",
        "5y, FIVE_YEARS"
    })
    @DisplayName("fromLabel should return correct enum")
    void shouldReturnCorrectEnumFromLabel(String label, StockTimeFrame expected) {
        assertEquals(expected, StockTimeFrame.fromLabel(label));
    }

    @Test
    @DisplayName("fromLabel should be case insensitive")
    void shouldBeCaseInsensitive() {
        assertEquals(StockTimeFrame.HOUR, StockTimeFrame.fromLabel("1H"));
    }

    @Test
    @DisplayName("fromLabel should return null for invalid label")
    void shouldReturnNullForInvalid() {
        assertNull(StockTimeFrame.fromLabel("invalid"));
    }
}
