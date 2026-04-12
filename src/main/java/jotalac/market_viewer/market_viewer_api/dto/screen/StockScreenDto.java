package jotalac.market_viewer.market_viewer_api.dto.screen;

import jakarta.validation.constraints.Min;
import jotalac.market_viewer.market_viewer_api.entity.screens.AssetTimeFrame;
import jotalac.market_viewer.market_viewer_api.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_api.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockScreenDto extends ScreenDto {
    private String symbol;
    private AssetTimeFrame timeFrame;
    private Boolean displayGraph;
    private GraphType graphType;
    private Boolean simpleDisplay;

    @Min(value = 2, message = "Minimum fetch interval is 2 minutes")
    private Integer fetchInterval;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.STOCK;
    }
}
