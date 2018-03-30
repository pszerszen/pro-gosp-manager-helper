/**
 *
 */
package com.manager.store.mapper;

import com.manager.store.entities.Product;
import com.manager.store.entities.Store;
import com.manager.store.entities.Supply;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.SupplyModel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import static com.manager.store.utils.DateUtils.fromDateTime;
import static com.manager.store.utils.DateUtils.toDateTime;

/**
 * @author Marek
 */
@Component
public class SupplyMapper implements Mapper<Supply, SupplyModel> {

    @Override
    public Supply toEntity(SupplyModel model, Supply existingEntity, SessionFactory sessionFactory) {
        Supply ent = existingEntity == null ? new Supply() : existingEntity;

        ent.setId(model.getId());
        ent.setDate(toDateTime(model.getDate()));

        Product product = (Product) sessionFactory
                .getCurrentSession()
                .get(Product.class, model.getProduct().getId());
        ent.setProduct(product);

        Store receiver = (Store) sessionFactory
                .getCurrentSession()
                .get(Store.class, model.getStore().getId());
        ent.setReceiver(receiver);

        ent.setStatus(model.getStatus());

        ent.setQuantity(model.getQuantity());
        return ent;
    }

    @Override
    public SupplyModel toModel(Supply entity, SessionFactory sessionFactory) {
        SupplyModel mod = new SupplyModel();

        mod.setId(entity.getId());
        mod.setDate(fromDateTime(entity.getDate()));

        Product product = entity.getProduct();
        ModelSummary productModel = new ModelSummary(
                product.getId(),
                product.getName(),
                product.getModel() + " - " + product.getProducer());
        mod.setProduct(productModel);

        Store store = entity.getReceiver();
        ModelSummary storeModel = new ModelSummary(store.getId(), store.getName(), store.getAddress());
        mod.setStore(storeModel);

        mod.setStatus(entity.getStatus());

        mod.setQuantity(entity.getQuantity());

        return mod;
    }

    @Override
    public ModelSummary summaryFromEntity(final Supply entity) {
        return new ModelSummary(entity.getId(),
                fromDateTime(entity.getDate()),
                entity.getProduct().getName() + " x " + entity.getQuantity());
    }

    @Override
    public ModelSummary summaryFromModel(final SupplyModel model) {
        return new ModelSummary(model.getId(),
                model.getDate(),
                model.getProduct().getName() + " + " + model.getQuantity());
    }
}
