package jotalac.market_viewer.market_viewer_api.dto.device;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record ScreensDeleteRequest(
        @NotEmpty(message = "Screen IDs list cannot be empty")
        @Size(max = 100, message = "Cannot delete more than 100 screens at once")
        Set<@NotNull(message = "Screen ID cannot be null") @Positive(message = "Screen ID must be a positive number") Integer> screenIds
) {
}
