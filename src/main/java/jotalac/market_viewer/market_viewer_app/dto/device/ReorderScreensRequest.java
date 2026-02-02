package jotalac.market_viewer.market_viewer_app.dto.device;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReorderScreensRequest(
        @NotNull
        List<Integer> newOrder
) {}
