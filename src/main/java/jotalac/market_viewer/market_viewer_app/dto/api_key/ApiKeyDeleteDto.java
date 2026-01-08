package jotalac.market_viewer.market_viewer_app.dto.api_key;

import jakarta.validation.constraints.NotNull;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;

public record ApiKeyDeleteDto (
        @NotNull(message = "Required valid endpoint")
        ApiKeyProvider endpoint
) {}
