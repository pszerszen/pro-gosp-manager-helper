/**
 *
 */
package com.manager.store.service.impl;

import com.manager.store.dao.ProductDAO;
import com.manager.store.entities.Product;
import com.manager.store.mapper.Mapper;
import com.manager.store.mapper.ProductMapper;
import com.manager.store.model.ProductModel;
import com.manager.store.service.ProductService;
import com.manager.store.validators.ValidationMessagesConstants;
import com.manager.store.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marek
 */
@Service("productService")
@Transactional
public class ProductServiceImpl extends AbstractService<Product, ProductModel> implements ProductService {

    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProduct(int store, int product) {
    	return productDAO.getProduct(store, product);
    }

    @Override
    public Product getByName(String name) {
        return productDAO.getByName(name);
    }

    @Override
    protected ProductDAO getDao() {
        return productDAO;
    }

    @Override
    protected Mapper<Product, ProductModel> getMapper() {
        return productMapper;
    }

    @Override
    protected List<Validator> getValidators(final ProductModel model) {
        List<Validator> validators = super.getValidators(model);
        validators.add(() -> {
            Set<String> errors = new HashSet<>();
				 if (!model.getName().matches(ValidationMessagesConstants.ALPHANUMERIC_MIN_4_SIGN_REGEX)) {
					 errors.add(ValidationMessagesConstants.MESSAGE_INVALID_NAME_4);
            }
            return errors;
        });
        validators.add(() -> {
            Set<String> errors = new HashSet<>();
				 if (!model.getModel().matches(ValidationMessagesConstants.ALPHANUMERIC_MIN_2_SIGN_REGEX)) {
					 errors.add(ValidationMessagesConstants.MESSAGE_INVALID_NAME_2);
            }
            return errors;
        });
        validators.add(() -> {
            Set<String> errors = new HashSet<>();
				 if (!model.getProducer().matches(ValidationMessagesConstants.ALPHANUMERIC_MIN_4_SIGN_REGEX)) {
					 errors.add(ValidationMessagesConstants.MESSAGE_INVALID_NAME_4);
            }
            return errors;
        });
        validators.add(() -> {
            Set<String> errors = new HashSet<>();
				 if (!model.getPurchasePrice().toString().matches(ValidationMessagesConstants.DECIMAL_SIX_TWO_REGEX)) {
					 errors.add(ValidationMessagesConstants.MESSAGE_INVALID_PRICE);
            }
            return errors;
        });
        validators.add(() -> {
            Set<String> errors = new HashSet<>();
				 if (!model.getSalesPrice().toString().matches(ValidationMessagesConstants.DECIMAL_SIX_TWO_REGEX)) {
					 errors.add(ValidationMessagesConstants.MESSAGE_INVALID_PRICE);
            }
            return errors;
        });

        return validators;
    }

    @Override
    public Product delete(final Long id) {
        return null;
    }
}
