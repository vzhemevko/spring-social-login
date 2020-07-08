package org.vz.spring.social.login.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.vz.spring.social.login.data.DataSource;
import org.vz.spring.social.login.user.OAuth2UserInfo;
import org.vz.spring.social.login.user.User;
import org.vz.spring.social.login.user.UserPrincipal;

import static org.vz.spring.social.login.config.OAuth2Provider.*;
import static org.vz.spring.social.login.user.OAuth2UserInfoFactory.*;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    
    private final DataSource dataSource;
    
    public OAuth2UserService(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        
        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(
            oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }
        
        User user = dataSource.findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            user = updateExistingUser(user, oAuth2UserRequest, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo, oAuth2User);
        }
        return new UserPrincipal(user, oAuth2User);
    }
    
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest,
                                 OAuth2UserInfo oAuth2UserInfo,
                                 OAuth2User oAuth2User) {
        User user = new User();
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setProvider(valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setAuthorities(oAuth2User.getAuthorities());
        
        return dataSource.saveUserByEmail(user.getEmail(), user);
    }
    
    private User updateExistingUser(User existingUser,
                                    OAuth2UserRequest oAuth2UserRequest,
                                    OAuth2UserInfo oAuth2UserInfo) {
        verifyProvider(existingUser, oAuth2UserRequest);
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setEmail(oAuth2UserInfo.getEmail());
        existingUser.setImageUrl(existingUser.getImageUrl());
    
        return dataSource.saveUserByEmail(existingUser.getEmail(), existingUser);
    }
    
    private void verifyProvider(User user, OAuth2UserRequest oAuth2UserRequest) {
        if (!user.getProvider().equals(valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
            throw new RuntimeException(
                "You're signed up with "  + user.getProvider() + " account. " +
                "Please use your " + user.getProvider() + " account to login."
            );
        }
    }
}
