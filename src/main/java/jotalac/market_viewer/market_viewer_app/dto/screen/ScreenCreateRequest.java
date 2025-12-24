package jotalac.market_viewer.market_viewer_app.dto.screen;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jotalac.market_viewer.market_viewer_app.entity.screens.ScreenType;

public record ScreenCreateRequest(
        @NotNull
        ScreenType type
) {}
