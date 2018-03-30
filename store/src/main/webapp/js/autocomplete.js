var Autocomplete = (function (out, $) {
    "use strict";

    var REGEX_EMAIL = '([a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*@' +
        '(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)';

    var triggers = {
        'select.selectize': {
            'change': function () {
                var that = $(this);
                var selected = that.closest("div").find(".selected");
                that.closest("div").find("input.object-id").val(selected.find(".hidden").text());
            }
        }
    };

    function onReady() {
        $("select.selectize").each(function (i) {
            var that = $(this);
            that.selectize({
                create: false,
                valueField: 'id',
                labelField: 'name',
                searchField: ['name', 'description'],
                options: [],
                render: {
                    option: function (item, escape) {
                        return '<div>' +
                            '<span class="title">' +
                            '<span class="name">' + escape(item.name) + '</span>' +
                            '</span>' +
                            '<span class="description">' + escape(item.description) + '</span>' +
                            '<span class="hidden">' + escape(item.id) + '</span>' +
                            '</div>';
                    }
                },
                load: function (query, callback) {
                    if (!query.length) {
                        return callback();
                    }
                    $.ajax({
                        url: that.data("url") + "?query=" + query + "&max=" + that.data("max-results"),
                        dataType: "json",
                        type: 'GET',
                        success: function (data) {
                            callback(data);
                        },
                        error: function () {
                            callback();
                        }
                    });
                }
            });
        });
        $("select.selectize-mail").each(function () {
            var that = $(this);
            that.selectize({
                persist: false,
                maxItems: null,
                valueField: 'mail',
                labelField: 'name',
                searchField: ['name', 'mail'],
                options: [],
                render: {
                    item: function (item, escape) {
                        return '<div>' +
                            '<span class="name">' + escape(item.name) + '</span>' +
                            '<span class="email">' + escape(item.mail) + '</span>' +
                            '</div>';
                    },
                    option: function (item, escape) {
                        var label = item.name || item.mail;
                        var caption = item.name ? item.mail : null;
                        return '<div>' +
                            '<span class="label" style="color: #0d3349">' + escape(label) + '</span>' +
                            (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                            '</div>';
                    }
                },
                createFilter: function (input) {
                    var match, regex;

                    // email@address.com
                    regex = new RegExp('^' + REGEX_EMAIL + '$', 'i');
                    match = input.match(regex);
                    if (match) return !this.options.hasOwnProperty(match[0]);

                    // name <email@address.com>
                    regex = new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i');
                    match = input.match(regex);
                    if (match) return !this.options.hasOwnProperty(match[2]);

                    return false;
                },
                create: function (input) {
                    if ((new RegExp('^' + REGEX_EMAIL + '$', 'i')).test(input)) {
                        return {email: input};
                    }
                    var match = input.match(new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i'));
                    if (match) {
                        return {
                            mail: match[2],
                            name: $.trim(match[1])
                        };
                    }
                    alert('Invalid email address.');
                    return false;
                },
                load: function (query, callback) {
                    if (!query.length) {
                        return callback();
                    }
                    $.ajax({
                        url: that.data("url") + "?query=" + query,
                        dataType: "json",
                        type: 'GET',
                        success: function (data) {
                            callback(data);
                        },
                        error: function () {
                            callback();
                        }
                    });
                }
            });
        });

        $("input.object-id").each(function () {
            var that = $(this);
            var id = that.val();
            if (id.length > 0) {
                var $name = that.parent().find("input.object-name");
                that.parent().find(".selectize-input").find("input").val($name.val());
            }
        });
    }

    out.triggers = triggers;
    out.onReady = onReady;

    return out;
})((Autocomplete || {}), jQuery);

Common.register("Autocomplete", Autocomplete);
