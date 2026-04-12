package jotalac.market_viewer.market_viewer_api.dto.device;

import java.util.UUID;

public record DeviceCreateResponse(
    Integer deviceId,
    UUID deviceHash
) {}
