package jotalac.market_viewer.market_viewer_app.dto.screen.update;

import jakarta.validation.constraints.Min;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoScreenUpdateRequest extends ScreenUpdateRequest {
    private String assetName;
    // will need to create some enum that have all the valid time frames
    private String timeFrame;
    private String currency;
    private Boolean displayGraph;
    private GraphType graphType;
    private Boolean simpleDisplay;

    @Min(value = 2, message = "Minimum fetch interval is 2 minutes")
    private Integer fetchInterval;
}
