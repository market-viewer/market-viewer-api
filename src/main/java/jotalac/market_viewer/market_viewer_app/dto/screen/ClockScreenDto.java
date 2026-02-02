package jotalac.market_viewer.market_viewer_app.dto.screen;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import jotalac.market_viewer.market_viewer_app.entity.screens.clock_screen.ClockType;
import jotalac.market_viewer.market_viewer_app.entity.screens.clock_screen.TimeFormat;
import jotalac.market_viewer.market_viewer_app.validation_annotation.ValidTimezone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClockScreenDto extends ScreenDto {
    @ValidTimezone
    private String timezone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String timezoneCode;

    @Pattern(regexp = "^0x[0-9a-fA-F]{4}", message = "Invalid RGB565 HEX color")
    private String bgColor;

    private ClockType clockType;
    private TimeFormat timeFormat;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CLOCK;
    }
}