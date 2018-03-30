/**
 *
 */
package com.manager.store.service;

import com.manager.store.entities.Supply;
import com.manager.store.model.SupplyModel;

import java.util.Date;
import java.util.List;

/**
 * @author Marek
 *
 */
public interface SupplyService extends DefaultService<Supply, SupplyModel> {
	/**
	 * Get supply by date
	 *
	 * @param date
	 * @return
	 */
	List<Supply> getByDate(Date date);
	
	/**
	 * Get supply list
	 *
	 * @param dateFrom, dateTo, store, product
	 * @return
	 */
	List<Supply> getSupply(Date dateFrom, Date dateTo, int store, int product);
	
}
