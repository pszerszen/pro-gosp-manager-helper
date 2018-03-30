package com.manager.store.controller;

import com.manager.store.entities.Product;
import com.manager.store.model.ProductModel;
import com.manager.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = ProductsController.SECTION_NAME)
public class ProductsController extends AbstractCrudController<Product, ProductModel> {

    protected static final String SECTION_NAME = "products";

    @Autowired
    @Qualifier("productService")
    private ProductService service;

    @Override
    protected ProductModel setUpNewModel() {
        return new ProductModel();
    }

    @Override
    protected ProductService getService() {
        return service;
    }

    @Override
    protected String getSectionName() {
        return SECTION_NAME;
    }

    @Override
    protected String getMainSectionPage() {
        return "products/ProductsMain";
    }

    @Override
    protected String getDetailsPage() {
        return "products/ProductsDetails";
    }

    @Override
    protected String getPageTitle() {
        return "Products";
    }
    protected List<Product> getProduct(int store, int product){
    	return service.getProduct(store, product);
    }

}
