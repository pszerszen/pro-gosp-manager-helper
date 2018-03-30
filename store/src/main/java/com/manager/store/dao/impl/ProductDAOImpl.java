package com.manager.store.dao.impl;

import com.manager.store.dao.ProductDAO;
import com.manager.store.entities.Product;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for ProductDAO
 *
 * @author Marek
 */
@Repository("productDAO")
public class ProductDAOImpl extends GenericDAOImpl<Product> implements ProductDAO {

    private static final String NAME_PARAM = "name";

    private static final String GET_PRODUCT_BY_NAME = new StringBuilder("SELECT p")
            .append("   FROM Product p")
            .append("   WHERE p.name = :").append(NAME_PARAM)
            .toString();

    public ProductDAOImpl() {
        this(Product.class);
    }

    public ProductDAOImpl(Class<Product> typeParameterClass) {
        super(typeParameterClass);
    }

    @Override
    public Product getByName(String name) {
        return (Product) getCurrentSession()
                .createQuery(GET_PRODUCT_BY_NAME)
                .setParameter(NAME_PARAM, name)
                .uniqueResult();
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProduct(int store, int product) {
    	StringBuilder sb = new StringBuilder();
			sb.append("SELECT  sum(p.quantity)  as quantity, p.product ");
			sb.append(" from productState p ");
			sb.append(" WHERE 1 ");
			if(store > 0){
				sb.append(" and p.store :STORE ");
			}
			if(product > 0){
				sb.append(" and p.product :PRODUCT ");
			}
			sb.append(" group by p.product order p.product ");
		Query sqlQuery = getCurrentSession().createQuery(sb.toString());
			if(store > 0){
				sqlQuery.setParameter("STORE", store);
			}
			if(product > 0){
				sqlQuery.setParameter("PRODUCT", product);
			}
	   return sqlQuery.list();
    }

    @Override
    public Product delete(final Long id) {
        Product product = get(id);
        if(product == null){
            return null;
        }
        product.setActive(false);

        return merge(product);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> search(final String term, final int limit, final String additionalQueryConditions) {
        if (StringUtils.isBlank(term)) {
            return super.search(term, limit, additionalQueryConditions);
        }
        String formula = "FROM Product p " +
                "   WHERE p.name LIKE :term " +
                "       OR p.model LIKE :term " +
                "       OR p.producer LIKE :term";
        Query sqlQuery = getCurrentSession().createQuery(formula)
                .setParameter("term", "%" + term + "%")
                .setMaxResults(limit);
        return sqlQuery.list();
    }
}
