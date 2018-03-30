var Shared = (function(out) {
    "use strict";

    var optionsDiv = ".options-div";

    var ajaxInterval = "";

    var tickChar = "\u2714";
    var crossChar = "\u2718";
    var tickEscapeChar = "&#x2714";
    var decimalCharacters = /[\.\-+0-9]/;
    var nonDecimalCharacters = /[^\.\-+0-9]/;
    var digitCharactersOnly =/[0-9+]/;
    var monthAndYearCharacters = /[\/0-9]/;


    String.prototype.capitalise = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    };

    /**
     * Format messages with arguments, by replacing the place-holders ("{x}") with values from second argument, an array of values.
     */
    function resolveMessageArgs(message, listOfArgs) {
        return message.replace(/{(\d+)}/g, function(match, number) {
            var arg = listOfArgs[+number];
            return typeof arg != '' ? arg : match;
        });
    }

    function removeLoadingSymbolWhenReady(button) {
        $(document).off(".buttons");
        setTimeout(function() {
            // if there are ajax calls going then wait for them to end.
            if ($.active !== 0) {
                $(document).on("ajaxStop.buttons", removeLoadingSymbolWhenReady(button));
            } else {
                button.removeClass("loading");
            }
        }, 0);
    }

    var triggers = {
        '.button' : {
            'click' : function(evt) {
                var button = $(this);
                if ($(this).hasClass("disabled")) {
                    evt.preventDefault();
                    evt.stopImmediatePropagation();
                } else {
                    button.addClass("loading");
                    removeLoadingSymbolWhenReady(button);
                }

            }
        },
        'input.uppercase' : {
            'blur' : function(evt) {
                this.value = this.value.toLocaleUpperCase();
            }
        },
        'input.digits-only-input' : {
            'keypress' : function(e) {
                return filterKeyPress(e, digitCharactersOnly);
            }
        },
        /* Counter mechanism for text field that have a counter */
        '.text-counting-field' : {
            'keyup keydown' : function() {
                var $textArea = $(this);
                var $counter = $("#" + $textArea.data("counter"));
                var maxLength = $textArea.data("length");
                var characterCount = $textArea.val().length;
                $counter.text(maxLength - characterCount);
                if (maxLength < characterCount) {
                    $counter.addClass("invalid-data-color");
                } else {
                    $counter.removeClass("invalid-data-color");
                }
            }
        },
        '.toggle-section' : {
            'click' : function() {
                var checked = $(this).is(":checked");
                var optionsContainer = $(this).closest(optionsDiv).parent();
                optionsContainer.find(".toggle-section").prop("checked", checked);
            }
        },
        'nav li.first' : {
            'click' : function() {
                $(this).toggleClass("active");
            }
        },
        '.no-new-lines' : {
            'keypress' : function(evt) {
                if (evt.keyCode === 13) {
                    return false;
                }
            }
        },
        'input.price-input' : {
            'keypress' : function(e) {
                return filterKeyPress(e, decimalCharacters);
            },
            'change' : function() {
                $(this).val(formatPrice($(this).val(), true));
            }
        },
        'input.decimal-2-input' : {
            'keypress' : function(e) {
                return filterKeyPress(e, decimalCharacters);
            },
            'change' : function() {
                $(this).val(formatDecimal($(this).val(), 2));
            }
        },
        'input.capitalised' : {
            'change' : function() {
                $(this).val($(this).val().capitalise());
            },
            'blur' : function() {
                $(this).val($(this).val().capitalise());
            }
        },
        'v.integer-input' : {
            'keypress' : function(e) {
                return filterKeyPress(e, digitCharactersOnly);
            }
        },
        'input.month-year-input' : {
            'keypress' : function(e) {
                return filterKeyPress(e, monthAndYearCharacters);
            }
        }

    };

    function getMainForm() {
        var form = $("#stage").find(".ketchup-form");
        if (form.length > 1) {
            console.error("More than one form returned from Shared.getMainForm()");
        }
        return form;
    }

    function tickCross(tick) {
        if(tick === tickChar || tick === crossChar){
            return tick;
        }
        if (tick === "true") {
            return tickChar;
        }
        return crossChar;
    }

    function isTick(value) {
        return (value.indexOf(tickChar) !== -1) || (value.indexOf(tickEscapeChar) !== -1);
    }

    function stringToDateTime(dateTimeString) {
        return $.datepicker.parseDateTime("dd/mm/yy", "hh:mm", dateTimeString);
    }

    /**
     * Converts a Javascript date into the correct format
     * for entry into textbox.
     *
     *  example output: 17/06/2015
     */
    function dateTimeToString(dateTime) {
        var day = dateTime.getDate();
        var month = dateTime.getMonth() + 1; // javascript months are zero-based.
        var year = dateTime.getFullYear();
        day = (day.toString().length === 1) ? "0" + day : day;
        month = (month.toString().length === 1) ? "0" + month : month;
        // build date string
        var dateString = day + "/" + month + "/" + year;
        return dateString;
    }

    /**
     * Yoda DateTime to text
     */
    function yodaDateTimeToString(dateTime) {
        var day = dateTime.dayOfMonth;
        var month = dateTime.monthOfYear; // javascript months are zero-based.
        var year = dateTime.year;
        day = (day.toString().length === 1) ? "0" + day : day;
        month = (month.toString().length === 1) ? "0" + month : month;
        // build date string
        var dateString = day + "/" + month + "/" + year;
        return dateString;
    }

    /**
     * Make a "selected" check-box for the table row.
     *
     * @param rowNum - row number
     * @param attrs - object representing extra attributes to add to the table.
     *          ex.: { data-pmsid: "10000", data-name: "Xxx" }
     *          pass empty object or null if You do not use it.
     * @param data - object representing extra "data-" attributes to add to the table.
     *          example, equivalent of above: { pmsid: "10000", name: "Xxx" }
     *          pass empty object or null if You do not use it.
     */
    function buildCheckBoxInput(rowNum, attrs, data) {
        var $checkbox = $("<input>").attr("type", "checkbox").addClass("check-result").attr("id", "check-result-" + rowNum);
        if (attrs) {
            for (var key in attrs) {
                if (attrs.hasOwnProperty(key)) {
                    $checkbox.attr(key, attrs[key]);
                };
            };
        }
        if (data) {
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    //Note: do not use a "checkbox.data(key, data[key])" here, it will not work
                    $checkbox.attr("data-" + key, data[key]);
                };
            };
        }
        return $("<span>").append($checkbox).html();
    }

    /**
     * Make a hidden input, ex. for binding
     *
     * @param name the name
     * @param value the value
     */
    function buildHiddenInput(name, value) {
        return $("<input>").attr("type", "hidden").attr("name", name).val(value);
    }

    function filterKeyPress(event, validCharactersRegex) {
        if (event.ctrlKey || event.metaKey) {
            return true;
        }
        var charCode = (typeof event.which == "undefined") ? event.keyCode : event.which;
        if (charCode >= 32) {
            var charStr = String.fromCharCode(charCode);
            if (!(charStr.match(validCharactersRegex))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Formats input, but only if the input is not empty.
     * Any characters that is not a digit, dot, minus or plus will be stripped, so it is safe to reformat already formatted input.
     *
     * @param input a input, as string or number
     * @param decimalPlaces a expected number of decimal places
     */
    function formatDecimal(input, decimalPlaces) {
        var output = input;
        if (typeof output !== "string" && typeof output !== "number") {
            output = "" + output;
        }
        if (typeof output === "string") {
            output = output.replace(nonDecimalCharacters, '');
            if (output === "") {
                return input;
            }
            if (!$.isNumeric(output)) {
                return input;
            }
            output = +output; //convert to number
            if (output > Number.MAX_SAFE_INTEGER) {
                //way too high, something is wrong, return original
                return input;
            }
        }
        return output.toFixed(decimalPlaces);
    }

    function getUrlParam(param){
        var url = window.location.search.substring(1);
        var params = url.split('&');
        for(var i = 0; i < params.length; i++){
            var currentParam = params[i].split('=');
            if(currentParam[0] == param){
                return currentParam[1];
            }
        }
    }

    /**
     * Formats input, and decorate it with pound, but only if the input is not empty.
     * Any characters that is not a digit, dot, minus or plus will be stripped, so it is safe to reformat already formatted input.
     * @param input a price, as string or number
     */
    function formatPrice(input) {
        var output = formatDecimal(input, 2);
        if (!output) {
            return output;
        } else {
            return output.charAt(0) == '-' ? '-' + output.substring(1) : output;
        }
    }

    function openInNewTab(url) {
		var tab = window.open(url, '_blank');
		if(tab != null){
		    tab.focus();
		}
	}


    out.getMainForm = getMainForm;
    out.tickCross = tickCross;
    out.isTick = isTick;
    out.stringToDateTime = stringToDateTime;
    out.triggers = triggers;
    out.resolveMessageArgs = resolveMessageArgs;
    out.buildCheckBoxInput = buildCheckBoxInput;
    out.buildHiddenInput = buildHiddenInput;
    out.dateTimeToString = dateTimeToString;
    out.yodaDateTimeToString = yodaDateTimeToString;
    out.getUrlParam = getUrlParam;
    out.openInNewTab = openInNewTab;

    return out;

})((Shared || {}));

/* globals Common */
Common.register("Shared", Shared);