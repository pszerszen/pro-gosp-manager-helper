/**
 * Global constants.
 */
var Constants = ((function(out) {
    "use strict";

    function makeWebGetterFunction(constant) {
        return function() {
            return out.getBasePath() + constant + "/";
        };
    }

    out.getUserRoot = makeWebGetterFunction("users");
    out.getValidateBaseUrlPath = makeWebGetterFunction("validate");
    out.getSaveBaseUrlPath = makeWebGetterFunction("save");
    out.getUpdateBaseUrlPath = makeWebGetterFunction("update");
    out.getEditBaseUrlPath = makeWebGetterFunction("edit");
    out.getDeleteBaseUrlPath = makeWebGetterFunction("delete");

    if (typeof out.getBasePath !== "undefined") {
        out.getBaseUrlPath = out.getBasePath;
    }

    return out;
})((Constants || {})));
