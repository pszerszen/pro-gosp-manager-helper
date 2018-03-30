/**
 *
 */
package com.manager.store.dao;

import com.manager.store.entities.ProductState;

/**
 * ProductState DAO interface
 *
 * @author Marek
 */
public interface ProductStateDAO extends GenericDAO<ProductState> {

    ProductState getByProductAndStore(Long product, Long store);

    ProductState getByProduct(String product);
}
