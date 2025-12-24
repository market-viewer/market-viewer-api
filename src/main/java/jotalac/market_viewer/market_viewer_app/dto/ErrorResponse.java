package jotalac.market_viewer.market_viewer_app.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        String message,
        String path
) {}
