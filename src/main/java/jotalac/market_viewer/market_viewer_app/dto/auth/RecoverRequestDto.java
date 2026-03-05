package jotalac.market_viewer.market_viewer_app.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static jotalac.market_viewer.market_viewer_app.config.Constants.PASSWORD_MAX_LENGTH;
import static jotalac.market_viewer.market_viewer_app.config.Constants.PASSWORD_MIN_LENGTH;

public record RecoverRequestDto(
        @NotBlank
        String username,

        @NotBlank
        String recoverCode,

        @NotBlank
        @Size(min = PASSWORD_MIN_LENGTH, max =PASSWORD_MAX_LENGTH, message = "Password length is invalid")
        String newPassword,

        @NotBlank
        @Size(min = PASSWORD_MIN_LENGTH, max =PASSWORD_MAX_LENGTH, message = "Password repeat length is invalid")
        String newPasswordRepeat
) {
}
