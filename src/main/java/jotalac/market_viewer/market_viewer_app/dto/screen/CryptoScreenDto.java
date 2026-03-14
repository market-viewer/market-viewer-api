package jotalac.market_viewer.market_viewer_app.dto.screen;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jotalac.market_viewer.market_viewer_app.entity.screens.AssetTimeFrame;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoScreenDto extends ScreenDto {

    @Size(min = 1, max = 255)
    private String assetName;
//    @NotNull(message = "Invalid time frame")
    private AssetTimeFrame timeFrame;
    private String currency;
    private Boolean displayGraph;
    private GraphType graphType;
    private Boolean simpleDisplay;

    @Min(value = 2, message = "Minimum fetch interval is 2 minutes")
    private Integer fetchInterval;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CRYPTO;
    }
}