package com.manager.store.dao;

import com.manager.store.model.AnalysisChartRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.List;

/**
 * @author Piotr
 */
public interface AnalysisDAO {

    List<Pair<Double, Date>> getGainsByTime(AnalysisChartRequest request);

    List<Pair<Long, Date>> getSoldProductsAmountByTime(AnalysisChartRequest request);

    List<Pair<Long, Date>> getDeliveriesByTime(AnalysisChartRequest request);

    List<Pair<String, Double>> getGainsOfProducts(AnalysisChartRequest request);

    List<Pair<String, Long>> getProductAmount(AnalysisChartRequest request);

    Double getGainsFromAllTransactions();
}
