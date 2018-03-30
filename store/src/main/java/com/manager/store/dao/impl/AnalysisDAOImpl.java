package com.manager.store.dao.impl;

import com.manager.store.dao.AnalysisDAO;
import com.manager.store.model.AnalysisChartRequest;
import com.manager.store.utils.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Piotr
 */
@Repository("analysisDAO")
public class AnalysisDAOImpl implements AnalysisDAO {

    private static final String SHOPS = "shops";

    private static final String WORKERS = "workers";

    private static final String PRODUCTS = "products";

    private static final String FROM = "from";

    private static final String UNTIL = "until";

    private static final String QUERY_DELIMITER = " AND ";

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<Pair<Double, Date>> getGainsByTime(AnalysisChartRequest request) {
        String formula = "SELECT sum((e.product.salesPrice - e.product.purchasePrice) * e.quantity), e.date" +
                " FROM Transaction e %s GROUP BY e.date ORDER BY e.date";

        Query query = getCurrentSession().createQuery(String.format(formula, createWhereStatement(request)));
        addParameters(query, request);

        return ((List<Object[]>) query.list())
                .stream()
                .map(getConverter(Double.class, Date.class))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pair<Long, Date>> getSoldProductsAmountByTime(AnalysisChartRequest request) {
        String formula = "SELECT sum(e.quantity), e.date FROM Transaction e %s GROUP BY e.date";

        Query query = getCurrentSession().createQuery(String.format(formula, createWhereStatement(request)));
        addParameters(query, request);

        return ((List<Object[]>) query.list())
                .stream()
                .map(getConverter(Long.class, Date.class))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pair<Long, Date>> getDeliveriesByTime(AnalysisChartRequest request) {
        String formula = "SELECT sum(e.quantity), e.date FROM Supply e %s GROUP BY e.date";

        Query query = getCurrentSession().createQuery(String.format(formula, createWhereStatement(request)));
        addParameters(query, request);

        return ((List<Object[]>) query.list())
                .stream()
                .map(getConverter(Long.class, Date.class))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pair<String, Double>> getGainsOfProducts(AnalysisChartRequest request) {
        String formula = "SELECT p.name, sum((p.salesPrice - p.purchasePrice) * e.quantity) FROM Product p JOIN p.transactions e %s GROUP BY p.id";

        Query query = getCurrentSession().createQuery(String.format(formula, createWhereStatement(request)));
        addParameters(query, request);

        return ((List<Object[]>) query.list())
                .stream()
                .map(getConverter(String.class, Double.class))
                .collect(Collectors.toList());
    }

    @Override
    public Double getGainsFromAllTransactions() {
        String formula = "SELECT sum((e.product.salesPrice - e.product.purchasePrice) * e.quantity) FROM Transaction e";
        return (Double) getCurrentSession().createQuery(formula).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pair<String, Long>> getProductAmount(AnalysisChartRequest request) {
        String formula = "SELECT p.product.name, sum(p.quantity) FROM ProductState p %s GROUP BY p.product.id";

        String where = "";
        if (!request.emptyRequest()) {
            StringJoiner joiner = new StringJoiner(QUERY_DELIMITER, "WHERE ", "");

            List<Long> ids = request.getShops();
            if (isNotEmpty(ids)) {
                joiner.add("p.store.id IN(:" + SHOPS + ")");
            }

            ids = request.getProducts();
            if (isNotEmpty(ids)) {
                joiner.add("p.product.id IN(:" + PRODUCTS + ")");
            }

            where = joiner.toString();
        }

        Query query = getCurrentSession().createQuery(String.format(formula, where));
        addParameters(query, request);

        return ((List<Object[]>) query.list())
                .stream()
                .map(getConverter(String.class, Long.class))
                .collect(Collectors.toList());
    }

    private static String createWhereStatement(final AnalysisChartRequest request) {
        String where = "";
        if (!request.emptyRequest()) {
            StringJoiner joiner = new StringJoiner(QUERY_DELIMITER, "WHERE ", "");

            if (isNotBlank(request.getDateFrom())) {
                joiner.add("e.date > :" + FROM);
            }

            if (isNotBlank(request.getDateUntil())) {
                joiner.add("e.date < :" + UNTIL);
            }

            List<Long> ids = request.getShops();
            if (isNotEmpty(ids)) {
                joiner.add("e.store.id IN(:" + SHOPS + ")");
            }

            ids = request.getWorkers();
            if (isNotEmpty(ids)) {
                joiner.add("e.staff.id IN(:" + WORKERS + ")");
            }

            ids = request.getProducts();
            if (isNotEmpty(ids)) {
                joiner.add("e.product.id IN(:" + PRODUCTS + ")");
            }

            where = joiner.toString();
        }
        return where;
    }

    private static Query addParameters(Query query, AnalysisChartRequest request) {
        if (isNotBlank(request.getDateFrom())) {
            query.setParameter(FROM, DateUtils.toDate(request.getDateFrom()));
        }

        if (isNotBlank(request.getDateUntil())) {
            query.setParameter(UNTIL, DateUtils.toDate(request.getDateUntil()));
        }

        List<Long> ids = request.getShops();
        if (isNotEmpty(ids)) {
            query.setParameterList(SHOPS, ids);
        }

        ids = request.getWorkers();
        if (isNotEmpty(ids)) {
            query.setParameterList(WORKERS, ids);
        }

        ids = request.getProducts();
        if (isNotEmpty(ids)) {
            query.setParameterList(PRODUCTS, ids);
        }

        return query;
    }

    private Session getCurrentSession() throws HibernateException {
        return sessionFactory.getCurrentSession();
    }

    private <L, R> Function<Object[], Pair<L, R>> getConverter(Class<L> left, Class<R> right) {
        return (objects) -> Pair.of(left.cast(objects[0]), right.cast(objects[1]));
    }
}
