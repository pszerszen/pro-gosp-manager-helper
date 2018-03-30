/**
 *
 */
package com.manager.store.mapper;

import com.manager.store.entities.Role;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.RoleModel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * @author Marek
 *
 */
@Component
public class RoleMapper implements Mapper<Role, RoleModel>{

	@Override
    public Role toEntity(RoleModel model, Role existingEntity, SessionFactory sessionFactory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RoleModel toModel(Role entity, SessionFactory sessionFactory) {
        RoleModel mod = new RoleModel();

        mod.setId(entity.getId());

        return mod;
    }

    @Override
    public ModelSummary summaryFromEntity(final Role entity) {
        return null;
    }

    @Override
    public ModelSummary summaryFromModel(final RoleModel model) {
        return null;
    }
}
