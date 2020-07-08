package org.vz.spring.social.login.user;

import java.util.Map;

import static org.vz.spring.social.login.config.OAuth2Provider.*;

public class OAuth2UserInfoFactory {
    
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new RuntimeException("Login with " + registrationId + " is not supported yet.");
        }
    }
}
