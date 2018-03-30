package com.manager.store.service;

import com.manager.store.entities.User;
import com.manager.store.entities.enums.RoleType;
import com.manager.store.model.PasswordChangeToken;

import java.util.List;

public interface CredentialsService {

    String getCurrentUsername();

    User getCurrentUser();

    boolean hasCurrentUserRole(RoleType role);

    boolean hasCurrentUserAnyOfRoles(List<RoleType> roles);

    /**
     * Checks if the given user is the origin application user (has the default
     * password).
     *
     * @param id
     *            of checked user
     *
     * @return true if user has default password
     */
    boolean isUserTheOriginAdmin(Long id);

    /**
     * Checks if the given user is the origin application user (has the default
     * password).
     *
     * @param mail
     *            of the checked user
     *
     * @return true if user has default password
     */
    boolean isUserTheOriginAdmin(String mail);

    void updatePasswordToCurrentUser(PasswordChangeToken passwordChangeToken);

}
