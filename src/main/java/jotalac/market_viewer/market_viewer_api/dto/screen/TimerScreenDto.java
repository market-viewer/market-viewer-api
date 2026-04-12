package jotalac.market_viewer.market_viewer_api.dto.screen;

import jakarta.validation.constraints.Size;
import jotalac.market_viewer.market_viewer_api.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerScreenDto extends ScreenDto {

    @Size(min=1, max = 20, message = "Timer name length not valid")
    private String name;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.TIMER;
    }
}
