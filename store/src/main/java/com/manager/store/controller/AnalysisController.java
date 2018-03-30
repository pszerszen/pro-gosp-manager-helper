package com.manager.store.controller;

import com.manager.store.model.AnalysisChartRequest;
import com.manager.store.model.ModelSummary;
import com.manager.store.service.AnalysisService;
import com.manager.store.service.ProductService;
import com.manager.store.service.StoreService;
import com.manager.store.service.UserService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/analysis")
public class AnalysisController extends AbstractController {

    protected static final String SECTION_NAME = "/analysis";

    private static final String MAIN_SECTION_PAGE = "analysis/AnalysisHome";

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnalysisService analysisService;

    @Override
    protected String getSectionName() {
        return SECTION_NAME.substring(1);
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
        return "Analysis";
    }

    @ModelAttribute("shopSummaries")
    public List<ModelSummary> shopSummaries() {
        return storeService.searchSummaries(null, 0);
    }

    @ModelAttribute("productSummaries")
    public List<ModelSummary> productSummaries() {
        return productService.searchSummaries(null, 0);
    }

    @ModelAttribute("workerSummaries")
    public List<ModelSummary> workerSummaries() {
        return userService.getWorkersSummaries();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView analisysPage() {
        return new ModelAndView(getMainSectionPage(), "request", new AnalysisChartRequest());
    }

    @RequestMapping(value = "/chart/lineGainsTime", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Double> getGainsByTime(@ModelAttribute AnalysisChartRequest request) {
        return analysisService.getGainsByTime(request);
    }

    @RequestMapping(value = "/chart/barSoldProductsTime", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Long> getSoldProductsAmountByTime(@ModelAttribute AnalysisChartRequest request) {
        return analysisService.getSoldProductsAmountByTime(request);
    }

    @RequestMapping(value = "/chart/barDeliveriesTime", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Long> getDeliveriesByTime(@ModelAttribute AnalysisChartRequest request) {
        return analysisService.getDeliveriesByTime(request);
    }

    @RequestMapping(value = "/chart/pieGains", method = RequestMethod.POST)
    @ResponseBody
    public List<Pair<String, Double>> getGainsOfProducts(@ModelAttribute AnalysisChartRequest request) {
        return analysisService.getGainsOfProducts(request);
    }

    @RequestMapping(value = "/chart/barProductsAmount", method = RequestMethod.POST)
    @ResponseBody
    public List<Pair<String, Long>> getProductAmount(@ModelAttribute AnalysisChartRequest request) {
        return analysisService.getProductAmount(request);
    }
}
