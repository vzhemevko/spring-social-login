package org.vz.spring.social.login.controller;

import org.springframework.core.ResolvableType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.vz.spring.social.login.user.UserPrincipal;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    
    private static String authorizationRequestBaseUri = "oauth2/authorization";
    
    private final Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    
    public LoginController(final ClientRegistrationRepository clientRegistrationRepository,
                           final OAuth2AuthorizedClientService authorizedClientService) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
    }
    
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
    
        clientRegistrations.forEach(registration ->
                                        oauth2AuthenticationUrls.put(registration.getClientName(),
                                            authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        
        return "login";
    }
    
    @GetMapping("/home")
    public String getIndexPage(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        model.addAttribute("user_name", userPrincipal.getName());
    
        return "index";
    }
}
