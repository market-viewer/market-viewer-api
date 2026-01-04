package jotalac.market_viewer.market_viewer_app.dto.screen.update;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITextScreenUpdateRequest extends ScreenUpdateRequest {

    @Size(min = 5, max = 500, message = "Prompt size is not valid")
    private String prompt;

    @Pattern(regexp = "^0x[0-9a-fA-F]{4}", message = "Invalid RGB565 HEX color")
    private String bgColor;

}
