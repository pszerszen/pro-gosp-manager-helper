package com.manager.store.controller;

import com.manager.store.entities.Transaction;
import com.manager.store.model.TransactionModel;
import com.manager.store.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = TransactionsController.SECTION_NAME)
public class TransactionsController extends AbstractCrudController<Transaction, TransactionModel> {

    protected static final String SECTION_NAME = "transactions";

    @Autowired
    @Qualifier("transactionService")
    private TransactionService service;

    @Override
    protected TransactionModel setUpNewModel() {
        return new TransactionModel();
    }

    @Override
    protected TransactionService getService() {
        return service;
    }

    @Override
    protected String getSectionName() {
        return SECTION_NAME;
    }

    @Override
    protected String getMainSectionPage() {
        return "transactions/TransactionsMain";
    }

    @Override
    protected String getDetailsPage() {
        return "transactions/TransactionsDetails";
    }

    @Override
    protected String getPageTitle() {
        return "Transactions";
    }

    protected List<Transaction> getProfit(Date dateFrom, Date dateTo, int store, int product, int staff){
    	return service.getProfit(dateFrom, dateTo, store, product, staff);
    }

    protected List<Transaction> getProfitByDate(Date dateFrom, Date dateTo, int store, int product, int staff, int storeType){
    	return service.getProfitByDate(dateFrom, dateTo, store, product, staff, storeType);
    }

    protected List<Transaction> getSales(Date dateFrom, Date dateTo, int store, int product, int staff){
    	return service.getSales(dateFrom, dateTo, store, product, staff);

    }

}
