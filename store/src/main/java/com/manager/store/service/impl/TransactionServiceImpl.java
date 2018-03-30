/**
 *
 */
package com.manager.store.service.impl;

import com.manager.store.dao.ProductStateDAO;
import com.manager.store.dao.TransactionDAO;
import com.manager.store.entities.ProductState;
import com.manager.store.entities.Transaction;
import com.manager.store.mapper.Mapper;
import com.manager.store.mapper.TransactionMapper;
import com.manager.store.model.TransactionModel;
import com.manager.store.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Marek
 */
@Service("transactionService")
@Transactional

public class TransactionServiceImpl extends AbstractService<Transaction, TransactionModel> implements TransactionService {

    @Autowired
    @Qualifier("transactionDAO")
    private TransactionDAO transactionDAO;

    @Autowired
    private ProductStateDAO productStateDAO;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public Transaction delete(final Long id) {
        Transaction transaction = transactionDAO.get(id);

        ProductState productState = productStateDAO.getByProductAndStore(transaction.getProduct().getId(), transaction.getStore().getId());
        productState.setQuantity(productState.getQuantity() + transaction.getQuantity());
        productStateDAO.merge(productState);

        return super.delete(id);
    }

    @Override
    public List<Transaction> getByDate(Date dateFrom, Date dateTo) {
        return transactionDAO.getByDate(dateFrom, dateTo);
    }
    @Override
    public List<Transaction> getProfit(Date dateFrom, Date dateTo, int store, int product, int staff) {
    	return transactionDAO.getProfit(dateFrom, dateTo, store, product, staff);
    }
    
    @Override
    public List<Transaction> getProfitByDate(Date dateFrom, Date dateTo, int store, int product, int staff, int storeType) {
    	return transactionDAO.getProfitByDate(dateFrom, dateTo, store, product, staff, storeType);
    }
    
    @Override
    public List<Transaction> getSales(Date dateFrom, Date dateTo, int store, int product, int staff) {
    	return transactionDAO.getSales(dateFrom, dateTo, store, product, staff);
    }
    
    @Override
    protected TransactionDAO getDao() {
        return transactionDAO;
    }

    @Override
    protected Mapper<Transaction, TransactionModel> getMapper() {
        return transactionMapper;
    }

}
