package org.vz.spring.social.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.vz.spring.social.login.user.UserPrincipal;

import static org.vz.spring.social.login.config.OAuth2Provider.*;

@Controller
public class LoginController {
    
    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute(facebook.toString(), authorizationRequestBaseUri + "/" + facebook.toString());
        model.addAttribute(github.toString(), authorizationRequestBaseUri + "/" + github.toString());
        model.addAttribute(google.toString(), authorizationRequestBaseUri + "/" + google.toString());
        
        return "login";
    }
    
    @GetMapping("/home")
    public String getIndexPage(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        model.addAttribute("user_name", userPrincipal.getName());
        model.addAttribute("user_avatar", userPrincipal.getImageUrl());
    
        return "index";
    }
}
