package jotalac.market_viewer.market_viewer_app.dto.screen_data;

import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITextScreenDataDto extends ScreenDataDto {

    private String displayText;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.AI_TEXT;
    }
}