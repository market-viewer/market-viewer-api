package jotalac.market_viewer.market_viewer_api.dto.screen;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jotalac.market_viewer.market_viewer_api.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITextScreenDto extends ScreenDto {

    @Size(min = 5, max = 500, message = "Prompt size is not valid")
    private String prompt;

    @Min(value = 1, message = "Minimal fetch interval is 1 hour")
    private Integer fetchIntervalHours;


    @Override
    public ScreenType getScreenType() {
        return ScreenType.AI_TEXT;
    }
}