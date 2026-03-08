package jotalac.market_viewer.market_viewer_app.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static jotalac.market_viewer.market_viewer_app.config.Constants.*;

public record RegisterRequestDto(

        @NotBlank
        @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "Username length is invalid")
        String username,

        @NotBlank
        @Size(min = PASSWORD_MIN_LENGTH, max =PASSWORD_MAX_LENGTH, message = "Password length is invalid")
        String password,

        @NotBlank
        @Size(min = PASSWORD_MIN_LENGTH, max =PASSWORD_MAX_LENGTH, message = "Password repeat length is invalid")
        String passwordRepeat
) {
        public RegisterRequestDto {
                if (username != null) {
                        username = username.trim();
                }
        }
}
