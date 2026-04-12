package jotalac.market_viewer.market_viewer_api.dto.screen_data;

import jotalac.market_viewer.market_viewer_api.entity.screens.ScreenType;
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