var ChangePassword = ((function(out, MessageBox, $) {
    "use strict";

    var $form = $("form#changePassword");

    var triggers = {
        '#change-password-button' : {
            'click' : function(evt){
                evt.preventDefault();

                $.ajax({
                    url: $form.attr("action"),
                    dataType: 'text',
                    type: $form.attr("method"),
                    data: $form.serialize(),
                    success: MessageBox.customisedSuccessAsyncCallback("/logout"),
                    error: MessageBox.errorAsyncCallback
                });
            }
        }
    };



    function onReady(){
        $("form#changePassword").ketchup();
    }

    out.triggers = triggers;
    out.onReady = onReady;

    return out;

})((ChangePassword || {}), MessageBox, jQuery));

Common.register("ChangePassword", ChangePassword);
