/**
 *
 */
package com.manager.store.mapper;

import com.manager.store.entities.Product;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.ProductModel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * @author Marek
 */
@Component
public class ProductMapper implements Mapper<Product, ProductModel> {

    @Override
    public Product toEntity(ProductModel model, Product existingEntity, SessionFactory sessionFactory) {

        Product ent = existingEntity == null ? new Product() : existingEntity;

        ent.setId(model.getId());
        ent.setModel(model.getModel());
        ent.setName(model.getName());
        ent.setProducer(model.getProducer());
        ent.setPurchasePrice(model.getPurchasePrice());
        ent.setSalesPrice(model.getPurchasePrice());
        ent.setActive(model.isActive());

        return ent;
    }

    @Override
    public ProductModel toModel(Product entity, SessionFactory sessionFactory) {
        ProductModel mod = new ProductModel();

        mod.setId(entity.getId());
        mod.setModel(entity.getModel());
        mod.setName(entity.getName());
        mod.setProducer(entity.getProducer());
        mod.setPurchasePrice(entity.getPurchasePrice());
        mod.setSalesPrice(entity.getSalesPrice());
        mod.setActive(entity.isActive());

        return mod;
    }

    @Override
    public ModelSummary summaryFromEntity(final Product entity) {
        return new ModelSummary(entity.getId(),
                entity.getName(),
                new StringBuilder(entity.getModel())
                        .append(entity.getProducer())
                        .toString());
    }

    @Override
    public ModelSummary summaryFromModel(final ProductModel model) {
        return new ModelSummary(model.getId(),
                model.getName(),
                new StringBuilder(model.getModel())
                        .append(model.getProducer())
                        .toString());
    }
}
