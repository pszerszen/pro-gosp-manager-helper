package com.manager.store.dao;

import com.manager.store.entities.User;

import java.util.List;

/**
 * User DAO interface.
 *
 * @author Piotr
 */
public interface UserDAO extends GenericDAO<User> {

    User getByMail(String mail, boolean activeUsersOnly);
    List<User> getWorkers();
}
