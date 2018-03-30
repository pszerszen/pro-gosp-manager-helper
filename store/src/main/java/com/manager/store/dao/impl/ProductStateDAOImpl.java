package com.manager.store.dao.impl;

import com.manager.store.dao.ProductStateDAO;
import com.manager.store.entities.ProductState;
import org.springframework.stereotype.Repository;

/**
 * Implementation for ProductStateDAO
 *
 * @author Marek
 */
@Repository("productStateDAO")

public class ProductStateDAOImpl extends GenericDAOImpl<ProductState> implements ProductStateDAO {

    private static final String PRODUCT_PARAM = "product";

    private static final String GET_PRODUCTSTATE_BY_PRODUCT = new StringBuilder("SELECT p")
            .append("   FROM ProuctState p")
            .append("   WHERE p.product = :").append(PRODUCT_PARAM)
            .toString();

    public ProductStateDAOImpl() {
        this(ProductState.class);
    }

    public ProductStateDAOImpl(Class<ProductState> typeParameterClass) {
        super(typeParameterClass);
    }

    public ProductState getByProductAndStore(Long product, Long store){
        String query = "FROM ProductState p WHERE p.product.id = :product AND p.store.id = :store";
        return (ProductState) getCurrentSession().createQuery(query)
                .setParameter("product", product)
                .setParameter("store", store)
                .uniqueResult();
    }

    @Override
    public ProductState getByProduct(String product) {
        return (ProductState) getCurrentSession()
                .createQuery(GET_PRODUCTSTATE_BY_PRODUCT)
                .setParameter(PRODUCT_PARAM, product)
                .uniqueResult();
    }

}
