package com.manager.store.service;

import com.manager.store.entities.AbstractEntity;
import com.manager.store.exceptions.ValidationException;
import com.manager.store.model.AbstractModel;
import com.manager.store.model.ModelSummary;

import java.util.List;
import java.util.Set;

/**
 * An interface of a standard entity-related service.
 *
 * @param <E> Entity type
 * @param <M> Model type
 * @author Piotr
 */
public interface DefaultService<E extends AbstractEntity, M extends AbstractModel> {

    /**
     * Performs a deletion process. In some cases this might be just a specific
     * entity update.
     *
     * @param model entity's model equivalent
     * @return deleted entity
     */
    E delete(M model);

    /**
     * Performs a deletion process for multiple entities.
     *
     * @param ids of entities to delete
     * @return true if  successful
     */
    boolean delete(List<Long> ids);

    /**
     * Finds specified entity and calls {@link #delete(AbstractModel)}.
     *
     * @param id of entity to delete
     * @return deleted entity
     */
    E delete(Long id);

    /**
     * Give model representation of the entity with specified id, or null if not
     * found.
     *
     * @param id of entity
     * @return model of the entity or null if not found.
     */
    M get(Long id);

    /**
     * Creates a new entity of posted model.
     *
     * @param model to map and save
     * @return created entity
     * @throws ValidationException if model not valid for saving
     */
    E create(M model) throws ValidationException;

    /**
     * Updates an existing entity of put model.
     *
     * @param model to map and update
     * @return updated entity
     * @throws ValidationException if model not valid or entity not found.
     */
    E update(M model) throws ValidationException;

    /**
     * Validates the specified model.
     *
     * @param model to validate
     * @return Set of validation messages. In case of valid model, empty set.
     */
    Set<String> validate(M model);

    List<M> search(String query, int limit);

    List<ModelSummary> searchSummaries(String query, int limit);

}
