((function (Validator){
    "use strict";

    var DATE_REGEX = /(?:0[1-9]|[12][0-9]|3[01])\/(?:0[1-9]|1[0-2])\/(?:19|2\d{3})/;
    var DATETIME_REGEX = /(?:0[1-9]|[12][0-9]|3[01])\/(?:0[1-9]|1[0-2])\/(?:19|2\d{3}) ([0-1][0-9]|[2][0-3])(:)([0-5][0-9])/;

    var messages = {
        time: "Invalid time format.",
        datetime: "Invalid date-time format.",
        date: "Invalid date format.",
        datePast: "Provided date is in the past.",
        required: "The field is required.",
        alphanumeric: "Field value is not alphanumeric.",
        regex: "Field doesn't match a regex: {arg1}",
        decimalScale: "This should be a decimal field with width: {arg2} and scale: {arg3}",
        password: "A password should has at least one lowercase character, one uppercase and one digit.",
        matchPassword: "Password confirmation doesn't match the original.",
        minlength: "The field must have minimal number of {arg1}.",
        mail: "E-mail format is incorrect."
    };

    Validator.addValidator('email', messages.mail, function (form, el, value){
        return isEmail(value);
    });

    Validator.addValidator('valid_time', messages.time, function (form, el, value){
        if (!el.prop("disabled") && value.replace(/\s/g, '').match("^([0-1][0-9]|[2][0-3])(:)([0-5][0-9])$") === null){
            return false;
        }
        return true;
    });

    Validator.addValidator('valid_time_allowempty', messages.time, function (form, el, value){
        if (el.prop("disabled") || (value === "")){
            return true;
        }

        if (/^([0-1][0-9]|[2][0-3])(:)([0-5][0-9])$/.test(value.replace(/\s/g, ''))){
            return true;
        }

        return false;
    });

    // Only works for jquery date picker elements
    Validator.addValidator('valid_datetime', messages.datetime, function (form, el, value){
        if (el.prop("disabled") || (value === "")){
            return true;
        }
        if (value.trim().match("^([0-9][0-9][-/][0-9][0-9][-/][0-9][0-9][0-9][0-9]) (([0-1][0-9]|[2][0-3])(:)([0-5][0-9]))$") === null){
            return false;
        }
        return true;
    });

    Validator.addValidator('valid_date', messages.date, function (form, el, value){
        return checkDate(form, el, value);
    });

    Validator.addValidator('valid_date_allowempty', messages.date, function (form, el, value){
        if (value === ""){
            return true;
        }
        return checkDate(form, el, value);
    });

    function checkDate(form, el, value){
        if (!el.prop("disabled") && !DATE_REGEX.test(value)){
            return false;
        }
        return true;
    }

    function checkDateTime(form, el, value){
        if (!el.prop("disabled") && !DATETIME_REGEX.test(value)){
            return false;
        }
        return true;
    }

    Validator.addValidator('date_nonempty', messages.date, function (form, el, value){
        if (el.prop("disabled") || el.prop("readonly")){
            return true;
        }

        if (DATE_REGEX.test(value)){
            return true;
        }

        return false;
    });

    Validator.addValidator('date_not_in_past', messages.datePast, function (form, el, value){
        if (el.prop("disabled") || el.prop("readonly")){
            return true;
        }

        if (DATE_REGEX.test(value)){
            var dateParts = value.split("/"),
                enteredDate = new Date(parseInt(dateParts[2], 10), parseInt(dateParts[1], 10) - 1, parseInt(dateParts[0], 10));
            var now = new Date(),
                today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

            return today.getTime() <= enteredDate.getTime();
        }

        return false;
    });

    Validator.addValidator('valid_price', messages.price, function (form, el, value){
        if ((value !== '') && !el.prop("disabled") && value.replace(/\s/g, '').match("^([0-9]{1,6})(\\.[0-9][0-9])?$") === null){
            return false;
        }
        return true;
    });

    function requiredCheck(el, value){
        var type = ('' + el.attr('type')).toLowerCase();
        if (type === 'checkbox' || type === 'radio'){
            return (el.attr('checked') === true);
        }
        return (value.length !== 0);
    }

    function checkEmptyOrHasLength(value, len){
        var valueLength = value.length;
        return (valueLength === 0 || valueLength === parseInt(len, 10));
    }

    function requiredIfEnabled(el, value){
        if (el.prop('disabled')){
            return true;
        }
        return requiredCheck(el, value);
    }

    function requiredIfEditable(el, value){
        if (el.prop('disabled') || el.prop('readonly')){
            return true;
        }
        return requiredCheck(el, value);
    }

    Validator.addValidator('valid_requiredIfVisible', messages.required, function (form, el, value){
        if (el.is(':hidden') || el.prop('disabled')){
            return true;
        }
        return requiredCheck(el, value);
    });

    Validator.addValidator('requiredIfEnabled', messages.required, function (form, el, value){
        return requiredIfEnabled(el, value);
    });

    Validator.addValidator('requiredIfEditable', messages.required, function (form, el, value){
        return requiredIfEditable(el, value);
    });

    Validator.addValidator('alphanumeric', messages.alphanumeric, function (form, el, value){
        if (el.prop('disabled')){
            return true;
        }
        return /^[a-zA-Z0-9]*$/.test(value);
    });

    Validator.addValidator('alphanumeric-spaced', messages.alphanumeric, function (form, el, value){
        if (el.prop('disabled')){
            return true;
        }
        return /^[a-zA-Z0-9 ]*$/.test(value);
    });

    Validator.addValidator('regex', messages.regex, function (form, el, value, regex){
        if (!el.hasClass("disabled") && !el.is("[readonly]") && value.match(regex) === null){
            return false;
        }
        return true;
    });

    Validator.addValidator('decimalScale', messages.decimalScale, function (form, el, value, signed, width, scale){
        if (el.prop("disabled")){
            return true;
        }
        var minus = "";
        if (signed === "true"){
            minus = "-?";
        }
        if (value.replace(/\s/g, '').match("^" + minus + "(0|[1-9]([0-9]{0," + (width - scale - 1) + "}))(\\.[0-9]{1," + scale + "})?$") === null){
            return false;
        }
        return true;
    });

    Validator.addValidator('decimalScaleEmptyAllowed', messages.decimalScale, function (form, el, value, signed, width, scale){
        if (el.prop("disabled") || value === null || value === ""){
            return true;
        }
        var minus = "";
        if (signed === "true"){
            minus = "-?";
        }
        if (value.replace(/\s/g, '').match("^" + minus + "(0|[1-9]([0-9]{0," + (width - scale - 1) + "}))(\\.[0-9]{1," + scale + "})?$") === null){
            return false;
        }
        return true;
    });

    Validator.addValidator('strongPassword', messages.password, function (form, el, value){
        if (value === ""){
            return true;
        }
        var regex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
        return regex.test(value);
    });

    Validator.addValidator('matchPassword', messages.matchPassword, function (form, el, value, passwordField){
        var password = $((passwordField.substring(0, 1) === "#" ? "" : "#") + passwordField).val();
        return (el.val() === password);
    });

    Validator.addValidator('minlength', messages.minlength, function (form, el, value, min){
        return value.length >= min;
    });

    function isValidDate(ExpiryDate){
        ExpiryDate = $.trim(ExpiryDate);
        var objDate, // date object initialized from the ExpiryDate string
            mSeconds, // ExpiryDate in milliseconds
            day, // day
            month, // month
            year; // year
        // date length should be 10 characters (no more no less)
        if (ExpiryDate.length !== 10){
            return false;
        }
        // third and sixth character should be '-'
        if (ExpiryDate.substring(2, 3) !== '/' || ExpiryDate.substring(5, 6) !== '/'){
            return false;
        }
        // extract month, day and year from the ExpiryDate (expected format is
        // dd/mm/yyyy)
        // subtraction will cast variables to integer implicitly (needed
        // for !== comparing)
        month = ExpiryDate.substring(3, 5) - 1; // because months in JS start
        // from 0
        day = ExpiryDate.substring(0, 2) - 0;
        year = ExpiryDate.substring(6, 10) - 0;
        // test year range
        if (year < 1000 || year > 3000){
            return false;
        }
        // convert ExpiryDate to milliseconds
        mSeconds = (new Date(year, month, day)).getTime();
        // initialize Date() object from calculated milliseconds
        objDate = new Date();
        objDate.setTime(mSeconds);
        // compare input date and parts from Date() object
        // if difference exists then date isn't valid
        if (objDate.getFullYear() !== year || objDate.getMonth() !== month || objDate.getDate() !== day){
            return false;
        }
        // otherwise return true
        return true;
    }

    function isValidTime(ExpiryTime){
        ExpiryTime = $.trim(ExpiryTime);
        var timeex = /^([2][0-3]|[01]?[0-9])(:[0-5][0-9])?$/;
        return ExpiryTime.match(timeex);
    }

    function isNumber(value){
        return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
    }

    function contains(value, word){
        return value.indexOf(word) !== -1;
    }

    function isEmail(value){
        return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
            .test(value);
    }

    function isUrl(value){
        return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
            .test(value);
    }

    function isUsername(value){
        return /^([a-zA-Z])[a-zA-Z_-]*[\w_-]*[\S]$|^([a-zA-Z])[0-9_-]*[\S]$|^[a-zA-Z]*[\S]$/.test(value);
    }

    function inputsWithName(form, el){
        return $('input[name="' + el.attr('name') + '"]', form);
    }

    function inputsWithNameNotSelf(form, el){
        return inputsWithName(form, el).filter(function (){
            return ($(this).index() !== el.index());
        });
    }

    function getKetchupEvents(el){
        var events = el.data('events').ketchup, retArr = [];

        for (var i = 0; i < events.length; i++){
            retArr.push(events[i].namespace);
        }

        return retArr.join(' ');
    }

    function bindBrothers(form, el){
        inputsWithNameNotSelf(form, el).bind(getKetchupEvents(el), function (){
            el.ketchup('validate');
        });
    }

    /* globals Validator */
})(Validator));
