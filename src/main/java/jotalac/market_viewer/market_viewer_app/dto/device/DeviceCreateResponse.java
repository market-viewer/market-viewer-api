package jotalac.market_viewer.market_viewer_app.dto.device;

import java.util.UUID;

public record DeviceCreateResponse(
    Integer deviceId,
    UUID deviceHash
) {}
