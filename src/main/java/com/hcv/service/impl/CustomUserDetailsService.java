package com.hcv.service.impl;

import com.hcv.dto.MyUser;
import com.hcv.dto.RoleDTO;
import com.hcv.dto.UserDTO;
import com.hcv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = userService.findOneByUsername(username);
        if (userDTO == null) {
            throw new UsernameNotFoundException("User " + username + " not exist !!!");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(STR."ROLE_\{role.getCode()}"));
        }

        return new MyUser(
                username, userDTO.getPassword(),
                true, true,
                true, true,
                authorities
        );

    }
}
