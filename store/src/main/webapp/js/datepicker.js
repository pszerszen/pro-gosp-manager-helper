var Datepicker = ((function(out, $){

    var datePickerOptions = {
        dateFormat : "yy-mm-dd"
    };

    out.onReady = function(){
        $("input.datepicker").each(function(i){
            $(this).datepicker(datePickerOptions);
        });

        $("input.datetimepicker").each(function(i){
            $(this).datetimepicker(datePickerOptions);
        });
    };

    return out;
})((Datepicker || {}), jQuery));

Common.register("Datepicker", Datepicker);