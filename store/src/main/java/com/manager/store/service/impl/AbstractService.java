package com.manager.store.service.impl;

import com.google.common.collect.Lists;
import com.manager.store.dao.GenericDAO;
import com.manager.store.entities.AbstractEntity;
import com.manager.store.exceptions.ValidationException;
import com.manager.store.mapper.Mapper;
import com.manager.store.model.AbstractModel;
import com.manager.store.model.ModelSummary;
import com.manager.store.service.DefaultService;
import com.manager.store.validators.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An abstract service containing common operations.
 *
 * @param <E> Entity type
 * @param <M> Model type
 * @author Piotr
 */
@Transactional
public abstract class AbstractService<E extends AbstractEntity, M extends AbstractModel> implements DefaultService<E, M> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * @return a default DAO for this page
     */
    protected abstract GenericDAO<E> getDao();

    /**
     * @return a default mapper for this page
     */
    protected abstract Mapper<E, M> getMapper();

    /**
     * @param model TODO
     * @return List of validators to be launched before DB save/update.
     */
    protected List<Validator> getValidators(M model) {
        return Lists.newArrayList();
    }

    @Override
    public E delete(final M model) {
        return delete(model.getId());
    }

    @Override
    public boolean delete(List<Long> ids){
        ids.forEach(this::delete);
        return true;
    }

    @Override
    @Transactional
    public E delete(Long id) {
        return getDao().delete(id);
    }

    @Override
    public M get(Long id) {
        return getMapper().toModel(getDao().get(id), sessionFactory);
    }

    @Override
    public E create(M model) throws ValidationException {
        assertModelCorrect(model);

        E entity = getMapper().toEntity(model, null, sessionFactory);
        getDao().save(entity);

        return entity;
    }

    @Override
    public E update(M model) throws ValidationException {
        assertModelCorrect(model);

        E existingEntity = getDao().get(model.getId());
        if (existingEntity == null) {
            throw new ValidationException();
        }

        E entity = getMapper().toEntity(model, existingEntity, sessionFactory);
        return getDao().merge(entity);
    }

    @Override
    public Set<String> validate(M model) {
        return getValidators(model).stream()
                .flatMap(validator -> validator.validate().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public List<M> search(final String query, final int limit) {
        return getDao().search(query, limit, null).stream()
                .map(e -> getMapper().toModel(e, sessionFactory))
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelSummary> searchSummaries(final String query, final int limit) {
        return getDao().search(query, limit, null).stream()
                .map(getMapper()::summaryFromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Validates the model and throws {@link ValidationException} if model not
     * valid.
     *
     * @param model to validate.
     * @throws ValidationException if any validation doesn't pass.
     */
    protected void assertModelCorrect(M model) throws ValidationException {
        final Set<String> validationMessages = validate(model);
        if (CollectionUtils.isNotEmpty(validationMessages)) {
            String msg = String.format("Attempted to persist invalid component %s.\nThe validation errors are: %s",
                    model.toString(),
                    validationMessages
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",", "[", "]")));
            LOG.warn(msg);
            throw new ValidationException(msg);
        }
    }

}
