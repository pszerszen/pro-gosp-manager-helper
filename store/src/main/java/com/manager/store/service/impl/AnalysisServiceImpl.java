package com.manager.store.service.impl;

import com.manager.store.dao.AnalysisDAO;
import com.manager.store.model.AnalysisChartRequest;
import com.manager.store.service.AnalysisService;
import com.manager.store.utils.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author pszerszen
 */
@Service
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

    private final static String DATE_FORM = "yyyy-MM-dd";

    @Autowired
    private AnalysisDAO analysisDAO;

    @Override
    public Map<String, Double> getGainsByTime(AnalysisChartRequest request) {
        return mergeDateTimesDoubleToDays(analysisDAO.getGainsByTime(request));
    }

    @Override
    public Map<String, Long> getSoldProductsAmountByTime(AnalysisChartRequest request) {
        return mergeDateTimesLongToDays(analysisDAO.getSoldProductsAmountByTime(request));
    }

    @Override
    public Map<String, Long> getDeliveriesByTime(AnalysisChartRequest request) {
        return mergeDateTimesLongToDays(analysisDAO.getDeliveriesByTime(request));
    }

    @Override
    public List<Pair<String, Double>> getGainsOfProducts(AnalysisChartRequest request) {
        Double allGains = analysisDAO.getGainsFromAllTransactions();
        final List<Pair<String, Double>> gainsOfProducts = analysisDAO.getGainsOfProducts(request);
        gainsOfProducts.forEach(pair -> Pair.of(pair.getLeft(), pair.getValue() / allGains));
        return gainsOfProducts;
    }

    @Override
    public List<Pair<String, Long>> getProductAmount(AnalysisChartRequest request) {
        return analysisDAO.getProductAmount(request);
    }

    private Map<String, Double> mergeDateTimesDoubleToDays(final List<Pair<Double, Date>> pairs) {
        Map<String, Double> dateValueMap = new TreeMap<>(this::stringifiedDateComparator);
        pairs.forEach(pair -> {
            String strigifiedDate = DateUtils.fromDateTime(pair.getRight());
            strigifiedDate = strigifiedDate.substring(0, DATE_FORM.length());
            dateValueMap.merge(strigifiedDate, pair.getLeft(), this::subtractDoubles);
        });

        return dateValueMap;
    }

    private Map<String, Long> mergeDateTimesLongToDays(final List<Pair<Long, Date>> pairs) {
        Map<String, Long> dateValueMap = new TreeMap<>(this::stringifiedDateComparator);
        pairs.forEach(pair -> {
            String strigifiedDate = DateUtils.fromDateTime(pair.getRight());
            strigifiedDate = strigifiedDate.substring(0, DATE_FORM.length());
            dateValueMap.merge(strigifiedDate, pair.getLeft(), this::subtractLongs);
        });

        return dateValueMap;
    }

    private int stringifiedDateComparator(String a, String b) {
        String[] aDate = a.split("-");
        String[] bDate = b.split("-");

        for (int i = 0; i < 3; i++) {
            Integer aNum = Integer.parseInt(aDate[ i ]);
            Integer bNum = Integer.parseInt(bDate[ i ]);

            int compare = aNum.compareTo(bNum);
            if (compare != 0) {
                return compare;
            }
        }

        return 0;
    }

    private Long subtractLongs(Long a, Long b) {
        return a + b;
    }

    private Double subtractDoubles(Double a, Double b) {
        return a + b;
    }
}
