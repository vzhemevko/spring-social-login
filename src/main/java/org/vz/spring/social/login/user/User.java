package org.vz.spring.social.login.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.vz.spring.social.login.user.OAuth2UserInfoFactory.AuthProvider;

import java.util.Collection;

@Getter
@Setter
public class User {
    
    private String name;
    private String email;
    private AuthProvider provider;
    private Collection<? extends GrantedAuthority> authorities;
}
