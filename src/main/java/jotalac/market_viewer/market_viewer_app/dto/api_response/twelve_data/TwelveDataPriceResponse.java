package jotalac.market_viewer.market_viewer_app.dto.api_response.twelve_data;

public record TwelveDataPriceResponse(
        String name,
        String currency,
        String price,
        String isMarketOpen
) {
}
