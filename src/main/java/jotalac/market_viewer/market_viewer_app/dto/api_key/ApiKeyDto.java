package jotalac.market_viewer.market_viewer_app.dto.api_key;

import jotalac.market_viewer.market_viewer_app.entity.ApiKeyEndpoint;

public record ApiKeyDto (
   String value,
   ApiKeyEndpoint endpoint
) {}
