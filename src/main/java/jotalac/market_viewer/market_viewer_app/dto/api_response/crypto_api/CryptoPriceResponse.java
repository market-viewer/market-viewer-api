package jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class CryptoPriceResponse {
    @JsonProperty("current_price")
    private Double price;

    @JsonProperty("ath")
    private Double allTimeHigh;

    @JsonProperty("ath_change_percentage")
    private Double allTimeHighChange;

    //all to get the correct price change percentage

    private final Map<String, Double> extraFields = new HashMap<>();

    @JsonAnySetter
    public void addExtraField(String key, Object value) {
        // Only store if it's our specific currency change field
        if (key.endsWith("_in_currency")) {
            this.extraFields.put("requested_change", (Double) value); // Normalize the key name here!
        }
    }

    public Double getPriceChange() {
        Object val = extraFields.get("requested_change");
        if (val instanceof Number n) {
            return n.doubleValue();
        }
        return 0.0;
    }

}
