var Submit = ((function (out, MessageBox, $){

    var $form = $("form.form.ketchup-form");

    var triggers = {
        '#save-button': {
            'click': function (){
                validateForm(function (){
                    $.ajax({
                        url: Constants.getSaveBaseUrlPath(),
                        dataType: 'json',
                        type: 'POST',
                        data: $form.serialize(),
                        success: function (){
                            MessageBox.displayMessageBox("Success", "Properly saved, continuing to main page...", MessageBox.types.INFORMATION);
                            setTimeout(navigateToMainPage, 3000);
                        },
                        error: function (){
                            MessageBox.displayMessageBox("Unsuccessful", "The persition has not been performed.", MessageBox.types.ERROR);
                        }
                    });
                });
            }
        },
        '#edit-button': {
            'click': function (){
                validateForm(function (){
                    $.ajax({
                        url: Constants.getUpdateBaseUrlPath(),
                        dataType: 'json',
                        type: 'POST',
                        data: $form.serialize(),
                        success: function (data){
                            MessageBox.displayMessageBox("Success", "Properly updated, continuing to main page...", MessageBox.types.INFORMATION);
                            setTimeout(navigateToMainPage, 3000);
                        },
                        error: function (){
                            MessageBox.displayMessageBox("Unsuccessful", "The persition has not been performed.", MessageBox.types.ERROR);
                        }
                    });
                });
            }
        },
        '#reset-password-button': {
            'click':function(){
                $.ajax({
                    url: Constants.getBaseUrlPath() + "resetPassword/" + $form.find("input#id").val(),
                    dataType: 'text',
                    type: 'PUT',
                    success: MessageBox.successAsyncCallback,
                    error: MessageBox.errorAsyncCallback
                });
            }
        }
    };

    function validateForm(successCallback){
        $(".error-container").hide();
        // client validation
        $form.find("[data-validate]").each(function(){
            $(this).trigger("blur");
        });
        if($(".ketchup-error").length > 0){
            return;
        }
        // server validation
        $.ajax({
            url: Constants.getValidateBaseUrlPath(),
            dataType: 'json',
            type: 'POST',
            data: $form.serialize(),
            success: function (data){
                if (data.length > 0){
                    MessageBox.populateErrorList(data);
                } else {
                    successCallback();
                }
            }
        });
    }

    function navigateToMainPage(){
        window.location.href = Constants.getBaseUrlPath();
    }

    out.triggers = triggers;

    return out;
})((Submit || {}), MessageBox, jQuery));

Common.register("Submit", Submit);