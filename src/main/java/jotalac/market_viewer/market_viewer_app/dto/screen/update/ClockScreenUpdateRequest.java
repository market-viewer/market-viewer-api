package jotalac.market_viewer.market_viewer_app.dto.screen.update;

import jakarta.validation.constraints.Pattern;
import jotalac.market_viewer.market_viewer_app.entity.screens.TimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClockScreenUpdateRequest extends ScreenUpdateRequest {
    private String timeZone;

    @Pattern(regexp = "^0x[0-9a-fA-F]{4}", message = "Invalid RGB565 HEX color")
    private String bgColor;

    private TimeFormat timeFormat;
}
