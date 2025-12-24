package jotalac.market_viewer.market_viewer_app.dto.screen;

import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;

public record ScreenDto(
        Integer id,
        ScreenType type
) {
}
