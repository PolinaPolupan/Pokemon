package example.client;

import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UiController {
    @GetMapping("/")
    public String getIndex(Model model, Authentication auth) {
        model.addAttribute("name",
                auth instanceof OAuth2AuthenticationToken oauth && oauth.getPrincipal() instanceof OidcUser oidc
                        ? oidc.getPreferredUsername()
                        : "");
        model.addAttribute("isAuthenticated",
                auth != null && auth.isAuthenticated());
        model.addAttribute("isUser",
                auth != null && auth.getAuthorities().stream().anyMatch(authority ->
                        Objects.equals("USER", authority.getAuthority())));
        return "index.html";
    }

    @GetMapping("/user")
    public String getUser(Model model, Authentication auth) {
        return "user.html";
    }
}