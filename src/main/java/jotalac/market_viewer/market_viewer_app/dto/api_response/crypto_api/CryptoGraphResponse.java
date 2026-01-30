package jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PRICE_GRAPH_POINTS;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
public class CryptoGraphResponse {

    @JsonProperty("prices")
    //index (position) and price value
    private List<List<Double>> rawPrices;

//    public List<Double> getReducedPrices() {
//        if (rawPrices == null ||  rawPrices.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        int originalSize = rawPrices.size();
//        int skip = Math.max(1, originalSize / PRICE_GRAPH_POINTS);
//
//        List<Double> reducedPrices = new ArrayList<>();
//
//        for (int i = 0; i < originalSize && reducedPrices.size() < PRICE_GRAPH_POINTS; i+= skip) {
//            reducedPrices.add(rawPrices.get(i).get(1));
//        }
//
//        return reducedPrices;
//    }

    public List<Double> getReducedPrices() {
        if (rawPrices == null || rawPrices.isEmpty()) {
            return Collections.emptyList();
        }

        int originalSize = rawPrices.size();
        List<Double> reducedPrices = new ArrayList<>();

        //if there are fewer points than needed, return all
        if (originalSize <= PRICE_GRAPH_POINTS) {
            for (List<Double> data : rawPrices) {
                reducedPrices.add(data.get(1));
            }
            return reducedPrices;
        }

        double step = (double) (originalSize - 1) / (PRICE_GRAPH_POINTS - 1);

        for (int i = 0; i < PRICE_GRAPH_POINTS - 1; i++) {
            int index = (int) Math.round(i * step);
            reducedPrices.add(rawPrices.get(index).get(1));
        }

        //always add the last retuned value
        reducedPrices.add(rawPrices.get(originalSize - 1).get(1));

        return reducedPrices;
    }

}
