package jotalac.market_viewer.market_viewer_api.dto.api_key;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jotalac.market_viewer.market_viewer_api.entity.ApiKeyProvider;

public record ApiKeyCreateDto(
        @NotNull
        ApiKeyProvider endpoint,

        @NotBlank
        String keyValue
) {}
