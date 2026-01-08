package jotalac.market_viewer.market_viewer_app.dto.screen_data;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockScreenDataDto extends ScreenDataDto {
    private Double price;
    private Double priceChange;
    private Double openPrice;
    private Double closePrice;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.STOCK;
    }
}
