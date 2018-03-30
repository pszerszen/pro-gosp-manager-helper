/**
 * 
 */
package com.manager.store.service;

import com.manager.store.entities.Role;
import com.manager.store.model.RoleModel;

/**
 * @author Marek
 *
 */
public interface RoleService extends DefaultService<Role, RoleModel> {

    /**
     * Get a role by RoleName
     * 
     * @param roleName
     * @return
     */
    Role getByRoleName(String roleName);

}
