package com.manager.store.controller;

import com.google.common.collect.Lists;
import com.manager.store.entities.Supply;
import com.manager.store.entities.enums.RoleType;
import com.manager.store.entities.enums.SupplyStatus;
import com.manager.store.model.SupplyModel;
import com.manager.store.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = SupplyController.SECTION_NAME)
public class SupplyController extends AbstractCrudController<Supply, SupplyModel> {

    protected static final String SECTION_NAME = "delivery";

    @Autowired
    @Qualifier("supplyService")
    private SupplyService service;

    @ModelAttribute("deliveryStatuses")
    public List<SupplyStatus> getDeliveryStatuses() {
        List<SupplyStatus> supplyStatuses = Lists.newArrayList(SupplyStatus.values());
        if(credentialsService.hasCurrentUserRole(RoleType.ROLE_MANAGER)){
            supplyStatuses.remove(SupplyStatus.DONE);
        }
        return supplyStatuses;
    }

    @Override
    protected SupplyModel setUpNewModel() {
        return new SupplyModel();
    }

    @Override
    protected SupplyService getService() {
        return service;
    }

    @Override
    protected String getSectionName() {
        return SECTION_NAME;
    }

    @Override
    protected String getMainSectionPage() {
        return "deliveries/DeliveriesMain";
    }

    @Override
    protected String getDetailsPage() {
        return "deliveries/DeliverysDetails";
    }

    @Override
    protected String getPageTitle() {
        return "Delivery";
    }

    protected List<Supply> getSupply(Date dateFrom, Date dateTo, int store, int product){
    	return service.getSupply(dateFrom, dateTo, store, product);
    }

}
