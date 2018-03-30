/**
 *
 */
package com.manager.store.service;

import com.manager.store.entities.Product;
import com.manager.store.model.ProductModel;

import java.util.List;

/**
 * Product service
 *
 * @author Marek
 *
 */
public interface ProductService extends DefaultService<Product, ProductModel> {
	/**
	 * Get a product by name.
	 *
	 * @param name
	 * @return
	 */
	Product getByName(String name);

	/**
	 * Get a list product .
	 *
	 * @param store
	 * @param product
	 * @return
	 */
	List<Product> getProduct(int store, int product);
}
