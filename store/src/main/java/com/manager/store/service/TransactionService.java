/**
 *
 */
package com.manager.store.service;

import com.manager.store.entities.Transaction;
import com.manager.store.model.TransactionModel;

import java.util.Date;
import java.util.List;

/**
 * @author Marek
 *
 */
public interface TransactionService extends DefaultService<Transaction, TransactionModel> {
	/**
	 * Get transaction by date.
	 *
	 * @param date
	 * @return
	 */
	List<Transaction> getByDate(Date dateFrom, Date dateTo);
	/**
	 * Get profit from transaction by product.
	 *
	 * @param dateFrom, dateTo, store, product, staff
	 * @return
	 */
	List<Transaction> getProfit(Date dateFrom, Date dateTo, int store, int product, int staff);
	
	/**
	 * Get profit from transaction by date.
	 *
	 * @param dateFrom, dateTo, store, product, staff, storeType
	 * @return
	 */
	List<Transaction> getProfitByDate(Date dateFrom, Date dateTo, int store, int product, int staff, int storeType);
	
	/**
	 * Get sales list
	 *
	 * @param dateFrom, dateTo, store, product, staff
	 * @return
	 */
	List<Transaction> getSales(Date dateFrom, Date dateTo, int store, int product, int staff) ;
}
