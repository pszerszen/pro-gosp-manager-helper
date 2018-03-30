package com.manager.store.beans;

import com.google.common.collect.Sets;
import com.manager.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Piotr
 */
@Component
public class Authenticator implements UserDetailsService {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.manager.store.entities.User user = userService.getByMail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found!");
        }

        Set<GrantedAuthority> auth = Sets.newHashSet();
        user.getRoles().forEach(role -> {
            auth.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), auth);
    }

}
