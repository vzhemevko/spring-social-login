package org.vz.spring.social.login.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.vz.spring.social.login.config.OAuth2Provider;
import java.util.Collection;

@Getter
@Setter
public class User {
    
    private String name;
    private String email;
    private String imageUrl;
    private OAuth2Provider provider;
    private Collection<? extends GrantedAuthority> authorities;
}
