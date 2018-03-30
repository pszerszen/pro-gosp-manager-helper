/**
 *
 */
package com.manager.store.dao;

import com.manager.store.entities.Product;

import java.util.List;

/**
 * Product DAO interface
 * @author Marek
 *
 */
public interface ProductDAO extends GenericDAO<Product> {
	Product getByName(String name);
	/*Zestawienie ilosci produktow [diagram slupkowy]
			Filtry:
			Sklep
			Produkt*/
	List<Product> getProduct(int store, int product);
}
