package com.hcv.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {
    public MyUser(String username, String password,
                  boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked,
                  Collection<? extends GrantedAuthority> authorities) {
        super(username, password,
                true, true,
                true, true,
                authorities);
    }
}
