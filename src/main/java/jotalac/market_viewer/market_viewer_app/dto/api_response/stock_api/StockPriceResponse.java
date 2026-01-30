package jotalac.market_viewer.market_viewer_app.dto.api_response.stock_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StockPriceResponse(
        String name,
        String currency,

        @JsonProperty("close")
        Double price,

        @JsonProperty("is_market_open")
        Boolean isMarketOpen
) {
}
