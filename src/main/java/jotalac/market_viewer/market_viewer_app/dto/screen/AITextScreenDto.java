package jotalac.market_viewer.market_viewer_app.dto.screen;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

import static jotalac.market_viewer.market_viewer_app.config.Constants.DEFAULT_BG_COLOR;

@Getter
@Setter
public class AITextScreenDto extends ScreenDto {

    @Size(min = 5, max = 500, message = "Prompt size is not valid")
    private String prompt;

    @Pattern(regexp = "^0x[0-9a-fA-F]{4}", message = "Invalid RGB565 HEX color")
    private String bgColor = DEFAULT_BG_COLOR;

    @Min(value = 1, message = "Minimal fetch interval is 1 hour")
    private Integer fetchIntervalHours;


    @Override
    public ScreenType getScreenType() {
        return ScreenType.AI_TEXT;
    }
}