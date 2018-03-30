package com.manager.store.dao.impl;

import com.manager.store.dao.RoleDAO;
import com.manager.store.entities.Role;
import org.springframework.stereotype.Repository;

/**
 * Implementation for RoleDAO.
 *
 * @author Marek
 */
@Repository("roleDAO")
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {

    private static final String ROLENAME_PARAM = "roleName";

    private static final String GET_ROLE_BY_ROLENAME = new StringBuilder("SELECT r")
            .append("   FROM Role r")
            .append("   WHERE r.roleName = :").append(ROLENAME_PARAM)
            .toString();

    public RoleDAOImpl() {
        this(Role.class);
    }

    public RoleDAOImpl(Class<Role> typeParameterClass) {
        super(typeParameterClass);
    }

    @Override
    public Role delete(final Long id) {
        return null;
    }

    @Override
    public Role getByRoleName(String roleName) {
        return (Role) getCurrentSession()
                .createQuery(GET_ROLE_BY_ROLENAME)
                .setParameter(ROLENAME_PARAM, roleName)
                .uniqueResult();
    }
}
