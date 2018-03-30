/**
 *
 */
package com.manager.store.service.impl;

import com.google.common.collect.ImmutableList;
import com.manager.store.dao.StoreDAO;
import com.manager.store.entities.Store;
import com.manager.store.mapper.Mapper;
import com.manager.store.mapper.StoreMapper;
import com.manager.store.model.ModelSummary;
import com.manager.store.model.StoreModel;
import com.manager.store.service.StoreService;
import com.manager.store.validators.ValidationMessagesConstants;
import com.manager.store.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.manager.store.validators.ValidationMessagesConstants.*;

/**
 * @author Marek
 */
@Service("storeService")
@Transactional
public class StoreServiceImpl extends AbstractService<Store, StoreModel> implements StoreService {

    @Autowired
    @Qualifier("storeDAO")
    private StoreDAO storeDAO;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Store getByName(String name) {
        return storeDAO.getByName(name);
    }

    @Override
    protected StoreDAO getDao() {
        return storeDAO;
    }

    @Override
    protected Mapper<Store, StoreModel> getMapper() {
        return storeMapper;
    }

    @Override
    public List<ModelSummary> searchSummaries(final String query, final int limit) {
        return getDao().search(query, limit, " AND s.active = true").stream()
                .map(getMapper()::summaryFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    protected List<Validator> getValidators(final StoreModel model) {
        return new ImmutableList.Builder<Validator>()
                .addAll(super.getValidators(model))
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if (!model.getPhone().replaceAll("-", "").replaceAll(" ", "").matches(ValidationMessagesConstants.TELEPHONE_REGEX)) {
                        errors.add(MESSAGE_INVALID_TELEPHONE);
                    }
                    return errors;
                })
                .add(() -> {
                    Set<String> errors = new HashSet<>();
                    if (!model.getName().matches(ALPHANUMERIC_SPACED_MIN_4_SIGN_REGEX)) {
                        errors.add(MESSAGE_INVALID_STORE_NAME);
                    }
                    Store store = getByName(model.getName());
                    if(!(store == null || Objects.equals(store.getId(), model.getId()))){
                        errors.add(MESSAGE_UNIQUE_STORE_NAME);
                    }
                    return errors;
                })
                .build();

    }

}
