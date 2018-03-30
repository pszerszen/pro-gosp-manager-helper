/**
 *
 */
package com.manager.store.dao;

import com.manager.store.entities.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Transaction DAO interface
 *
 * @author Marek
 *
 */
public interface TransactionDAO extends GenericDAO<Transaction> {

	List<Transaction> getByDate(Date dateFrom, Date dateTo);
	
	/*Zestawienie zyskow od czasu [Wykres Liniowy]
	Filtry :
	Sklep
	rodzaj sklepu
	pracownik
	okres czasu
	przychody  - do wyjasnienia o co chodzi ?
	Produkt*/
	List<Transaction> getProfitByDate(Date dateFrom, Date dateTo, int store, int product, int staff, int storeType);

	/*Zestawienie Sprzedanych towarow od czasu [Wykres slupkowy]
			Filtry:
			Okres czasu
			Sklep
			Produkt
			Pracownik*/
	List<Transaction> getSales(Date dateFrom, Date dateTo, int store, int product, int staff);
	/*Zestawieni zyskow [diagram kołowy]
			Filtry:
			Okres czasu
			Produkt
			Sklep
			Pracownik*/
	List<Transaction> getProfit(Date dateFrom, Date dateTo, int store, int product, int staff);
}
