/**
 * 
 */
package com.manager.store.dao;

import com.manager.store.entities.Store;

/**
 * Store DAO interface
 * 
 * @author Marek
 *
 */
public interface StoreDAO extends GenericDAO<Store> {
	Store getByName(String name);
	
}
