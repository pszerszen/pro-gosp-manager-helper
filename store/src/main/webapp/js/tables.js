var Tables = ((function (out, $){
    "use strict";

    var FLAG = "FFFFFFLLLLLL@@@@@@GGGGGG";

    var defaultOptions = {
        bFilter: false,
        bInfo: false,
        drawCallback: function (){
            $(".logic-value").each(function (){
                var that = $(this);
                that.html(Shared.tickCross(that.text()));
            });
            Common.registerEvent($("table.main-table").find("input[type=checkbox]"), 'click', MainPage.switchButtons);
            Common.registerEvent($("table.main-table").find("tr"), 'click', populateRowClick);
        }
    };

    var summaryOptions = {
        bFilter: false,
        bInfo: false,
        scrollX: true,
        scrollY: "180px",
        scrollCollapse: true
    };

    var triggers = {
        '.searcher': {
            'click': function (){
                var that = $(this);
                var $input = that.closest(".form-group").find("input");
                var $table = that.closest("form").find("table");

                $.ajax({
                    url: Constants.getBasePath() + "search?term=" + $input.val(),
                    dataType: "json",
                    type: 'GET',
                    success: function (data){
                        var type = $table.attr('id').replace("-table", "");
                        var mapper = mappers[type];
                        $table.trigger('clear');
                        $.each(data, function (id, val){
                            $table.trigger('add-row', mapper(val).join(FLAG));
                        });
                        defaultOptions.drawCallback();
                    }
                });
            }
        },
        'tr': {
            'click': populateRowClick
        },
        '.data-table': {
            'clear': function (){
                var rows = getRows($(this));
                try {
                    $(this).dataTable().fnClearTable();
                } catch (err){
                    console.error(err);
                }
                $(this).trigger('table-update');
            },
            'add-row': function (evt, tableRow){
                $(this).DataTable().row.add(tableRow.split(FLAG)).draw(false);
            }
        }
    };

    var mappers = {
        'shops': function (model){
            return createTableRow({
                id: model.id,
                name: model.name,
                phone: model.phone,
                type: model.type.toLowerCase().capitalise(),
                address: model.address,
                active: model.active
            });
        },
        'users': function (model){
            return createTableRow({
                id: model.id,
                name: model.name,
                mail: model.mail,
                salary: model.salary,
                bonus: model.bonus,
                dateFrom: model.dateFrom,
                dateUntil: model.dateUntil,
                active: model.active
            });
        },
        'products': function (model){
            return createTableRow({
                id: model.id,
                name: model.name,
                model: model.model,
                producer: model.producer,
                purchasePrice: model.purchasePrice,
                salesPrice: model.salesPrice,
                active: model.active
            });
        },
        'transactions': function (model){
            return createTableRow({
                id: model.id,
                staff: model.staff.name,
                product: model.product.name,
                store: model.store.name,
                quantity: model.quantity,
                date: model.date
            }, function (){
                return "<input type='checkbox' data-id='" + model.id + "' data-staff-id='" + model.staff.id +
                    "' data-product-id='" + model.product.id + "' data-store-id='" + model.store.id + "'>";
            });
        },
        'delivery': function (model){
            return createTableRow({
                id: model.id,
                product: model.product.name,
                store: model.store.name,
                date: model.date,
                status: model.status
            }, function (){
                return "<input type='checkbox' data-id='" + model.id + "' data-product-id='" + model.product.id +
                    "' data-store-id='" + model.store.id + "'>";
            });
        }
    };

    function createTableRow(data, firstCellCreator){
        var row = [];
        $.each(data, function (id, val){
            if (id === 'id'){
                if (typeof firstCellCreator === 'function'){
                    row.push(firstCellCreator());
                } else {
                    row.push("<input type='checkbox' data-id='" + id + "'>");
                }
            } else if(id === 'active'){
                row.push("<span class='logic-value'>"+ val +"</span>")
            } else {
                row.push(val);
            }
        });
        return row;
    }

    function populateRowClick(event){
        var checkbox = $(this).find("input[type=checkbox]");
        if (!$(event.target).is(checkbox)){
            checkbox.click();
        }
    }

    /**
     * Gets the value of a cell a row index and column title. Requires the row
     * to be in dataTable data (Will not work when the table uses an ajaxSource
     * and the row is on another page.)
     */
    function getTableValue(dataTable, rowIndex, title){
        return getCellValue(rowIndex, getColumnIndex(dataTable, title), dataTable);
    }

    /**
     * Returns all rows in the datatable as an array Requires the row to be in
     * dataTable data (Will not work when the table uses an ajaxSource and the
     * row is on another page.)
     */
    function getRows($dataTable){
        var rows = [];
        var table = $dataTable.dataTable().fnGetNodes();

        for (var i = 0; i < table.length; i++){
            rows.push(table[i]);
        }
        return rows;
    }

    function getSelectedIds($dataTable){
        var ids = [];
        $(getRows($dataTable)).find("input[type='checkbox']:checked").each(function(){
            ids.push($(this).data("id"));
        });
        return ids;
    }

    /**
     * Returns a
     * <tr> representing the row from a rowIndex. Requires the row to be in
     * dataTable data (Will not work when the table uses an ajaxSource and the
     * row is on another page.)
     */
    function getRow($dataTable, rowIndex){
        return $dataTable.dataTable().fnGetNodes(rowIndex);
    }

    function onReady(){
        $(".data-table").each(function (){
            $(this).dataTable(defaultOptions);
        });
        $(".data-table-summary").each(function(){
            $(this).dataTable(summaryOptions);
        });
    }

    out.getRow = getRow;
    out.getRows = getRows;
    out.getSelectedIds = getSelectedIds;

    out.triggers = triggers;
    out.onReady = onReady;

    return out;
})((Tables || {}), jQuery));

Common.register("Tables", Tables);