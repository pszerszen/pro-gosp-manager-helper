/**
 *
 */
package com.manager.store.service.impl;

import com.manager.store.dao.RoleDAO;
import com.manager.store.entities.Role;
import com.manager.store.mapper.Mapper;
import com.manager.store.mapper.RoleMapper;
import com.manager.store.model.RoleModel;
import com.manager.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marek
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl extends AbstractService<Role, RoleModel> implements RoleService {

    @Autowired
    @Qualifier("roleDAO")
    private RoleDAO roleDAO;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getByRoleName(String roleName) {
        return roleDAO.getByRoleName(roleName);
    }

    @Override
    protected RoleDAO getDao() {
        return roleDAO;
    }

    @Override
    protected Mapper<Role, RoleModel> getMapper() {
        return roleMapper;
    }

}
