var MessageBox = ((function (out){
    "use strict";

    var types = {
        ERROR: "error",
        CONFIRMATION: "confirmation",
        INFORMATION: "information",
    };

    var triggers = {
        '.close-message-box': {
            'click': function (){
                $(this).closest(".message-box").hide();
            }
        }
    };

    // Add a error message with a class. Can also specify if the class is unique
    // (don't add the error if that class already exists in the list).
    function addErrorMessageWithClass(message, messageClass, isClassUnique){
        var $errorContainer = $("#error-container");
        return addErrorMessageWithClass(message, messageClass, isClassUnique, $errorContainer);
    }

    function addErrorMessageWithClass(message, messageClass, isClassUnique, errorContainer){
        if (!errorContainer || !(errorContainer.length > 0)){
            errorContainer = $("#error-container");
        }
        var $errorList = errorContainer.find(".content").find("ul");

        if (isClassUnique){
            if ($errorList.find("li." + messageClass).length !== 0){
                return;
            }
        }
        $errorList.append($("<li>").text(message).addClass(messageClass));
        errorContainer.toggle(errorContainer.find("li").length > 0);
    }

    function removeErrorMessage(selector){
        var $errorContainer = $("#error-container");
        return removeErrorMessage(selector, $errorContainer);
    }

    function removeErrorMessage(selector, errorContainer){
        if (!errorContainer || !(errorContainer.length > 0)){
            errorContainer = $("#error-container");
        }
        var $errorList = errorContainer.find(".content").find("ul");
        $errorList.find(selector).remove();
        errorContainer.toggle(errorContainer.find("li").length > 0);
    }

    function populateErrorList(messages){
        var $errorContainer = $("#error-container");
        var $errorsContent = $errorContainer.find(".content");
        var $errorList = $errorsContent.find("ul");
        if ($errorList.length === 0){
            $errorsContent.append($("<ul></ul>"));
            $errorList = $errorsContent.find("ul");
        }
        $errorList.html("");
        for (var i = 0; i < messages.length; i++){
            $errorList.append($("<li>").text(messages[i]));
        }
        $errorContainer.toggle($errorContainer.find("li").length > 0);
        window.scrollTo(0, 0);
    }

    function populateErrorListInsideElement($rootElement, messages){
        var $errorContainer = $rootElement.is(".error-container") ? $rootElement : $rootElement.find(".error-container");
        var $errorList = $errorContainer.find(".content").find("ul");
        $errorList.html("");
        for (var i = 0; i < messages.length; i++){
            $errorList.append($("<li>").text(messages[i]));
        }
        $errorContainer.toggle($errorContainer.find("li").length > 0);
        window.scrollTo(0, 0);
    }

    function populateWarningList($rootElement, messages){
        var $warningContainer = $rootElement.find(".warning-container");
        var $warningList = $warningContainer.find(".content").find("ul");
        $warningList.html("");
        for (var i = 0; i < messages.length; i++){
            $warningList.append($("<li>").text(messages[i]));
        }
        $warningContainer.toggle($warningContainer.find("li").length > 0);
        window.scrollTo(0, 0);
    }

    function emptyWarningList($rootElement){
        var $warningContainer = $rootElement.find(".warning-container");
        var $warningList = $warningContainer.find(".content").find("ul");
        $warningList.find("li").remove();
    }

    function displayMessageBox(title, message, status){
        var container = messageBoxFromStatus(status).show();
        container.find(".heading h3").text(title);
        container.find(".content").text(message);
        window.scrollTo(0, 0);
        if (status === "confirmation"){
            hideConfirmationAfterTimeout(container);
        }
    }

    function hideMessageBox(status){
        messageBoxFromStatus(status).hide();
    }

    function messageBoxFromStatus(status){
        return $("#" + status + "-container");
    }

    var onReady = function (){
        $("#binding-errors-container").find("li").each(function (){
            $(this).siblings("li:contains(" + $(this).text() + ")").remove();
        });

        if (!$("#confirmation-container").is(":hidden")){
            hideConfirmationAfterTimeout($("#confirmation-container"));
        }

    };

    function hideConfirmationAfterTimeout(container){
        window.setTimeout(function (){
            container.slideUp({
                duration: 50
            });
        }, 8000);
    }

    function customisedSuccessAsyncCallback(redirectUrl){
        return function(data){
            MessageBox.displayMessageBox("Success", data, MessageBox.types.INFORMATION);
            setTimeout(function(){
               window.location.href = redirectUrl;
            }, 3000);
        }
    }

    function successAsyncCallback(data){
        MessageBox.displayMessageBox("Success", data, MessageBox.types.INFORMATION);
        setTimeout(navigateToMainPage, 3000);
    }

    function errorAsyncCallback(xhr, textStatus, errorThrown){
        MessageBox.displayMessageBox("Unsuccessful", errorThrown + ":\n" + xhr.responseText, MessageBox.types.ERROR);
    }

    function navigateToMainPage(){
        window.location.href = Constants.getBaseUrlPath();
    }

    out.successAsyncCallback = successAsyncCallback;
    out.customisedSuccessAsyncCallback = customisedSuccessAsyncCallback;
    out.errorAsyncCallback = errorAsyncCallback;
    out.displayMessageBox = displayMessageBox;
    out.populateErrorList = populateErrorList;
    out.populateErrorListInsideElement = populateErrorListInsideElement;
    out.populateWarningList = populateWarningList;
    out.emptyWarningList = emptyWarningList;
    out.addErrorMessageWithClass = addErrorMessageWithClass;
    out.removeErrorMessage = removeErrorMessage;
    out.hideMessageBox = hideMessageBox;
    out.types = types;

    out.onReady = onReady;
    out.triggers = triggers;

    return out;

})((MessageBox || {}), jQuery));

Common.register("MessageBox", MessageBox);
