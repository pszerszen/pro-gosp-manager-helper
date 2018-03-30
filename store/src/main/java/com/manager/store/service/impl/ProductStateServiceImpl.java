/**
 *
 */
package com.manager.store.service.impl;

import com.manager.store.dao.ProductStateDAO;
import com.manager.store.entities.ProductState;
import com.manager.store.mapper.ProductStateMapper;
import com.manager.store.model.ProductStateModel;
import com.manager.store.service.ProductStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marek
 *
 */
@Service("productStateService")
@Transactional

public class ProductStateServiceImpl extends AbstractService<ProductState, ProductStateModel> implements ProductStateService {

	@Autowired
    @Qualifier("productStateDAO")
    private ProductStateDAO productStateDAO;

    @Autowired
    private ProductStateMapper productStateMapper;

    @Override
    public ProductState getByProduct(String product) {
        return productStateDAO.getByProduct(product);
    }

    @Override
    protected ProductStateDAO getDao() {
        return productStateDAO;
    }

    @Override
    protected ProductStateMapper getMapper() {
        return productStateMapper;
    }
}

