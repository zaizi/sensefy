/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   search directive is functioned.
 */

(function () {
    angular.module('SensefyDirectives', []).directive('sensefyAutocomplete', [
        '$timeout', function ($timeout) {
            return {
                restrict: 'EA',
                templateUrl: 'views/directive/sensefy-autocomplete/sensefy-autocomplete.html',
                replace: true,
                scope: {
                    queryTerm: '=',
                    queryFunction: '&'
                },
                controller: [
                    '$scope',
                    'SemanticSearchService',
                    'SmartAutocompleteService',
                    function ($scope, SemanticSearchService, SmartAutocompleteService) {
                        var phase1, phase2, phase3;
                        $scope.suggestions = {};
                        $scope.showSuggestions = false;
                        $scope.selectedEntityTypeAttribute = null;
                        $scope.selectedEntityTypeAttributeValue = null;
                        $scope.selectedEntity = null;
                        $scope.selectedEntityType = null;
                        $scope.autocompletePhase = 'phase1';
                        $scope.selectedTitleIndex = -1;
                        $scope.originalAutocompleteQueryTerm = '';
                        $scope.autocomplete = function ($event) {
                            if ($event == null) {
                                $event = null;
                            }
                            if ($event !== null) {
                                $event.stopPropagation();
                            }
                            if (($event != null ? $event.keyCode : void 0) === 13) {
                                if ($scope.selectedTitleIndex >= 0) {
                                    $scope.titleSelected($scope.suggestions.titles[$scope.selectedTitleIndex]);
                                } else {
                                    $scope.queryFunction();
                                }
                                if ($scope.queryTerm.length !== 0) {
                                    angular.element('header').removeClass('ggl');
                                }
                                return;
                            }
                            if (($event != null ? $event.keyCode : void 0) !== 40 && ($event != null ? $event.keyCode : void 0) !== 38) {
                                switch ($scope.autocompletePhase) {
                                    case 'phase1':
                                        phase1();
                                        break;
                                    case 'phase2':
                                        phase2();
                                        break;
                                    case 'phase3':
                                        phase3();
                                        break;
                                    default:
                                        return;
                                }
                            }
                            return $scope.queryTerm = $scope.queryTerm;
                        };
                        phase1 = function () {
                            if ($scope.queryTerm.length === 0) {
                                return 0;
                            }
                            $scope.originalAutocompleteQueryTerm = $scope.queryTerm;
                            return SmartAutocompleteService.phase1($scope.queryTerm, $scope.$parent.user.token).then(function (response) {
                                if (response.data.header.status !== 200) {
                                    return;
                                }
                                $scope.selectedTitleIndex = -1;
                                if (response.data.responseContent.entities) {
                                    $scope.suggestions['entities'] = response.data.responseContent.entities;
                                }
                                if (response.data.responseContent.entityTypes) {
                                    $scope.suggestions['entityTypes'] = response.data.responseContent.entityTypes;
                                }
                                if (response.data.responseContent.titles) {
                                    $scope.suggestions['titles'] = response.data.responseContent.titles;
                                }
                                if (response.data.responseContent.suggestions) {
                                    return $scope.suggestions['suggestions'] = response.data.responseContent.suggestions;
                                }
                            }, function (response) {
                                return $scope.$parent.logout();
                            });
                        };
                        phase2 = function () {
                            return SmartAutocompleteService.phase2($scope.selectedEntityType.hierarchy.join(','), $scope.queryTerm, $scope.$parent.user.token).then(function (response) {
                                if (response.data.responseContent.entities) {
                                    $scope.suggestions['entities'] = response.data.responseContent.entities;
                                }
                                if (response.data.responseContent.entityTypes) {
                                    $scope.suggestions['entityTypes'] = response.data.responseContent.entityTypes;
                                }
                                if (response.data.responseContent.suggestions) {
                                    return $scope.suggestions['suggestions'] = response.data.responseContent.suggestions;
                                }
                            }, function (response) {
                                return $scope.$parent.logout();
                            });
                        };
                        phase3 = function () {
                            return SmartAutocompleteService.phase3($scope.selectedEntityType.id, $scope.selectedEntityTypeAttribute, $scope.queryTerm, $scope.$parent.user.token).then(function (response) {
                                if (response.data.responseContent.entities) {
                                    $scope.suggestions['entities'] = response.data.responseContent.entities;
                                }
                                if (response.data.responseContent.entityTypes) {
                                    $scope.suggestions['entityTypes'] = response.data.responseContent.entityTypes;
                                }
                                if (response.data.responseContent.suggestions) {
                                    return $scope.suggestions['suggestions'] = response.data.responseContent.suggestions;
                                }
                            }, function (response) {
                                return $scope.$parent.logout();
                            });
                        };
                        $scope.entitySelected = function (entity) {
                            $scope.selectedEntity = entity;
                            $scope.queryTerm = entity.label;
                            return $scope.$emit('entitySelected', entity);
                        };
                        $scope.entityTypeSelected = function (entityType) {
                            $scope.selectedEntityType = entityType;
                            $scope.selectedEntity = null;
                            $scope.suggestions = {};
                            $scope.queryTerm = '';
                            $scope.autocompletePhase = 'phase2';
                            $scope.$emit('entityTypeSelected', entityType);
                            return $scope.autocomplete();
                        };
                        $scope.entityTypeAttributeSelected = function (attribute) {
                            $scope.selectedEntityTypeAttribute = attribute;
                            $scope.suggestions = {};
                            $scope.autocompletePhase = 'phase3';
                            $scope.queryTerm = '';
                            $scope.$emit('entityTypeAttributeSelected', attribute);
                            return $scope.autocomplete();
                        };
                        $scope.entityTypeAttributeValueSelected = function (value) {
                            $scope.selectedEntityTypeAttributeValue = value;
                            $scope.suggestions = {};
                            $scope.queryTerm = '';
                            $scope.selectedEntity = null;
                            $scope.autocompletePhase = '';
                            return $scope.$emit('entityTypeAttributeValueSelected', $scope.selectedEntityTypeAttribute, $scope.selectedEntityTypeAttributeValue);
                        };
                        $scope.titleSelected = function (title) {
                            $scope.queryTerm = title.document_suggestion;
                            return $scope.$emit('titleSelected', title);
                        };
                        $scope.suggestionSelected = function (suggestion) {
                            $scope.queryTerm = suggestion;
                            return $scope.$emit('suggestionSelected', suggestion);
                        };
                        $scope.cleanSuggestions = function () {
                            return $scope.suggestions = {};
                        };
                        $scope.cleanEntityType = function () {
                            $scope.selectedEntityType = null;
                            $scope.selectedEntityTypeAttribute = null;
                            $scope.selectedEntityTypeAttributeValue = null;
                            $scope.queryTerm = "";
                            $scope.autocompletePhase = "phase1";
                            return $scope.$emit("entityTypeCleaned");
                        };
                        $scope.cleanEntityTypeAttribute = function () {
                            $scope.selectedEntityTypeAttribute = null;
                            $scope.selectedEntityTypeAttributeValue = null;
                            $scope.queryTerm = '';
                            $scope.autocompletePhase = 'phase2';
                            return $scope.$emit('entityTypeAttributeCleaned');
                        };
                        return $scope.cleanEntityTypeAttributeValue = function () {
                            $scope.selectedEntityTypeAttributeValue = null;
                            $scope.queryTerm = '';
                            $scope.autocompletePhase = 'phase3';
                            return $scope.$emit('entityTypeAttributeValueCleaned');
                        };
                    }
                ],
                link: function (scope, element, attrs) {
                    var fontSize, getWidthFilters, input, originalPaddingLeft, placeholder, span, suggestionsWrapper;
                    input = element.find('input');
                    span = element.find('div.ser-btn');
                    originalPaddingLeft = input.css('paddingLeft');
                    fontSize = input.css('fontSize');
                    fontSize = parseInt(fontSize.substring(0, fontSize.length - 2));
                    placeholder = input.attr('placeholder');
                    suggestionsWrapper = element.find('.suggestions-wrapper');
                    $timeout(function () {
                        return input.focus();
                    }, 0);
                    input.on('focus', function () {
                        if (scope.autocompletePhase !== '') {
                            return suggestionsWrapper.fadeIn();
                        }
                    });
                    input.on('blur', function () {
                        scope.showSuggestions = false;
                        if (scope.selectedTitleIndex >= 0) {
                            scope.suggestions.titles[scope.selectedTitleIndex].selected = false;
                        }
                        scope.selectedTitleIndex = -1;
                        return suggestionsWrapper.fadeOut();
                    });
                    input.on('keyup', function (event) {
                        var maxTitles, valid;
                        if ((event != null ? event.keyCode : void 0) === 27) {
                            input.trigger('blur');
                            return;
                        }
                        if ((event != null ? event.keyCode : void 0) === 13) {
                            scope.showSuggestions = false;
                            suggestionsWrapper.fadeOut();
                            angular.element('body').removeClass('ggl');
                        }
                        if (event.keyCode === 40 || event.keyCode === 38) {
                            valid = scope.suggestions.titles !== null && scope.suggestions.titles !== void 0 && scope.suggestions.titles.length > 0;
                            if (!valid) {
                                return;
                            }
                            maxTitles = scope.suggestions.titles.length - 1;
                            if (scope.selectedTitleIndex >= 0) {
                                scope.suggestions.titles[scope.selectedTitleIndex].selected = false;
                            }
                            if ((event != null ? event.keyCode : void 0) === 40) {
                                scope.selectedTitleIndex = scope.selectedTitleIndex < maxTitles ? scope.selectedTitleIndex + 1 : -1;
                            } else if ((event != null ? event.keyCode : void 0) === 38) {
                                scope.selectedTitleIndex = scope.selectedTitleIndex > -1 ? scope.selectedTitleIndex - 1 : scope.selectedTitleIndex = maxTitles;
                            }
                            if (scope.selectedTitleIndex >= 0) {
                                scope.suggestions.titles[scope.selectedTitleIndex].selected = true;
                                scope.queryTerm = scope.suggestions.titles[scope.selectedTitleIndex].document_suggestion;
                            }
                            if (scope.selectedTitleIndex === -1) {
                                scope.queryTerm = scope.originalAutocompleteQueryTerm;
                            }
                            return scope.$digest();
                        }
                    });
                    span.on('keyup', function (event) {
                        if ((event != null ? event.keyCode : void 0) === 13) {
                            scope.showSuggestions = false;
                            //if (scope.queryTerm.length !== 0) {
                            angular.element('body').removeClass('ggl');
                            //}
                            return suggestionsWrapper.fadeOut();
                        }
                    });
                    span.on('click', function () {
                        //if (scope.queryTerm.length !== 0) {
                        angular.element('body').removeClass('ggl');
                        //}
                    });
                    scope.$on('titleSelected', function () {
                        return angular.element('body').removeClass('ggl');
                    });
                    scope.$on('suggestionSelected', function () {
                        return angular.element('body').removeClass('ggl');
                    });
                    scope.$on('entityTypeSelected', function (event, entityType) {
                        var filtersWidth;
                        suggestionsWrapper.fadeIn();
                        filtersWidth = getWidthFilters();
                        return scope.style = "padding-left: " + filtersWidth + "px";
                    });
                    scope.$on('entityTypeAttributeSelected', function (event) {
                        var filtersWidth;
                        filtersWidth = getWidthFilters();
                        return scope.style = "padding-left: " + filtersWidth + "px";
                    });
                    getWidthFilters = function () {
                        var accum;
                        accum = 0;
                        if (scope.selectedEntityType !== null) {
                            accum += scope.selectedEntityType.type[0].length;
                        }
                        if (scope.selectedEntityTypeAttribute !== null) {
                            accum += scope.selectedEntityTypeAttribute.length;
                        }
                        if (scope.selectedEntityTypeAttributeValue !== null) {
                            accum += scope.selectedEntityTypeAttributeValue.length;
                        }
                        return accum = (accum * fontSize) / 2;
                    };
                    scope.$on('entityTypeAttributeValueSelected', function (event, attribute, value) {
                        var filtersWidth;
                        suggestionsWrapper.fadeOut();
                        filtersWidth = getWidthFilters();
                        input.prop('readonly', true);
                        input.attr('placeholder', "");
                        return scope.style = "padding-left: " + filtersWidth + "px";
                    });
                    scope.$on('entityTypeCleaned', function () {
                        scope.cleanSuggestions();
                        scope.style = "padding-left: " + originalPaddingLeft + "px";
                        input.prop('readonly', false);
                        return input.attr('placeholder', placeholder);
                    });
                    scope.$on('entityTypeAttributeCleaned', function () {
                        var filtersWidth;
                        scope.autocomplete();
                        suggestionsWrapper.fadeIn();
                        filtersWidth = getWidthFilters();
                        scope.style = "padding-left: " + filtersWidth + "px";
                        input.prop('readonly', false);
                        return input.attr('placeholder', placeholder);
                    });
                    return scope.$on('entityTypeAttributeValueCleaned', function () {
                        var filtersWidth;
                        scope.autocomplete();
                        suggestionsWrapper.fadeIn();
                        filtersWidth = getWidthFilters();
                        scope.style = "padding-left: " + filtersWidth + "px";
                        input.prop('readonly', false);
                        return input.attr('placeholder', placeholder);
                    });
                }
            };
        }
    ]).directive("imagedrop", [
        '$parse', function ($parse) {
            return {
                restrict: "EA",
                link: function (scope, element, attrs) {
                    var loadFile, onDragEnd, onDragOver, onImageDrop;
                    onImageDrop = $parse(attrs.onImageDrop);
                    onDragOver = function (e) {
                        e.preventDefault();
                        return $(element).addClass("drag-over");
                    };
                    onDragEnd = function (e) {
                        e.preventDefault();
                        return $(element).removeClass("drag-over");
                    };
                    loadFile = function (file) {
                        scope.uploadedFile = file;
                        return scope.$apply(onImageDrop(scope));
                    };
                    $(element).bind("dragover", onDragOver);
                    element.bind("dragleave", onDragEnd);
                    return element.bind("drop", function (e) {
                        onDragEnd(e);
                        return loadFile(e.originalEvent.dataTransfer.files[0]);
                    });
                }
            };
        }
    ]).directive("datasourceTab", [
        '$parse', function ($parse) {
            return {
                restrict: "EA",
                link: function (scope, element, attrs, $index) {
                    var colorArray, colorArrayLight, colorArrayLightRelation, cssClassName, style;
                    colorArray = ['rgba(151,187,205,1)', 'rgba(70,191,189,1)', 'rgba(253,180,92,1)', 'rgba(247,70,74,1)', 'rgba(148,159,177,1)', 'rgba(120,209,35,1)'];
                    colorArrayLight = ['rgba(151,187,205,.3)', 'rgba(70,191,189,.3)', 'rgba(253,180,92,.3)', 'rgba(247,70,74,.3)', 'rgba(148,159,177,.3)', 'rgba(120,209,35,.3)'];
                    colorArrayLightRelation = ['rgba(151,187,205,.5)', 'rgba(70,191,189,.5)', 'rgba(253,180,92,.5)', 'rgba(247,70,74,.5)', 'rgba(148,159,177,.5)', 'rgba(120,209,35,.5)'];
                    cssClassName = attrs.dynamicClass.replace(RegExp(' ', 'g'), '');
                    element.attr('class', cssClassName);
                    style = document.createElement('style');
                    style.type = 'text/css';
                    style.innerHTML += '.data-sources a.' + cssClassName + '{ background-color:' + colorArrayLight[scope.$index] + '; border-left:3px solid ' + colorArray[scope.$index] + ';  }';
                    style.innerHTML += '.data-sources a.active.' + cssClassName + '{ background-color:' + colorArray[scope.$index] + '; border-left:3px solid ' + colorArray[scope.$index] + '; }';
                    style.innerHTML += '#se-results .document.' + cssClassName + '{ border-left:2px solid ' + colorArrayLightRelation[scope.$index] + '; }';
                    style.innerHTML += '#se-results .document.' + cssClassName + ':hover { border-left:2px solid ' + colorArray[scope.$index] + '; }';
                    style.innerHTML += '#sensefy .' + cssClassName + ' .channel-d {color: ' + colorArray[scope.$index] + ';}';
                    style.innerHTML += '.' + cssClassName + ' i.file, .' + cssClassName + ' .hover-result {color: ' + colorArray[scope.$index] + ';}';
                    /*
                     element.bind("dragleave", onDragEnd)
                     element.bind("drop", (e) ->
                     onDragEnd(e)
                     loadFile e.originalEvent.dataTransfer.files[0]
                     )
                     */

                    return document.getElementsByTagName('head')[0].appendChild(style);
                }
            };
        }
    ]).directive("resultSet", [
        '$parse', function ($parse) {
            return {
                restrict: "EA",
                link: function (scope, element, attrs, $index) {
                    var cssRelationClassName;
                    cssRelationClassName = attrs.datasourceRelation.replace(RegExp(' ', 'g'), '');
                    return element.attr('class', 'link item  document  ' + cssRelationClassName);
                    /*
                     element.bind("dragleave", onDragEnd)
                     element.bind("drop", (e) ->
                     onDragEnd(e)
                     loadFile e.originalEvent.dataTransfer.files[0]
                     )
                     */

                }
            };
        }
    ]);
}.call(this));