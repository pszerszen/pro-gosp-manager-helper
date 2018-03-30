/**
 *
 */
package com.manager.store.mapper;

import com.manager.store.entities.ProductState;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.ProductStateModel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * @author Marek
 *
 */
@Component
public class ProductStateMapper implements Mapper<ProductState, ProductStateModel>{

	@Override
	public ProductState toEntity(ProductStateModel model, ProductState existingEntity, SessionFactory sessionFactory) {
		ProductState ent = existingEntity == null ? new ProductState(): existingEntity;

		ent.setId(model.getId());

        return ent;
    }
	@Override
    public ProductStateModel toModel(ProductState entity, SessionFactory sessionFactory) {
		ProductStateModel mod = new ProductStateModel();

		mod.setId(entity.getId());

        return mod;
    }

	@Override
	public ModelSummary summaryFromEntity(final ProductState entity) {
		return null;
	}

	@Override
	public ModelSummary summaryFromModel(final ProductStateModel model) {
		return null;
	}
}
