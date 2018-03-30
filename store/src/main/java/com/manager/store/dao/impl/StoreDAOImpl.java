package com.manager.store.dao.impl;

import com.manager.store.dao.StoreDAO;
import com.manager.store.entities.Store;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for StoreDAO.
 *
 * @author Marek
 */
@Repository("storeDAO")

public class StoreDAOImpl extends GenericDAOImpl<Store> implements StoreDAO {

    private static final String NAME_PARAM = "name";

    private static final String GET_STORE_BY_NAME = new StringBuilder("SELECT s")
            .append("   FROM Store s")
            .append("   WHERE s.name = :").append(NAME_PARAM)
            .toString();

    public StoreDAOImpl() {
        this(Store.class);
    }

    public StoreDAOImpl(Class<Store> typeParameterClass) {
        super(typeParameterClass);
    }

    @Override
    public Store delete(final Long id) {
        Store store = get(id);
        if (store == null) {
            return null;
        }
        store.setActive(false);

        return merge(store);
    }

    @Override
    public Store getByName(String name) {
        return (Store) getCurrentSession()
                .createQuery(GET_STORE_BY_NAME)
                .setParameter(NAME_PARAM, name)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Store> search(final String query, final int limit, final String additionalQueryConditions) {
        if (StringUtils.isBlank(query)) {
            return super.search(query, limit, additionalQueryConditions);
        }
        @SuppressWarnings("JpaQlInspection")
        String formula = "FROM Store s " +
                "WHERE " +
                "   (s.name LIKE :term " +
                "   OR s.address LIKE :term " +
                "   OR s.type LIKE :term)";
        if (StringUtils.isNotBlank(additionalQueryConditions)) {
            formula += additionalQueryConditions;
        }

        Query sqlQuery = getCurrentSession().createQuery(formula)
                .setParameter("term", "%" + query + "%")
                .setMaxResults(limit);
        return sqlQuery.list();
    }
}
