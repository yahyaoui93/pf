package io.maxilog.security;

import io.maxilog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mossa on 06/11/2017.
 */
public final class UserFactory {

    private UserFactory() {
    }

    public static User create(JwtUser user) {
        User u = new User();
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setPhoto(user.getPhoto());
        u.setRole(user.getAuthorities().iterator().next().toString());
        u.setEnabled(user.isEnabled());
        u.setLastPasswordResetDate(user.getLastPasswordResetDate());
        return u;
    }
}