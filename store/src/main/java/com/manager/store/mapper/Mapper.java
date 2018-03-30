package com.manager.store.mapper;

import com.manager.store.model.ModelSummary;
import org.hibernate.SessionFactory;

/**
 * A standard Entity-Model Mapper interface.
 *
 * @param <E>
 *            Entity type
 * @param <M>
 *            Model tpye
 *
 * @author Piotr
 */
public interface Mapper<E, M> {

    /**
     * Maps model to entity.
     *
     * @param model
     *            to map
     * @param existingEntity
     *            if mapping performed on model of already existing entity it's
     *            needed to be specified here
     * @param sessionFactory
     *            the sessionFactory
     * @return mapped entity or null if model was null
     */
    E toEntity(M model, E existingEntity, SessionFactory sessionFactory);

    /**
     * Maps the entity to model form.
     *
     * @param entity
     *            to map
     * @param sessionFactory
     *            the sessionFactory
     * @return mapped model or null if entity was null
     */
    M toModel(E entity, SessionFactory sessionFactory);

    ModelSummary summaryFromEntity(E entity);

    ModelSummary summaryFromModel(M model);
}
