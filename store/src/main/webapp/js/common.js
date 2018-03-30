var Common = (function(out, $) {
    "use strict";

    var dependencies = {};
    var onReadyFunctions = [];
    var onLoadFunctions = [];

    var setEvents = function(eventMap) {
        for ( var className in eventMap) {
            var selector = getCachedSelector(className);
            for ( var listenEvent in eventMap[className]) {
                var action = eventMap[className][listenEvent];
                if ($.isArray(action)) {
                    for (var i = 0; i < action.length; i++) {
                        registerEvent(selector, listenEvent, action[i]);
                    }
                } else {
                    registerEvent(selector, listenEvent, action);
                }
            }
        }
    };
    setEvents.selectorCache = {};

    function getCachedSelector(className) {
        if (setEvents.selectorCache[className]) {
            return setEvents.selectorCache[className];
        }
        var selector = $(className);
        setEvents.selectorCache[className] = selector;
        return selector;
    }

    function registerEvent(selector, listenEvent, handler) {
        if (typeof handler === 'function') {
            selector.on(listenEvent, handler);
        } else if (typeof handler === 'object') {
            var subSelector = handler.selector;
            var f = handler.action;
            selector.on(listenEvent, subSelector, f);
        } else {
            alert("invalid type for " + selector + ", with action of: " + handler);
            throw new Error("invalid type for " + selector + ", with action of: " + handler);
        }
    }

    function warn(msg) {
        if (console.warn) {
            console.warn(msg);
        } else {
            console.log(msg);
        }
    }

    function register(name, obj, rebindSelector) {
        if (typeof obj === "undefined") {
            console.error("Module '" + name + "' has not returned out object!");
        } else {
            if (name in dependencies && !rebindSelector) {
                warn("Dependecy " + name + " is being loaded twice, fix now. This may throw an error in future");
            }
            if (typeof obj.triggers === 'object') {
                setEvents(obj.triggers);
            }

            if (typeof obj.onReady === 'function') {
                onReadyFunctions.push(obj.onReady);
            }

            if (typeof obj.init === 'function') {
                obj.init(!rebindSelector ? "" : rebindSelector);
            }

            if (typeof obj.onLoad === 'function') {
                onLoadFunctions.push(obj.onLoad);
            }

            dependencies[name] = obj;
        }
    }

    function rebind(registerObjects, rebindSelector) {
        // clear cache to avoid stale jQuery object references.
        setEvents.selectorCache = {};
        for (var i = 0; i < registerObjects.length; i++) {
            var newTrigger = {};
            var triggers = registerObjects[i].triggers;
            for ( var selector in triggers) {
                newTrigger[rebindSelector + " " + selector] = triggers[selector];
            }
            register("ReboundTo_" + rebindSelector + "_" + i, {
                init : registerObjects[i].init,
                triggers : newTrigger
            }, rebindSelector);
        }
    }

    $(function() {
        // clear cache to avoid stale jQuery object references.
        setEvents.selectorCache = {};
        for (var i = 0; i < onReadyFunctions.length; i++) {
            onReadyFunctions[i]();
        }
    });

    $(window).load(function() {
        for (var i = 0; i < onLoadFunctions.length; i++) {
            onLoadFunctions[i]();
        }
    });

    out.rebind = rebind;
    out.register = register;
    out.registerEvent = registerEvent;

    return out;

}((Common || {}), jQuery));