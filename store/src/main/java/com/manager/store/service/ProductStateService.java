/**
 * 
 */
package com.manager.store.service;

import com.manager.store.entities.ProductState;
import com.manager.store.model.ProductStateModel;

/**
 * ProductState interface
 * 
 * @author Marek
 *
 *
 */
public interface ProductStateService extends DefaultService<ProductState, ProductStateModel> {
		/**
		 * Get product state by product
		 * 
		 * @param product
		 * @return
		 */
		ProductState getByProduct(String product);
	
}
