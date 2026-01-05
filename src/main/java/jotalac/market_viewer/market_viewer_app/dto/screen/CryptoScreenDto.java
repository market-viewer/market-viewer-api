package jotalac.market_viewer.market_viewer_app.dto.screen;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jotalac.market_viewer.market_viewer_app.entity.screens.GraphType;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CryptoScreenDto extends ScreenDto {

    @Size(min = 1, max = 255)
    private String assetName;
    // will need to create some enum that have all the valid time frames
    private String timeFrame;
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