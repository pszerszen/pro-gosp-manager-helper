/**
 * 
 */
package com.manager.store.dao;

import com.manager.store.entities.Role;

/**
 * Role DAO interface
 * 
 * @author Marek
 *
 */
public interface RoleDAO extends GenericDAO<Role> {
	Role getByRoleName(String roleName);
}
