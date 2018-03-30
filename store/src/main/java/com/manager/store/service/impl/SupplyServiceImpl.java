/**
 *
 */
package com.manager.store.service.impl;

import com.manager.store.dao.SupplyDAO;
import com.manager.store.entities.Supply;
import com.manager.store.mapper.Mapper;
import com.manager.store.mapper.SupplyMapper;
import com.manager.store.model.SupplyModel;
import com.manager.store.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Marek
 *
 */
@Service("supplyService")
@Transactional

public class SupplyServiceImpl extends AbstractService<Supply, SupplyModel> implements SupplyService {

	@Autowired
    @Qualifier("supplyDAO")
    private SupplyDAO supplyDAO;

    @Autowired
    private SupplyMapper supplyMapper;

    @Override
    public List<Supply> getSupply(Date dateFrom, Date dateTo, int store, int product) {
    	return supplyDAO.getSupply(dateFrom, dateTo, store, product);
    }

    @Override
    public List<Supply> getByDate(Date date) {
        return supplyDAO.getByDate(date);
    }

    @Override
    protected SupplyDAO getDao() {
        return supplyDAO;
    }

    @Override
    protected Mapper<Supply, SupplyModel> getMapper() {
        return supplyMapper;
    }

}
