/**
 * This file controls the validation of save and submit.
 *
 * To make a field validate for save used a class on the <input> of 'required'
 * and the validation data-validate(required, ...) To make a field validate for
 * submit used data-validate(required, ...) To make a field only validate if the
 * user has entered a value (an optional field) use data-validate(...)
 *
 */
var Validation = ((function(out, MessageBox, Constants, $, Validator) {
    "use strict";

    var trident = "Trident";

    var DATA_CALLBACKS = "callbacks";
    var DATA_PREVALIDATION_CALLBACKS = "prevalidation-callbacks";
    var DATA_POSTVALIDATION_CALLBACKS = "postvalidation-callbacks";
    var DATA_SAVE_VALIDATORS = "save-validators";
    var DATA_SUBMIT_VALIDATORS = "submit-validators";
    var VALIDATION_FAILED_CALLBACKS = "validation-failed-callbacks";

    var presubmitDialogData = "presubmit-dialog";

    var uniqueValidationRowId = 0;

    var triggers = {
        '[data-validate]' : {
            'change validate blur' : function() {
                Validator.validateElement($(this));
            }
        },

        'form' : {
            'add-presubmit-callback' : function(e, callback) {
                addCallback($(this), DATA_CALLBACKS, callback);
            },
            'run-presubmit-callbacks' : function() {
                runCallbacks($(this), DATA_CALLBACKS);
            },
            'add-prevalidation-callback' : function(e, callback) {
                addCallback($(this), DATA_PREVALIDATION_CALLBACKS, callback);
            },
            'run-prevalidation-callbacks' : function() {
                runCallbacks($(this), DATA_PREVALIDATION_CALLBACKS);
            },
            'add-postvalidation-callback' : function(e, callback) {
                addCallback($(this), DATA_POSTVALIDATION_CALLBACKS, callback);
            },
            'run-postvalidation-callbacks' : function() {
                runCallbacks($(this), DATA_POSTVALIDATION_CALLBACKS);
            },
            'set-presubmit-dialog' : function(e, callback) {
                $(this).data(presubmitDialogData, callback);
            },
            'add-validation-failed-callback' : function(e, callback) {
                addCallback($(this), VALIDATION_FAILED_CALLBACKS, callback);
            },
            'run-validation-failed-callbacks' : function(element, data) {
                runCallbacks($(this), VALIDATION_FAILED_CALLBACKS, data);
            }
        },
        '.save' : {
            'click' : function(evt) {
                evt.preventDefault();
                var $submittedForm = $(this).closest("form");
                doClientValidation($submittedForm, false, function() {
                    doServerValidation($submittedForm, false, false, function() {
                        var url = Constants.getBaseUrlPath() + "/save";
                        $submittedForm.attr("action", url);
                        $.bypassUnloadMsg = true;
                        // submit html form to avoid ketchup preventing submit.
                        $submittedForm.get(0).submit();
                    });
                });
            }
        },
        '.validate' : {
            'click' : function() {
                var $submittedForm = $(this).closest("form");

                var successFunction = function() {
                    MessageBox.displayMessageBox("Title", "Message", MessageBox.types.CONFIRMATION);
                    MessageBox.hideMessageBox(MessageBox.types.ERROR);
                };

                doClientValidation($submittedForm, true, function() {
                    doServerValidation($submittedForm, true, false, successFunction);
                });

            }
        },
        '.cancel' : {
            'click' : function() {
                var $submittedForm = $(this).closest("form");
                $.ajax({
                    url : Constants.getBaseUrlPath() + "/cancel",
                    type : "POST",
                    async : false,
                    data : $submittedForm.serialize(),
                    success : function(data) {
                        try {
                        window.location.href = Constants.getBaseUrl() + data;
                        } catch (e) {
                            if(navigator.userAgent.indexOf(trident) == -1) {
                                throw e;
                            }
                        }
                    }
                });
            }
        },
        'form.ketchup-form, form.validation-form' : {
            'fieldIsValid' : function(evt, form, el) {
                getErrorTooltipSpan(el).removeClass("error-tooltip");
                updateHelpMessage(el, true);
            },
            'fieldIsInvalid' : function(evt, form, el) {
                var shouldValidate = shouldFieldValidateForSave(el);
                getErrorTooltipSpan(el).toggleClass("error-tooltip", shouldValidate);
                updateHelpMessage(el, !shouldValidate);
            },
            'validate-submit' : function(evt, onSuccess, onFailure) {
                var $form = $(this);
                doClientValidation($form, true, function() {
                    doServerValidation($form, false, onSuccess, onFailure);
                }, onFailure);
            },
            'client-validate-submit' : function(evt, onSuccess, onFailure) {
                doClientValidation($(this), false, onSuccess, onFailure);
            },
            'server-validate-submit' : function(evt, onSuccess, onFailure) {
                doServerValidation($(this), false, false, onSuccess, onFailure);
            },
            'add-validator' : function(evt, validationFunc, failMessage) {
                addSubmitValidator($(this), validationFunc, failMessage);
            }
        },
    };

    function addCallback(caller, callbacksSetName, callback) {
        var callbacks = caller.data(callbacksSetName);
        if (typeof callbacks === "undefined") {
            callbacks = [];
        }
        var idx = callbacks.map(function(i) {
            return i.toString();
        }).indexOf(callback.toString());
        if (idx !== -1) {
            callbacks.splice(idx, 1);
        }
        callbacks.push(callback);
        caller.data(callbacksSetName, callbacks);
    }

    function runCallbacks(caller, callbacksSetName, data) {
        var callbacks = caller.data(callbacksSetName);
        if (callbacks) {
            for (var i = 0; i < callbacks.length; i++) {
                callbacks[i](data);
            }
        }
    }

    function handlePresubmitDialog($submittedForm, callback) {
        if ($submittedForm.data(presubmitDialogData)) {
            $submittedForm.data(presubmitDialogData)(callback);
        } else {
            callback();
        }
    }

    /*
     * This function will return true if the field is validated for save and
     * false if validated for submit. The 'required' class indicates a field is
     * required for save as draft validation. We must also validate fields that
     * aren't empty.
     */
    function shouldFieldValidateForSave($field) {
        return ($field.val() !== null && ($field.val().length > 0)) || $field.hasClass("required");
    }

    function doServerValidation($form, onSuccess, onFailure) {
        $form.trigger("run-presubmit-callbacks");
        var validateUrl = Constants.getBaseUrlPath() + "/validate";
        $.ajax({
            url : validateUrl,
            type : 'POST',
            data : $form.serialize(),
            dataType : 'json',
            success : function(data) {
                // Passed server validation
                if (data.length === 0) {
                    if (typeof onSuccess === 'function') {
                        onSuccess(data);
                    }
                    // Failed server validation
                } else {
                    MessageBox.populateErrorList(data);
                    if (typeof onFailure === 'function') {
                        onFailure(data);
                    }
                    $form.trigger("run-validation-failed-callbacks", [ data ]);
                }
            }
        });
    }

    function doClientValidation($form, isSubmit, onSuccess, onFailure) {
        if ($form.data("skipClientValidation") === true) {
            onSuccess();
            return;
        }

        function fillErrorContainer(incorrectFields, failedValidators) {
            var messages = [];
            var uniqueRows = {};
            $.each(incorrectFields, function(index, field) {
                var $errorText = getErrorTooltipSpan(field);
                if (!$errorText.data("row-validation-id")){
                    $errorText.data("row-validation-id", ++uniqueValidationRowId);
                }

                if (!uniqueRows[$errorText.data("row-validation-id")]){
                    uniqueRows[$errorText.data("row-validation-id")] = true;
                    $errorText.addClass("error-tooltip");
                    messages.push($errorText.data("invalid"));
                    var tabContainer = field.closest(".tab-content");
                    if (!tabContainer.is(":visible")) {
                        $("label[for='" + tabContainer.prev("input").attr('id') + "']").addClass("error");
                    }
                }
            });
            for (var i = 0; i < failedValidators.length; i++) {
                messages.push(failedValidators[i].msg);
            }
            MessageBox.populateErrorList(messages);
        }

        $form.trigger("run-prevalidation-callbacks");
        try {
            var invalidFields = [];
            var failedValidators = [];
            var fields = $form.find("[data-validate]:not(.disabled,:disabled), [data-validate][value!=''].validate-if-set");
            $(".error-tooltip").removeClass("error-tooltip");
            $("label.error").removeClass("error");
            if (!isSubmit) {
                fields = fields.filter(function() {
                    return shouldFieldValidateForSave($(this));
                });
            }

            fields.each(function() {
                var $field = $(this);
                if (!Validator.validateElement($field)) {
                    invalidFields.push($field);
                }
            });

            var validators = $form.data(isSubmit ? DATA_SUBMIT_VALIDATORS : DATA_SAVE_VALIDATORS);
            for ( var index in validators) {
                var validator = validators[index];
                if (!validator.func()) {
                    failedValidators.push(validator);
                }
            }

            // Passed client validation
            if (failedValidators.length === 0 && invalidFields.length === 0) {
                if (typeof onSuccess === 'function') {
                    onSuccess();
                }
                // Failed client validation
            } else {
                fillErrorContainer(invalidFields, failedValidators);
                if (typeof onFailure === 'function') {
                    onFailure();
                }
            }
        } finally {
            $form.trigger("run-postvalidation-callbacks");
        }
    }

    function doLimitedClientValidation($form, onSuccess, onFailure) {

        $form.trigger("run-prevalidation-callbacks");
        var invalidFields = [];
        var failedValidators = [];
        var fields = $form.find("[data-validate]:not(.disabled,:disabled), [data-validate][value!=''].validate-if-set");

        $(".error-tooltip").removeClass("error-tooltip");
        $("label.error").removeClass("error");

        fields.each(function() {
            if (!Validator.validateElement($(this))) {
                invalidFields.push($(this));
            }
        });

        $.each(invalidFields, function(index, field) {
            var $errorText = getErrorTooltipSpan(field);
            $errorText.addClass("error-tooltip");
            var tabContainer = field.closest(".tab-content");
            if (!tabContainer.is(":visible")) {
                $("label[for='" + tabContainer.prev("input").attr('id') + "']").addClass("error");
            }
        });

        if (failedValidators.length === 0 && invalidFields.length === 0) {
            if (typeof onSuccess === 'function') {
                onSuccess();
            }
        } else {
            if (typeof onFailure === 'function') {
                onFailure();
            }
        }
    }

    function updateHelpMessage(el, toggle) {
        var defaultSpan;
        defaultSpan = el.closest(".form-row").find(".information-tooltip");
        if (defaultSpan.length !== 0) {
            defaultSpan.toggle(toggle);
        }
    }

    /**
     * Function to try and get the span that contains the error tooltip, in some
     * cases (text area) need to have an inner form-col div and so the
     * grandparent is where to find the error tooltip.
     */
    function getErrorTooltipSpan(el) {
        var defaultSpan, specificTooltipSpan;

        defaultSpan = el.closest(".form-row").find("span[data-invalid]");
        if (defaultSpan.length !== 0) {
            return defaultSpan;
        }

        // You might want to use a specific tooltip selector if it's some
        // distance away in the DOM.
        // To use this add a data-error-tooltip-id attribute to the field that
        // contains the
        // data-validate attribute. Use the ID of your tooltip span.
        specificTooltipSpan = $("#" + el.attr("data-error-tooltip-id"));
        if (specificTooltipSpan.length !== 0) {
            return specificTooltipSpan;
        }
        // return empty jquery object if none found
        return defaultSpan;
    }

    function toggleError(el, show) {
        getErrorTooltipSpan(el).toggleClass("error-tooltip", show);
    }

    function addError(el) {
        toggleError(el, true);
    }

    function removeError(el) {
        toggleError(el, false);
    }

    function addSubmitValidator($form, func, failMessage) {
        var submitValidators = $form.data(DATA_SUBMIT_VALIDATORS);
        if (!submitValidators) {
            submitValidators = [];
        }
        submitValidators.push({
            func : func,
            msg : failMessage
        });
        $form.data(DATA_SUBMIT_VALIDATORS, submitValidators);
    }

    function addSaveValidator($form, func, failMessage) {
        var saveValidators = $form.data(DATA_SAVE_VALIDATORS);
        var submitValidators = $form.data(DATA_SUBMIT_VALIDATORS);
        if (!saveValidators) {
            saveValidators = [];
        }
        if (!submitValidators) {
            submitValidators = [];
        }
        var validator = {
            func : func,
            msg : failMessage
        };
        saveValidators.push(validator);
        submitValidators.push(validator);
        $form.data(DATA_SAVE_VALIDATORS, saveValidators);
        $form.data(DATA_SUBMIT_VALIDATORS, submitValidators);
    }

    function setErrorMessage(el, message) {
        var $errorTooltip = Validation.getErrorTooltipSpan(el);
        $errorTooltip.data('invalid', message);
        $errorTooltip.attr('data-invalid', message);
	}

	function addSubmitValidator($form, func, failMessage) {
		var submitValidators = $form.data(DATA_SUBMIT_VALIDATORS);
		if (!submitValidators) {
			submitValidators = [];
		}
		submitValidators.push({
			func : func,
			msg : failMessage
		});
		$form.data(DATA_SUBMIT_VALIDATORS, submitValidators);
	}

	function addSaveValidator($form, func, failMessage) {
		var saveValidators = $form.data(DATA_SAVE_VALIDATORS);
		var submitValidators = $form.data(DATA_SUBMIT_VALIDATORS);
		if (!saveValidators) {
			saveValidators = [];
		}
		if (!submitValidators) {
			submitValidators = [];
		}
		var validator = {
			func : func,
			msg : failMessage
		};
		saveValidators.push(validator);
		submitValidators.push(validator);
		$form.data(DATA_SAVE_VALIDATORS, saveValidators);
		$form.data(DATA_SUBMIT_VALIDATORS, submitValidators);
	}

    function onReady(){
        $("form.ketchup-form").each(function(){
            $(this).ketchup();
        });
    }

	out.getErrorTooltipSpan = getErrorTooltipSpan;
	out.setErrorMessage = setErrorMessage;
	out.addError = addError;
	out.removeError = removeError;
	out.toggleError = toggleError;
	out.doClientValidation = doClientValidation;
	out.doLimitedClientValidation = doLimitedClientValidation;

	out.triggers = triggers;
	out.onReady = onReady;

	/* globals SubmitDialog, MessageBox, Props, Constants, Validator */
	return out;

})((Validation || {}), MessageBox, Constants, jQuery, Validator));

/* globals Common */
Common.register("Validation", Validation);
