package org.vz.spring.social.login.data;

import org.springframework.stereotype.Component;
import org.vz.spring.social.login.user.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataSource {
    // Normally the data would be stored in a database or some other datasource
    // This project is aimed to demonstrate  how to implement Spring social login
    // Therefore the data is kept in memory.
    private Map<String, User> userRepo = new HashMap<>();
    
    public User findUserByEmail(String email) {
        return userRepo.get(email);
    }

    public User saveUserByEmail(String email, User user) {
        userRepo.put(email, user);
        return user;
    }
}
