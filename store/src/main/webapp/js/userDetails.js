var UserDetails = ((function (out, $){
    "use strict";

    var $workerSection = $("#worker-section");

    var triggers = {
        '#worker-checkbox': {
            'change': function (){
                var selected = $(this).is(":checked");
                enableWorkerSection(selected);
            }
        }
    };

    /**
     * @param {Boolean} enable
     */
    function enableWorkerSection(enable){
        $workerSection.toggle(enable);
        $workerSection.find("input, select").prop("disable", !enable);
    }

    function onReady(){
        $("#worker-checkbox").click();
    }

    out.triggers = triggers;
    out.onReady = onReady;

    return out;
})((UserDetails || {}), jQuery));

Common.register("UserDetails", UserDetails);
