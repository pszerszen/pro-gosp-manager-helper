package com.manager.store.service;

import com.manager.store.entities.User;
import com.manager.store.model.ContactModel;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.UserModel;

import java.util.List;

/**
 * UserService interface.
 *
 * @author Piotr
 */
public interface UserService extends DefaultService<User, UserModel> {

    /**
     * Gets a user by a mail.
     *
     * @param mail
     *            of searched user
     * @return
     */
    User getByMail(String mail);

    /**
     * Sets a new password to the specified user.
     *
     * @param id
     *            of user
     * @param password
     *            a plain-text password.
     * @return true if update successful
     */
    boolean setPasswordToUser(Long id, String password);

    List<ContactModel> getEmails(String term);

    List<ModelSummary> getWorkersSummaries();
}
