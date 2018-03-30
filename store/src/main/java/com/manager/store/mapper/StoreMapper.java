/**
 *
 */
package com.manager.store.mapper;

import com.manager.store.entities.Store;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.StoreModel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * @author Marek
 */
@Component
public class StoreMapper implements Mapper<Store, StoreModel> {

    @Override
    public Store toEntity(StoreModel model, Store existingEntity, SessionFactory sessionFactory) {
        Store ent = existingEntity == null ? new Store() : existingEntity;

        ent.setId(model.getId());
        ent.setActive(model.isActive());
        ent.setAddress(model.getAddress());
        ent.setName(model.getName());
        ent.setPhone(model.getPhone());
        ent.setType(model.getType());

        return ent;
    }

    @Override
    public StoreModel toModel(Store entity, SessionFactory sessionFactory) {
        StoreModel model = new StoreModel();

        model.setId(entity.getId());
        model.setActive(entity.isActive());
        model.setAddress(entity.getAddress());
        model.setName(entity.getName());
        model.setPhone(entity.getPhone());
        model.setType(entity.getType());

        return model;
    }

    @Override
    public ModelSummary summaryFromEntity(final Store entity) {
        return new ModelSummary(entity.getId(), entity.getName(), entity.getAddress());
    }

    @Override
    public ModelSummary summaryFromModel(final StoreModel model) {
        return new ModelSummary(model.getId(), model.getName(), model.getAddress());
    }
}
