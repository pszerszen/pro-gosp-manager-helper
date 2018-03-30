package com.manager.store.service.impl;

import com.manager.store.entities.User;
import com.manager.store.entities.enums.RoleType;
import com.manager.store.model.PasswordChangeToken;
import com.manager.store.service.CredentialsService;
import com.manager.store.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("credentialsService")
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Value("${default.user.password}")
    private String defaultUserPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public User getCurrentUser() {
        return userService.getByMail(getCurrentUsername());
    }

    @Override
    public boolean hasCurrentUserRole(RoleType role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication
                .getAuthorities();
        for(SimpleGrantedAuthority auth : authorities){
            if(auth.getAuthority().equals(role.name())){
                return true;
            }
        }
        return false;
//        return authorities
//                .stream()
//                .anyMatch(auth -> role.name().equals(auth.getAuthority()));

    }

    @Override
    public boolean hasCurrentUserAnyOfRoles(List<RoleType> roles) {
        for (RoleType roleType : roles) {
            if (hasCurrentUserRole(roleType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserTheOriginAdmin(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isUserTheOriginAdmin(String mail) {
        // TODO switch comments
        //return userService.getByMail(mail).getPassword().equals(defaultUserPassword);
        return false;
    }

    @Override
    public void updatePasswordToCurrentUser(PasswordChangeToken passwordChangeToken) {
        User currentUser = getCurrentUser();

        if (!passwordEncoder.matches(passwordChangeToken.getOldPassword(), currentUser.getPassword())) {
            throw new SecurityException("Wrong password provided.");
        }
        if (!StringUtils.equals(passwordChangeToken.getPassword(), passwordChangeToken.getPasswordConfirmation())) {
            throw new SecurityException("New password and confirmation password do not match.");
        }

        if (!userService.setPasswordToUser(currentUser.getId(), passwordChangeToken.getPassword())) {
            throw new RuntimeException();
        }
    }
}
