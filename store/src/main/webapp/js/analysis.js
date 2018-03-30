//noinspection JSUnusedAssignment
var Analysis = (function (out, Tables, $){

    var bar = 'bar';
    var line = 'line';
    var pie = 'pie';
    var sectionIndex = 1;

    var $prototype = $("#prototype").find("div.section-row");
    var $simulations = $("#simulations");
    var $imageDialog = $("#image-dialog");

    var Charts = {
        0: {
            filters: ['blank-tab'],
            charType: null
        },
        1: {
            filters: ['shop-tab', 'product-tab', 'worker-tab', 'time-tab'],
            chartType: 'lineGainsTime',
            render: function (data){
                var x = ['x'];
                var y = ['GAINS'];
                $.each(data, function (key, value){
                    x.push(key);
                    y.push(value.toFixed(2));
                });
                return [x, y];
            }
        },
        2: {
            filters: ['shop-tab', 'product-tab', 'worker-tab', 'time-tab'],
            chartType: 'barSoldProductsTime',
            render: barChartRender
        },
        3: {
            filters: ['shop-tab', 'product-tab', 'time-tab'],
            chartType: 'barDeliveriesTime',
            render: barChartRender
        }
        ,
        4: {
            filters: ['shop-tab', 'product-tab', 'worker-tab', 'time-tab'],
            chartType: 'pieGains',
            render: function (data){
                var map = {};
                $.each(data, function (el, val){
                    if (typeof map[val.key] === 'undefined'){
                        map[val.key] = [val.value];
                    } else {
                        map[val.key].push(val.value);
                    }
                });
                var columns = [];
                $.each(map, function (key, val){
                    var insert = [key];
                    for (var i = 0; i < val.length; i++){
                        insert.push(val[i]);
                    }
                    columns.push(insert);
                });
                return columns;
            }
        }
        ,
        5: {
            filters: ['shop-tab', 'product-tab'],
            chartType: 'barProductsAmount',
            render: function (data){
                var columns = [];
                $.each(data, function (key, val){
                    columns.push([val.key, val.value]);
                });
                return columns;
            }
        }
    };

    var triggers = {
        '.graph-type': {
            'change': function (){
                var that = $(this);
                var $tabsSection = getTabSection(that);

                var charType = that.val();
                var chart = Charts[charType];

                $tabsSection.find("li").each(function (){
                    var that = $(this);
                    var filterType = that.find("a").data("tab");
                    var availableFilter = chart.filters.indexOf(filterType) > -1;
                    that.toggleClass("hidden", !availableFilter);
                });
                $tabsSection.find(".tabbed").data("active", "false");
                $tabsSection.find("li:not(.hidden):first").find("a").click();
                $("table input[type='checkbox']").prop("checked", false);
                getCheckBox(that).prop('checked', false);

                getChartSection(that).html("");
                if (charType > 0){
                    getChartSection(that).trigger("redraw");
                }
            }
        },
        '.filter-tab': {
            'click': function (){
                var that = $(this);
                var $list = that.closest("ul");
                $list.find("li").each(function (){
                    var thatLi = $(this);
                    thatLi.toggleClass("active", thatLi.is(that.closest("li")));
                });
                getTabSection(that).find(".filter-section").addClass("hidden");
                var $activeFilterSection = getTabSection(that).find("." + that.data("tab"));
                $activeFilterSection.removeClass("hidden");
                getCheckBox(that).prop("checked", $activeFilterSection.find("div.tabbed").data("active") == true);
            }
        },
        '.active-checkbox': {
            'change': function (){
                var that = $(this);
                var $tabSection = getTabSection(that);
                var displayedFilterSection = $tabSection.find("li.active > a").data("tab");
                var $displayedFilterSection = $tabSection.find("div." + displayedFilterSection);
                // setting attribute too for easier collecting data
                $displayedFilterSection.find("div.tabbed")
                    .attr("data-active", that.is(":checked"))
                    .data("active", that.is(":checked"));

                getChartSection(that).trigger("redraw");
            }
        },
        'input[data-id]': {
            'change': function (){
                var that = $(this);
                if (that.closest("div.tabbed").data("active") == true){
                    getChartSection(that).trigger("redraw");
                }
            }
        },
        '.add-chart': {
            'click': function (){
                var clone = $prototype.clone(true, true);
                clone.find(".chart-section").prop("id", "section" + sectionIndex);
                getSectionRow($(this)).after(clone);
                toggleRemoveButtons(true);
                sectionIndex++;
            }
        },
        '.remove-chart': {
            'click': function (){
                getSectionRow($(this)).remove();
                if ($simulations.find("div.section-row").length < 2){
                    toggleRemoveButtons(false);
                }
            }
        },
        '.save-chart': {
            'click': function (){
                var that = $(this);
                var $chartSection = getChartSection(that);
                var canvas = $("<canvas></canvas>")
                    .attr("id", "canvas")
                    .height($chartSection.outerHeight())
                    .width($chartSection.outerWidth());
                $chartSection.html("").append(canvas);
                $imageDialog.removeClass("hidden");
                $imageDialog.dialog({
                    height: $chartSection.outerHeight() + 60,
                    width: $chartSection.outerWidth() + 60
                });
                canvg(document.getElementById("canvas"), $chartSection.html());
            }
        },
        '.chart-section': {
            'redraw': function (){
                var that = $(this);
                var $sectionRow = getSectionRow(that);
                var chart = Charts[$sectionRow.find("select.graph-type").val()];
                var data = collectData($sectionRow);
                $.ajax({
                    url: Constants.getBaseUrlPath() + "chart/" + data.type,
                    dataType: 'json',
                    type: 'POST',
                    data: $.param(data, true),
                    success: function (response){
                        var drawer = getChartDrawer(chart.chartType);
                        drawer(that, chart.render(response));
                    },
                    error: function (xhr, err){
                        console.error(xhr);
                        console.error(err);
                    }
                });
            }
        }
    };

    function getChartDrawer(chartType){
        if (chartType.substr(0, bar.length) === bar){
            return drawBarChart;
        } else if (chartType.substr(0, line.length) === line){
            return drawTimelinesChart;
        } else if (chartType.substr(0, pie.length) === pie){
            return drawPieChart;
        }
        return false;
    }

    function drawBarChart($section, columns){
        $section.html("");
        c3.generate({
            bindto: '#' + $section.prop("id"),
            padding: {
                right: 30
            },
            data: {
                columns: columns,
                type: 'bar'
            },
            legend: {
                show: columns.length < 21
            },
            bar: {
                width: {
                    ratio: 1
                }
            }
        });
    }

    function drawTimelinesChart($section, columns){
        $section.html("");
        c3.generate({
            bindto: '#' + $section.prop("id"),
            padding: {
                right: 50
            },
            data: {
                x: 'x',
                columns: columns
            },
            axis: {
                x: {
                    type: 'timeseries',
                    show: columns[0].length < 21,
                    tick: {
                        format: '%Y-%m-%d'
                    }
                }
            }
        });
    }

    function drawPieChart($section, columns){
        $section.html("");
        c3.generate({
            bindto: '#' + $section.prop("id"),
            data: {
                columns: columns,
                type: 'pie'
            },
            legend: {
                show: columns.length < 21
            }
        });
    }

    function barChartRender(data){
        var list = [];
        $.each(data, function (key, value){
            list.push([key, value]);
        });
        return list;
    }

    function getCheckBox($el){
        return getSectionRow($el).find("input.active-checkbox");
    }

    function getChartSection($el){
        return getSectionRow($el).find("div.chart-section");
    }

    function getSectionRow($el){
        return $el.closest(".section-row");
    }

    function getTabSection($el){
        return getSectionRow($el).find(".tabs-section");
    }

    function collectData($sectionRow){
        var chart = Charts[$sectionRow.find("select.graph-type").val()];
        var data = {
            type: chart.chartType,
            dateFrom: "",
            dateUntil: "",
            shops: [],
            products: [],
            workers: []
        };
        $sectionRow.find(".tabbed[data-active='true']").each(function (){
            var that = $(this);
            var $datePickers = that.find(".datepicker");
            var $table = that.find("table[id]");

            if ($datePickers.length > 0){
                data.dateFrom = that.find(".from-date").val();
                data.dateUntil = that.find(".until-date").val();
            } else if ($table.length > 0){
                switch ($table.prop("id")){
                    case 'shop-summaries-table':
                        data.shops = Tables.getSelectedIds($table);
                        break;
                    case 'product-summaries-table':
                        data.products = Tables.getSelectedIds($table);
                        break;
                    case 'worker-summaries-table':
                        data.workers = Tables.getSelectedIds($table);
                        break;
                }
            }
        });
        return data;
    }

    function toggleRemoveButtons(enabled){
        $(".remove-chart")
            .toggleClass("disabled", !enabled)
            .prop("disabled", !enabled);
    }

    out.onReady = function (){
        $(".graph-type").change();
        toggleRemoveButtons(false);
    };

    out.triggers = triggers;

    return out;
})
((Analysis || {}), Tables, jQuery);

Common.register("Analysis", Analysis);