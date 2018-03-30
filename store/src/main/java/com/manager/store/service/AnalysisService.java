package com.manager.store.service;

import com.manager.store.model.AnalysisChartRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * @author pszerszen
 */
public interface AnalysisService {
    Map<String, Double> getGainsByTime(AnalysisChartRequest request);

    Map<String, Long> getSoldProductsAmountByTime(AnalysisChartRequest request);

    Map<String, Long> getDeliveriesByTime(AnalysisChartRequest request);

    List<Pair<String, Double>> getGainsOfProducts(AnalysisChartRequest request);

    List<Pair<String, Long>> getProductAmount(AnalysisChartRequest request);
}
