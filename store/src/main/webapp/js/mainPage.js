var MainPage = (function (out, Shared, $){
    "use strict";

    var $mainTable = $("table.main-table");
    var $editButton = $("#edit-button");
    var $removeButton = $("#remove-button");

    var triggers = {
        'input[type=checkbox]': {
            'click': switchButtons
        },
        '#edit-button': {
            'click': function (){
                var checkedId = $(getChecked()[0]).data("id");
                window.location.href = Constants.getEditBaseUrlPath() + checkedId;
            }
        },
        '#remove-button': {
            'click': function (){
                var ids = [];
                getChecked().each(function (i){
                    ids.push($(this).data("id"));
                });
                $.ajax({
                    url: Constants.getDeleteBaseUrlPath() + "?ids=" + ids.join(","),
                    dataType: "json",
                    type: 'DELETE',
                    success: function (data){
                        $(".searcher").click();
                        MessageBox.displayMessageBox("Success", "Successfully deleted selected data.", MessageBox.types.INFORMATION);
                        setTimeout(function (){
                            MessageBox.hideMessageBox(MessageBox.types.INFORMATION);
                            var $button = $(".searcher");
                            $button.closest(".form-group").find("input").val("");
                            $button.click();
                        }, 3000);
                    },
                    error: function (){
                        MessageBox.displayMessageBox("Error", "Deletion process did not passed clear.", MessageBox.types.ERROR);
                    }
                });
            }
        }
    };

    function switchButtons(){
        var checkedNum = getChecked().size();

        var disableEdit = checkedNum != 1;
        $editButton.toggleClass("disabled", disableEdit);
        $editButton.prop("disabled", disableEdit);

        var disableRemoving = checkedNum < 1;
        if ($(this).hasClass("deliveries-checkbox")){
            disableRemoving = disableRemoving || !deliveryRemoveCondition();
        }
        $removeButton.toggleClass("disabled", disableRemoving);
        $removeButton.prop("disabled", disableRemoving);
    }

    function deliveryRemoveCondition(){
        var possible = true;
        getChecked().each(function (){
            if ($(this).closest("tr").find("td.delivery-status").text() !== "PLACED")
                possible = false;
        });

        return possible;
    }

    function getChecked(){
        return $mainTable.find("input:checked");
    }

    function onReady(){
        switchButtons();
    }

    out.switchButtons = switchButtons;
    out.triggers = triggers;
    out.onReady = onReady;

    return out;
})((MainPage || {}), Shared, jQuery);

Common.register("MainPage", MainPage);