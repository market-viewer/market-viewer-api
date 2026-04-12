package jotalac.market_viewer.market_viewer_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotBlank
        String username,

        @NotNull
        String password
) {}
