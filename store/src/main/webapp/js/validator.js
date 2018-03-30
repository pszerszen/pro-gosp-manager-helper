var Validator = ((function(out) {
    "use strict";

    var VALIDATE_DATA = "validate";
    var VALIDATE_PREFIX = "validate";

    var validators = {};

    out.validateElement = function(ele) {
        var form = ele.closest("form");
        var val = ele.val();
        var dataValue = ele.data(VALIDATE_DATA);
        var elementValidators = getArguments(dataValue.replace(VALIDATE_PREFIX, ""));
        for (var i = 0; i < elementValidators.length; i++) {
            var validator = extractValidatorAndArguments(elementValidators[i]);
            if (typeof validator.validator === "undefined") {
                console.error("couldn't find validator: " + validator.name);
                return;
            }
            if (!validator.validator.apply(this, [ form, ele, val ].concat(getArguments(validator.args)))) {
                ele.trigger("fieldIsInvalid", [ form, ele ]);
                return false;
            }
        }
        ele.trigger("fieldIsValid", [ form, ele ]);
        return true;
    };

    /**
     * Launches just the selected validators for the specified element. Validation messages will be displayed on upper error-container list only.
     *
     * @param ele {Object} jQuery element
     * @param errorContainer {Object} error container jQuery element
     * @param validatorNamesAndMessages {Map} map of validators and validation message code
     *
     * @returns true if element is valid, false otherwise
     */
    out.validateElementWithChosenValidators = function(ele, errorContainer, validatorNamesAndMessages){
        var form = ele.closest("form");
        var errors = errorContainer.find(".content").find("ul");
        var val = ele.val();
        var validations = $.map(validatorNamesAndMessages, function(message, elementValidator){
            var validator = extractValidatorAndArguments(elementValidator);
            if (typeof validator.validator === "undefined") {
                console.error("couldn't find validator: " + validator.name);
                return;
            }
            if (!validator.validator.apply(this, [ form, ele, val ].concat(getArguments(validator.args)))) {
                errors.append($("<li>").text(message));
                errorContainer.toggle(true);
                return false;
            }
            return true;
        });
        for(var i = 0;i<validations.length; i++){
            if(!validations[i]){
                return false;
            }
        }
        errorContainer.toggle(false);
        return true;
    };

    function extractValidatorAndArguments(elementValidator){
        var bracketIndex = elementValidator.indexOf("(");
        var validatorName = bracketIndex === -1 ? elementValidator : elementValidator.substring(0, bracketIndex);
        var args = bracketIndex === -1 ? "" : elementValidator.substring(bracketIndex);
        var validator = validators[validatorName];

        return {validator : validator,
                name : validatorName,
                args : args};
    }

    // Takes something like "(arg1, arg2, arg3(subArg1, subArg2)" and returns
    // ["arg1", "arg2", "arg3(subArg1, subArg2)"]
    function getArguments(args) {
        var listOfArguments = [];
        if (args[0] === "(" && args[args.length - 1] === ")") {
            args = args.substring(1, args.length - 1);
            var index = 0;
            while (index !== -1) {
                var nextComma = args.indexOf(",", index + 1);
                var nextOpenBracket = args.indexOf("(", index + 1);
                if (nextComma > nextOpenBracket && nextOpenBracket !== -1) {
                    // This comma could be inside brackets
                    var nextClosedBracket = args.indexOf(")", nextOpenBracket);
                    nextComma = args.indexOf(",", nextClosedBracket);
                }
                listOfArguments.push(args.substring(args[index] === "," ? index + 1 : index, nextComma === -1 ? args.length : nextComma).trim());
                index = nextComma;
            }
        }
        return listOfArguments;
    }

    /**
     * Function for binding the new validation function.
     *
     * @param name of the validation added in data-validate of validated input
     * @param validator function returning a boolean saying if the field is valid
     */
    out.addValidator = function(name, message, validator) {
        validators[name] = validator;
        $.ketchup.validation(name, message, validator);
    };

    /**
     * Calls single validator on a element, but does not change any element state,
     * specifically it does not show or hide default validation hint for the element.
     * This method should be used for any secondary checks.
     *
     * @param name of the validator to execute
     * @param ele DOM element to validate
     * @param ... all subsequent arguments will be passed to the validator
     * @return boolean, result returned from the validator
     */
    out.callSingleValidator = function(name, ele) {
        var form = ele.closest("form");
        var val = ele.val();
        var args = [ form, ele, val ];
        for (var i = 2; i < arguments.length; i++) {
            args.push(arguments[i]);
        }
        return validators[name].apply(this, args);
    };

    return out;

})(Validator || {}));

/* globals Common */
Common.register("Validator", Validator);
