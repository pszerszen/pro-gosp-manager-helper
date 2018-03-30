package com.manager.store.dao.impl;

import com.manager.store.dao.SupplyDAO;
import com.manager.store.entities.Supply;
import com.manager.store.entities.enums.SupplyStatus;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Implementation for SupplyDAO.
 *
 * @author Marek
 */

@Repository("supplyDAO")
public class SupplyDAOImpl extends GenericDAOImpl<Supply> implements SupplyDAO {

    private static final String DATE_PARAM = "date";
    private static final String GET_SUPPLY_BY_DATE = new StringBuilder("SELECT s")
            .append("   FROM Supply s")
            .append("   WHERE s.date = :").append(DATE_PARAM)
            .toString();
    public SupplyDAOImpl() {
        this(Supply.class);
    }

    @Override
    public Supply delete(final Long id) {
        Supply supply = get(id);
        if(supply == null || supply.getStatus().isAnyOf(SupplyStatus.CANCELED, SupplyStatus.DONE, SupplyStatus.PENDING)){
            return null;
        }

        supply.setStatus(SupplyStatus.CANCELED);
        return merge(supply);
    }

    public SupplyDAOImpl(Class<Supply> typeParameterClass) {
        super(typeParameterClass);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Supply> getSupply(Date dateFrom, Date dateTo, int store, int product) {
		StringBuilder sb =  new StringBuilder();
			sb.append("SELECT s.date, sum(s.quantity) as quantity ");
			sb.append(" from supply s ");
			sb.append(" where s.status = 3 ");
			if(dateFrom != null){
				sb.append(" and s.date between :DATEFROM ");
				sb.append(" and  :DATETO ");
			}
			if(store > 0){
				sb.append(" and s.receiver :STORE ");
			}
			if(product > 0){
				sb.append(" and s.product :PRODUCT ");
			}
		sb.append(" group by s.date ");
		Query sqlQuery = getCurrentSession().createQuery(sb.toString());
			if(dateFrom != null){
				sqlQuery.setParameter("DATEFROM", "'" +dateFrom + "'");
				sqlQuery.setParameter("DATETO", "'" +dateTo +"'");
			}
			if(store > 0){
				sqlQuery.setParameter("STORE", store);
			}
			if(product > 0){
				sqlQuery.setParameter("PRODUCT", product);
			}
		return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Supply> getByDate(Date date) {
        return (List<Supply>) getCurrentSession()
                .createQuery(GET_SUPPLY_BY_DATE)
                .setParameter(DATE_PARAM, date)
                .list();
    }
}
