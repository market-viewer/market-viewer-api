package jotalac.market_viewer.market_viewer_api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jotalac.market_viewer.market_viewer_api.entity.OAuthProvider;
import jotalac.market_viewer.market_viewer_api.entity.User;
import jotalac.market_viewer.market_viewer_api.service.auth.AuthService;
import jotalac.market_viewer.market_viewer_api.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final AuthService authService;

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        //get username
        OAuthProvider provider = OAuthProvider.GITHUB;
        String providerId = oAuth2User.getAttribute("id").toString();
        String username = oAuth2User.getAttribute("login");

        User user = authService.findOrCreateOAuthUser(username, provider, providerId);

        String token = jwtService.generateToken(user.getId());

        getRedirectStrategy().sendRedirect(request, response, frontendUrl+"/oauth2/github/callback?token=" + token);
    }


}
