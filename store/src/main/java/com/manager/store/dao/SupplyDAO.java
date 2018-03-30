/**
 *
 */
package com.manager.store.dao;

import com.manager.store.entities.Supply;

import java.util.Date;
import java.util.List;

/**
 * Supply DAO interface
 *
 * @author Marek
 *
 */
public interface SupplyDAO extends GenericDAO<Supply> {
	List<Supply> getByDate(Date date);
	/*Zestawienie Dostaw od Czasu [Wykres slupkowy]
			Filtry :
			Produkt
			Sklep
			Okres Czasu*/
	List<Supply> getSupply(Date dateFrom, Date dateTo, int store, int product);
}
