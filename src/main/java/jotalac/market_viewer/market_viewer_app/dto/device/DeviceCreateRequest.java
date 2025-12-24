package jotalac.market_viewer.market_viewer_app.dto.device;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static jotalac.market_viewer.market_viewer_app.config.ValidationConstants.*;

public record DeviceCreateRequest(
        @NotBlank
        @Size(min = DEVICE_NAME_MIN_LENGTH, max = DEVICE_NAME_MAX_LENGTH, message = "Name length invalid")
        String name
) {
}
