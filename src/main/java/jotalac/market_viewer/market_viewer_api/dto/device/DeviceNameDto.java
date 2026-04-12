package jotalac.market_viewer.market_viewer_api.dto.device;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static jotalac.market_viewer.market_viewer_api.config.Constants.*;

public record DeviceNameDto(
        @NotBlank
        @Size(min = DEVICE_NAME_MIN_LENGTH, max = DEVICE_NAME_MAX_LENGTH, message = "Name length invalid")
        String name
) {
}
