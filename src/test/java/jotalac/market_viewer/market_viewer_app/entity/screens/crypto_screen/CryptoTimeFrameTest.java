package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTimeFrameTest {

    @ParameterizedTest
    @CsvSource({
        "1h, HOUR",
        "24h, DAY",
        "7d, WEEK",
        "14d, TWO_WEEKS",
        "30d, MONTH",
        "200d, TWO_HUNDRED_DAYS",
        "1y, YEAR"
    })
    @DisplayName("fromValue should return correct enum")
    void shouldReturnCorrectEnumFromValue(String value, CryptoTimeFrame expected) {
        assertEquals(expected, CryptoTimeFrame.fromValue(value));
    }

    @Test
    @DisplayName("fromValue should be case insensitive")
    void shouldBeCaseInsensitive() {
        assertEquals(CryptoTimeFrame.DAY, CryptoTimeFrame.fromValue("24H"));
    }

    @Test
    @DisplayName("fromValue should return null for invalid value")
    void shouldReturnNullForInvalid() {
        assertNull(CryptoTimeFrame.fromValue("invalid"));
    }
    
    @ParameterizedTest
    @CsvSource({
        "HOUR, 1",
        "DAY, 1",
        "WEEK, 7",
        "TWO_WEEKS, 14",
        "MONTH, 30",
        "TWO_HUNDRED_DAYS, 200",
        "YEAR, 365"
    })
    @DisplayName("convertToDays should return correct days")
    void shouldConvertToDays(CryptoTimeFrame timeFrame, Integer expectedDays) {
        assertEquals(expectedDays, timeFrame.convertToDays());
    }
}
