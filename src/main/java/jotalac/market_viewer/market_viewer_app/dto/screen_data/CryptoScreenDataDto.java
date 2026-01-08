package jotalac.market_viewer.market_viewer_app.dto.screen_data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CryptoScreenDataDto extends ScreenDataDto {

    private Double price;

    private Double priceChange;

    private Double allTimeHigh;

    private Double allTimeHighChange;

    private List<Double> graphData;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CRYPTO;
    }
}