package com.manager.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Piotr on 2015-11-22.
 */
@Controller
@RequestMapping(value = "/simulations")
public class SimulationController extends AbstractController{

    protected static final String SECTION_NAME = "/simulations";

    private static final String MAIN_SECTION_PAGE = "simulations/SimulationsHome";

    @Override
    protected String getSectionName() {
        return SECTION_NAME;
    }

    @Override
    protected String getMainSectionPage() {
        return MAIN_SECTION_PAGE;
    }

    @Override
    protected String getDetailsPage() {
        return null;
    }

    @Override
    protected String getPageTitle() {
        return "Simulations";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView simulationsPage(){
        return new ModelAndView(getMainSectionPage());
    }
}
