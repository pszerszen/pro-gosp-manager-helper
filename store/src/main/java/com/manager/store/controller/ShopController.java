package com.manager.store.controller;

import com.google.common.collect.Lists;
import com.manager.store.entities.Store;
import com.manager.store.entities.enums.StoreType;
import com.manager.store.model.StoreModel;
import com.manager.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = ShopController.SECTION_NAME)
public class ShopController extends AbstractCrudController<Store, StoreModel> {

    protected static final String SECTION_NAME = "shops";

    @Autowired
    private StoreService service;

    @ModelAttribute("storeTypes")
    public List<StoreType> getStoreTypes(){
        return Lists.newArrayList(StoreType.values());
    }

    @Override
    protected StoreModel setUpNewModel() {
        return new StoreModel();
    }

    @Override
    protected StoreService getService() {
        return service;
    }

    @Override
    protected String getSectionName() {
        return SECTION_NAME;
    }

    @Override
    protected String getMainSectionPage() {
        return "shops/ShopsMain";
    }

    @Override
    protected String getDetailsPage() {
        return "shops/ShopsDetails";
    }

    @Override
    protected String getPageTitle() {
        return "Shop";
    }

}
