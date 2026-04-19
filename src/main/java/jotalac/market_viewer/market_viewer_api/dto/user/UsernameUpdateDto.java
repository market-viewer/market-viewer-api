package jotalac.market_viewer.market_viewer_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static jotalac.market_viewer.market_viewer_api.config.Constants.USERNAME_MAX_LENGTH;
import static jotalac.market_viewer.market_viewer_api.config.Constants.USERNAME_MIN_LENGTH;

public record UsernameUpdateDto(

        @NotBlank
        @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "Username length is invalid")
        String username
) {
}
