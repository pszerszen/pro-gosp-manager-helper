package com.manager.store.dao.impl;

import com.manager.store.dao.TransactionDAO;
import com.manager.store.entities.Transaction;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Implementation for TransactionDAO.
 *
 * @author Marek
 */

@Repository("transactionDAO")
public class TransactionDAOImpl extends GenericDAOImpl<Transaction> implements TransactionDAO {

    private static final String DATEFROM_PARAM = "dateFrom";
    private static final String DATETO_PARAM = "dateTo";

    private static final String GET_TRANSACTION_BY_DATE = new StringBuilder("SELECT t")
            .append("   FROM Transaction t")
            .append("   WHERE t.date between :").append(DATEFROM_PARAM)
            .append("   and  :").append(DATETO_PARAM)
            .toString();

    public TransactionDAOImpl() {
        this(Transaction.class);
    }

    public TransactionDAOImpl(Class<Transaction> typeParameterClass) {
        super(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Transaction> getByDate(Date dateFrom, Date dateTo) {
        return (List<Transaction>) getCurrentSession()
                .createQuery(GET_TRANSACTION_BY_DATE)
                .setParameter(DATEFROM_PARAM, dateFrom)
                .setParameter(DATETO_PARAM, dateTo)
                .list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getProfit(Date dateFrom, Date dateTo, int store, int product, int staff) {
    	StringBuilder sb = new StringBuilder();
			sb.append("SELECT t.store, sum(t.quantity*(p.salesPrice - p.purchasePrice)) as profit ");
			sb.append("	FROM product p join p.transaction t ");
			sb.append(" WHERE 1 ");
			if(dateFrom != null){
				sb.append(" and t.date between :DATEFROM ");
				sb.append(" and  :DATETO ");
			}
			if(store > 0){
				sb.append(" and t.store :STORE ");
			}
			if(product > 0){
				sb.append(" and t.product :PRODUCT ");
			}
			if(staff > 0){
				sb.append(" and t.staff :STAFF ");
			}
			sb.append(" group by t.store ");
		Query sqlQuery = getCurrentSession().createQuery(sb.toString());
			if(dateFrom != null){
				sqlQuery.setParameter("DATEFROM", "'" + dateFrom + "'");
				sqlQuery.setParameter("DATETO", "'" + dateTo + "'");
			}
			if(store > 0){
				sqlQuery.setParameter("STORE", store);
			}
			if(product > 0){
				sqlQuery.setParameter("PRODUCT", product);
			}
			if(staff > 0){
				sqlQuery.setParameter("STAFF", staff);
			}
		return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getProfitByDate(Date dateFrom, Date dateTo, int store, int product, int staff, int storeType) {
    	StringBuilder sb = new StringBuilder();
			sb.append("SELECT t.date, sum(t.quantity*(p.salesPrice - p.purchasePrice)) as profit ");
			sb.append("	FROM product p join p.transaction t ");
			sb.append("	join t.store s  ");
			sb.append(" WHERE 1 ");
			if(dateFrom != null){
				sb.append(" and t.date between :DATEFROM ");
				sb.append(" and  :DATETO ");
			}
			if(store > 0){
				sb.append(" and t.store :STORE ");
			}
			if(storeType > 0){
				sb.append(" and s.type :STORETYPE ");
			}
			if(product > 0){
				sb.append(" and t.product :PRODUCT ");
			}
			if(staff > 0){
				sb.append(" and t.staff :STAFF ");
			}
			sb.append(" group by t.date ");
		Query sqlQuery = getCurrentSession().createQuery(sb.toString());
			if(dateFrom != null){
				sqlQuery.setParameter("DATEFROM", "'" + dateFrom + "'");
				sqlQuery.setParameter("DATETO", "'" + dateTo + "'");
			}
			if(store > 0){
				sqlQuery.setParameter("STORE", store);
			}
			if(storeType > 0){
				sqlQuery.setParameter("STORTYPE", storeType);
			}
			if(product > 0){
				sqlQuery.setParameter("PRODUCT", product);
			}
			if(staff > 0){
				sqlQuery.setParameter("STAFF", staff);
			}
		return sqlQuery.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getSales(Date dateFrom, Date dateTo, int store, int product, int staff) {
		StringBuilder sb =  new StringBuilder();
			sb.append("SELECT t.date, t.product, sum(t.quantity) as quantity ");
			sb.append("   FROM Transaction t");
			sb.append(" WHERE 1 ");
			if(dateFrom != null){
				sb.append(" and t.date between :DATEFROM ");
				sb.append(" and  :DATETO ");
			}
			if(store > 0){
				sb.append(" and t.store :STORE ");
			}
			if(product > 0){
				sb.append(" and t.product :PRODUCT ");
			}
			if(staff > 0){
				sb.append(" and t.staff :STAFF ");
			}
			sb.append(" group by t.date, t.product order by t.date, t.product");
			Query sqlQuery = getCurrentSession().createQuery(sb.toString());
				if(dateFrom != null){
					sqlQuery.setParameter("DATEFROM", "'" + dateFrom + "'");
					sqlQuery.setParameter("DATETO", "'" + dateTo + "'");
				}
				if(store > 0){
					sqlQuery.setParameter("STORE", store);
				}
				if(product > 0){
					sqlQuery.setParameter("PRODUCT", product);
				}
				if(staff > 0){
					sqlQuery.setParameter("STAFF", staff);
				}
			return sqlQuery.list();
    }
}
