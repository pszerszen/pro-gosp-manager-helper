/**
 * 
 */
package com.manager.store.service;

import com.manager.store.entities.Store;
import com.manager.store.model.StoreModel;

/**
 * @author Marek
 *
 */
public interface StoreService extends DefaultService<Store, StoreModel> {
	/**
	 * Get store by name.
	 * @param name
	 * @return
	 */
	Store getByName(String name);

}
