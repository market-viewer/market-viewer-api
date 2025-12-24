package jotalac.market_viewer.market_viewer_app.dto.api_key;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyEndpoint;

public record ApiKeyCreateDto(
        @NotNull
        ApiKeyEndpoint endpoint,

        @NotBlank
        String keyValue
) {}
