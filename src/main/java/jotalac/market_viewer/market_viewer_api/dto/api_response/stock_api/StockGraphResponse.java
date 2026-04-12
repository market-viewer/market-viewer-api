package jotalac.market_viewer.market_viewer_api.dto.api_response.stock_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jotalac.market_viewer.market_viewer_api.config.Constants.PRICE_GRAPH_POINTS;

@Slf4j
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockGraphResponse {

    @JsonProperty("values")
    private List<JsonNode> historicalData;

    public List<Double> getReducedPrices() {
        if (historicalData == null || historicalData.isEmpty()) {
            return Collections.emptyList();
        }

        int originalSize = historicalData.size();
        List<Double> reducedPrices = new ArrayList<>();

        //if there are fewer points than needed, reutnr all
        if (originalSize <= PRICE_GRAPH_POINTS) {
            for (JsonNode data : historicalData) {
                reducedPrices.add(data.get("close").asDouble());
            }
            return reducedPrices.reversed();
        }

        double step = (double) (originalSize - 1) / (PRICE_GRAPH_POINTS - 1);

        for (int i = 0; i < PRICE_GRAPH_POINTS - 1; i++) {
            int index = (int) Math.round(i * step);
            reducedPrices.add(historicalData.get(index).get("close").asDouble());
        }

        //always add the last retuned value
        reducedPrices.add(historicalData.get(originalSize - 1).get("close").asDouble());

        return reducedPrices.reversed();
    }
}
