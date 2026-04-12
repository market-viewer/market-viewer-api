package jotalac.market_viewer.market_viewer_api.dto.screen;

import com.fasterxml.jackson.annotation.JsonProperty;
import jotalac.market_viewer.market_viewer_api.entity.screens.ScreenType;
import jotalac.market_viewer.market_viewer_api.entity.screens.clock_screen.ClockType;
import jotalac.market_viewer.market_viewer_api.entity.screens.clock_screen.TimeFormat;
import jotalac.market_viewer.market_viewer_api.validation_annotation.ValidTimezone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClockScreenDto extends ScreenDto {
    @ValidTimezone
    private String timezone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String timezoneCode;

    private ClockType clockType;
    private TimeFormat timeFormat;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.CLOCK;
    }
}