<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="chartId" required="true" %>

<div class="row section-row">
    <div class="col-sm-7">
        <div class="row small-row">
            <div class="col-sm-12">
                <select class="form-control graph-type">
                    <option value="0">Choose chart...</option>
                    <option value="1">The statement of profits from the time [line graph]</option>
                    <option value="2">Summary of goods sold from the time [bar graph]</option>
                    <option value="3">Summary Supply of Time [bar graph]</option>
                    <option value="4">The statement of profits [pie chart]</option>
                    <option value="5">Summary of quantities of products [bar graph]</option>
                </select>
            </div>
        </div>
        <div class="row small-row">
            <div class="col-sm-12">
                <div id="${chartId}" class="form-control left-side chart-section"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-5">
        <div class="row small-row">
            <div class="col-sm-9">
                <div class="row small-row tabs-section">
                    <div class="col-sm-11">
                        <ul class="nav nav-tabs">
                            <li class="filter-tab hidden active" role="presentation">
                                <a class="filter-tab" data-tab="blank-tab"></a>
                            </li>
                            <li role="presentation">
                                <a class="filter-tab" data-tab="shop-tab">SHOP</a>
                            </li>
                            <li role="presentation">
                                <a class="filter-tab" data-tab="product-tab">PRODUCT</a>
                            </li>
                            <li role="presentation">
                                <a class="filter-tab" data-tab="worker-tab">WORKER</a>
                            </li>
                            <li role="presentation">
                                <a class="filter-tab" data-tab="time-tab">TIME</a>
                            </li>
                        </ul>
                    </div>
                    <div class="blank-tab filter-section col-sm-11">
                        <div class="form-control tabbed"></div>
                    </div>
                    <div class="shop-tab filter-section col-sm-11 hidden">
                        <div data-active="false" class="form-control tabbed">
                            <t:summaryTable idPrefix="shop-summaries" summaries="${shopSummaries}"/>
                        </div>
                    </div>
                    <div class="product-tab filter-section col-sm-11 hidden">
                        <div data-active="false" class="form-control tabbed">
                            <t:summaryTable idPrefix="product-summaries" summaries="${productSummaries}"/>
                        </div>
                    </div>
                    <div class="worker-tab filter-section col-sm-11 hidden">
                        <div data-active="false" class="form-control tabbed">
                            <t:summaryTable idPrefix="worker-summaries" summaries="${workerSummaries}"/>
                        </div>
                    </div>
                    <div class="time-tab filter-section col-sm-11 hidden">
                        <div data-active="false" class="form-control tabbed">
                            <t:formRow labelText="From" extraClass="date-row">
                                <input class="form-control datepicker from-date"/>
                            </t:formRow>
                            <t:formRow labelText="Until" extraClass="date-row">
                                <input class="form-control datepicker until-date"/>
                            </t:formRow>
                        </div>
                    </div>
                </div>
                <div class="row small-row">
                    <div class="col-sm-1">
                        <input type="checkbox" class="active-checkbox form-control" value="ACTIVE">
                    </div>
                    <div class="col-sm-1" style="padding-top: 3%">
                        ACTIVE
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="row small-row">
                    <button class="btn btn-default btn-block add-chart">ADD CHART</button>
                </div>
                <div class="row small-row">
                    <button class="btn btn-default btn-block remove-chart">REMOVE CHART</button>
                </div>
                <div class="row small-row">
                    <button class="btn btn-default btn-block save-chart disabled">SAVE CHART</button>
                </div>
            </div>
        </div>
    </div>
</div>