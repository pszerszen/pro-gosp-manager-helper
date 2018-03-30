var Messaging = ((function (out, MessageBox, $){
    var triggers = {
        '#send-message': {
            'click': function (){
                $.ajax({
                    url: $(this).closest("form").attr("action"),
                    dataType: 'text',
                    type: 'POST',
                    data: $.param(collectMessage(), true),
                    success: MessageBox.successAsyncCallback,
                    error: MessageBox.errorAsyncCallback
                });
            }
        }
    };

    function collectMessage(){
        return {
            messageContent: $("#msg-content").val(),
            toMails: $("#to-input").val(),
            ccMails: $("#CC-input").val()
        }
    }

    function navigateToMainPage(){
        window.location.href = Constants.getBaseUrlPath();
    }

    out.triggers = triggers;

    return out;
})((Messaging || {}), MessageBox, jQuery));

Common.register("Messaging", Messaging);