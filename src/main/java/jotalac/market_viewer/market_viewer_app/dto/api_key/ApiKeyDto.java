package jotalac.market_viewer.market_viewer_app.dto.api_key;

import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;

public record ApiKeyDto (
   String value,
   ApiKeyProvider endpoint
) {}
