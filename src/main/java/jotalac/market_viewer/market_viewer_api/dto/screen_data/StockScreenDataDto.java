package jotalac.market_viewer.market_viewer_api.dto.screen_data;

import jotalac.market_viewer.market_viewer_api.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockScreenDataDto extends ScreenDataDto {
    private String name;
    private String currency;
    private Double price;
    private Double priceChange;
    private List<Double> graphData;
    private Boolean isMarketOpen;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.STOCK;
    }
}
