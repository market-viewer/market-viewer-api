package jotalac.market_viewer.market_viewer_api.dto.user;

import jotalac.market_viewer.market_viewer_api.entity.ApiKeyProvider;

import java.util.List;

public record UsernameAndApiKeysDto(
    String username,
    List<ApiKeyProvider> apiKeyProviders
) {}
