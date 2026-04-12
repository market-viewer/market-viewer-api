package jotalac.market_viewer.market_viewer_api.dto.auth;

import java.util.List;

public record RegisterResponseDto(
        String message,
        List<String> recoveryCodes
) {}
