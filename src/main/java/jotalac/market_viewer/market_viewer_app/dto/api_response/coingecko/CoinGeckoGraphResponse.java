package jotalac.market_viewer.market_viewer_app.dto.api_response.coingecko;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_GRAPH_POINTS;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
public class CoinGeckoGraphResponse {

    @JsonProperty("prices")
    //index (position) and price value
    private List<List<Double>> rawPrices;

    public List<Double> getReducedPrices() {
        if (rawPrices == null ||  rawPrices.isEmpty()) {
            return null;
        }

        int originalSize = rawPrices.size();
        int skip = Math.max(1, originalSize / PRICE_GRAPH_POINTS);

        List<Double> reducedPrices = new ArrayList<>();

        for (int i = 0; i < originalSize && reducedPrices.size() < PRICE_GRAPH_POINTS; i+= skip) {
            reducedPrices.add(rawPrices.get(i).get(1));
        }

        return reducedPrices;
    }

}
