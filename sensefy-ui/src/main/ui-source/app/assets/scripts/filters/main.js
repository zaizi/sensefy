(function () {
    'use strict';
    angular.module('SensefyFilters', []).filter("excerpt", function () {
        return function (text, numCharacters, end) {
            if (numCharacters == null) {
                numCharacters = 100;
            }
            if (end == null) {
                end = "...";
            }
            if (text.length > numCharacters) {
                return text.substring(0, numCharacters) + end;
            } else {
                return text;
            }
        };
    }).filter("highlight", function () {
        return function (text, textToHighlight, element, clazz) {
            var escapeText, hlPieces, resultText;
            if (element == null) {
                element = "span";
            }
            if (clazz == null) {
                clazz = "hl";
            }
            escapeText = function (text) {
                var charToEscape, i, newText, _i, _len;
                charToEscape = ["*", ":", "?", "+", "[", "]", "(", ")"];
                newText = "";
                for (_i = 0, _len = text.length; _i < _len; _i++) {
                    i = text[_i];
                    newText += charToEscape.indexOf(text.charAt(i)) >= 0 ? "\\" + i : i;
                }
                return newText;
            };
            if (text === void 0 || text === null) {
                return text;
            }
            hlPieces = textToHighlight.split(" ");
            resultText = text;
            angular.forEach(hlPieces, function (piece, key) {
                var regExpStr, regularExp;
                regExpStr = "(?:^|\\b)(" + escapeText(piece) + ")";
                regularExp = new RegExp(regExpStr, "i");
                return resultText = resultText.replace(regularExp, function (match, p1, offset, all) {
                    var str;
                    return str = '<' + element + " class='" + clazz + "'>" + p1 + '</' + element + '>';
                });
            });
            return resultText;
        };
    }).filter("initials", function () {
        return function (text, maxSize) {
            var piece, pieces, result, _i, _len;
            if (maxSize == null) {
                maxSize = 2;
            }
            pieces = text.split(/\s+|-/);
            pieces = pieces.slice(0, maxSize);
            result = "";
            for (_i = 0, _len = pieces.length; _i < _len; _i++) {
                piece = pieces[_i];
                result += piece.substring(0, 1).toUpperCase();
            }
            return result;
        };
    }).filter("sanitizeIdForURI", function ($window) {
        return function (identifier) {
            var encoded, regExp;
            encoded = $window.encodeURI(identifier);
            regExp = new RegExp("\/", "g");
            return encoded.replace(regExp, '[SL]');
        };
    });

}).call(this);