/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   search directive is functioned.
 */

(function () {
    angular.module('SensefyDirectives', [])
        .directive('sensefyAutocomplete', ['$timeout', 'DEBUGmode', 'CONSOLEmode', 'isJSON',
            function ($timeout, DEBUGmode, CONSOLEmode, isJSON, SensefySearchLogin) {
                return {
                    restrict: 'EA',
                    templateUrl: 'views/directive/sensefy-autocomplete/sensefy-autocomplete.html',
                    replace: true,
                    scope: {
                        queryTerm: "=",
                        queryFunction: "&"
                    },
                    controller: function ($scope, SemanticSearchService, SmartAutocompleteService) {
                        var phase1, phase2, phase3;
                        $scope.suggestions = {};
                        $scope.showSuggestions = false;
                        $scope.selectedEntityTypeAttribute = null;
                        $scope.selectedEntityTypeAttributeValue = null;
                        $scope.selectedEntity = null;
                        $scope.selectedEntityType = null;
                        $scope.autocompletePhase = "phase1";
                        $scope.selectedTitleIndex = -1;
                        $scope.originalAutocompleteQueryTerm = "";
                        $scope.autocomplete = function ($event) {
                            if ($event == null) {
                                $event = null;
                            }
                            if ($event !== null) {
                                $event.stopPropagation();
                            }
                            if (($event != null ? $event.keyCode : void 0) === 13) {

                                if(selectedQueryObj.type === 'title'){
                                    $scope.titleSelected(selectedQueryObj.dataSet);
                                }
                                else if(selectedQueryObj.type === 'suggestions'){
                                    $scope.suggestionSelected(selectedQueryObj.dataSet);
                                }
                                else if(selectedQueryObj.type === 'entity'){
                                    $scope.entitySelected(selectedQueryObj.dataSet);
                                }else {
                                    $scope.queryFunction();
                                }
                                if ($scope.queryTerm.length !== 0) {
                                    angular.element('header').removeClass('ggl');
                                }
                                /*if ($scope.selectedTitleIndex >= 0) {
                                    $scope.titleSelected($scope.suggestions.titles[$scope.selectedTitleIndex]);
                                } else {
                                    $scope.queryFunction();
                                }
                                if ($scope.queryTerm.length !== 0) {
                                    angular.element('header').removeClass('ggl');
                                }
                                return;*/
                            }
                            if (($event != null ? $event.keyCode : void 0) !== 40 && ($event != null ? $event.keyCode : void 0) !== 38) {
                                switch ($scope.autocompletePhase) {
                                    case "phase1":
                                        phase1();
                                        break;
                                    case "phase2":
                                        phase2();
                                        break;
                                    case "phase3":
                                        phase3();
                                        break;
                                    default:
                                        return;
                                }
                            }
                            var regex = /(<([^>]+)>)/ig,
                                body = $scope.queryTerm,
                                clnresult = body.replace(regex, "");
                            $scope.queryTerm = clnresult;
                        };
                        phase1 = function () {
                            if ($scope.queryTerm.length === 0) {
                                return 0;
                            }
                            if (CONSOLEmode) {
                                console.log('Phase 1 is fired');
                            }

                            $scope.originalAutocompleteQueryTerm = $scope.queryTerm;
                            return SmartAutocompleteService.phase1($scope.queryTerm).then(function (response) {
                                if (response.data.header === undefined) {
                                    if (CONSOLEmode) {
                                        console.log('Phase 1 is fired - response.data.header: undefined')
                                    }
                                    document.location.href = SensefySearchLogin;
                                    if (DEBUGmode) {
                                        debugger;
                                    }
                                    return;
                                }

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
                                $scope.$parent.logout();
                            });
                        };
                        phase2 = function () {
                            return SmartAutocompleteService.phase2($scope.selectedEntityType.hierarchy.join(","), $scope.queryTerm).then(function (response) {
                                if (CONSOLEmode) {
                                    console.log('phase2 is fired')
                                }
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
                                $scope.$parent.logout();
                            });
                        };
                        phase3 = function () {
                            return SmartAutocompleteService.phase3($scope.selectedEntityType.id, $scope.selectedEntityTypeAttribute, $scope.queryTerm).then(function (response) {
                                if (CONSOLEmode) {
                                    console.log('phase3 is fired')
                                }
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
                                $scope.$parent.logout();
                            });
                        };
                        $scope.entitySelected = function (entity) {
                            if(CONSOLEmode){
                                console.log ('$scope.entitySelected at directive 140');
                                var a = '';
                                if(isJSON) {
                                    a = JSON.stringify(entity);
                                }
                                else{
                                    a = entity;
                                }
                                console.log('$scope.entitySelected -> directive @param - entity : '+ a);
                            }
                            $scope.selectedEntity = entity;
                            $scope.queryTerm = entity.label;
                            angular.element('body').removeClass('ggl');
                            $scope.$emit('entitySelected', entity);
                        };
                        $scope.entityTypeSelected = function (entityType) {
                            $scope.selectedEntityType = entityType;
                            $scope.selectedEntity = null;
                            $scope.suggestions = {};
                            $scope.queryTerm = "";
                            $scope.autocompletePhase = "phase2";
                            $scope.$emit("entityTypeSelected", entityType);
                            $scope.autocomplete();
                        };
                        $scope.entityTypeAttributeSelected = function (attribute) {
                            $scope.selectedEntityTypeAttribute = attribute;
                            $scope.suggestions = {};
                            $scope.autocompletePhase = "phase3";
                            $scope.queryTerm = "";
                            $scope.$emit("entityTypeAttributeSelected", attribute);
                            $scope.autocomplete();
                        };
                        $scope.entityTypeAttributeValueSelected = function (value) {
                            $scope.selectedEntityTypeAttributeValue = value;
                            $scope.suggestions = {};
                            $scope.queryTerm = "";
                            $scope.selectedEntity = null;
                            $scope.autocompletePhase = "";
                            $scope.$emit("entityTypeAttributeValueSelected", $scope.selectedEntityTypeAttribute, $scope.selectedEntityTypeAttributeValue);
                        };
                        $scope.titleSelected = function (title) {
                            $scope.queryTerm = title.document_suggestion;
                            $scope.$emit("titleSelected", title);
                        };
                        $scope.suggestionSelected = function (suggestion) {
                            $scope.queryTerm = suggestion;
                            $scope.$emit("suggestionSelected", suggestion);
                        };
                        $scope.cleanSuggestions = function () {
                            $scope.suggestions = {};
                        };
                        $scope.cleanEntityType = function () {
                            $scope.selectedEntityType = null;
                            $scope.selectedEntityTypeAttribute = null;
                            $scope.selectedEntityTypeAttributeValue = null;
                            $scope.queryTerm = "";
                            $scope.autocompletePhase = "phase1";
                            $scope.$emit("entityTypeCleaned");
                        };
                        $scope.cleanEntityTypeAttribute = function () {
                            $scope.selectedEntityTypeAttribute = null;
                            $scope.selectedEntityTypeAttributeValue = null;
                            $scope.queryTerm = "";
                            $scope.autocompletePhase = "phase2";
                            $scope.$emit("entityTypeAttributeCleaned");
                        };
                        $scope.cleanEntityTypeAttributeValue = function () {
                            $scope.selectedEntityTypeAttributeValue = null;
                            $scope.queryTerm = "";
                            $scope.autocompletePhase = "phase3";
                            $scope.$emit("entityTypeAttributeValueCleaned");
                        };
                    },
                    link: function (scope, element, attrs) {
                        var fontSize, getWidthFilters, input, originalPaddingLeft, placeholder, btn, suggestionsWrapper;
                        input = element.find("input");
                        btn = element.find("span.search-btn");
                        originalPaddingLeft = input.css('paddingLeft');
                        fontSize = input.css('fontSize');
                        fontSize = parseInt(fontSize.substring(0, fontSize.length - 2));
                        placeholder = input.attr('placeholder');
                        suggestionsWrapper = element.find(".suggestions-wrapper");
                        $timeout(function () {
                            input.focus();
                        }, 0);
                        input.on('focus', function () {
                            if (scope.autocompletePhase !== "") {
                                suggestionsWrapper.fadeIn();
                            }
                        });
                        input.on('blur', function () {
                            scope.showSuggestions = false;
                            if (scope.selectedTitleIndex >= 0) {
                                scope.suggestions.titles[scope.selectedTitleIndex].selected = false;
                            }
                            scope.selectedTitleIndex = -1;
                            suggestionsWrapper.fadeOut();
                        });
                        input.on('keyup', function (event) {
                            var maxTitles, validTitle, validSuggestion;
                            if ((event != null ? event.keyCode : void 0) === 27) {
                                input.trigger('blur');
                                return;
                            }
                            if ((event != null ? event.keyCode : void 0) === 13) {
                                scope.showSuggestions = false;
                                suggestionsWrapper.fadeOut();
                                angular.element('body').removeClass('ggl');
                            }
                            if (event.keyCode === 40 || event.keyCode === 38 || event.keyCode === 9) {
                                if (CONSOLEmode) {
                                    console.log('Started pressing up or down arrow keys.');
                                }

                                $('.selection-container').keySelection();
                                $('.selection-container').on('keySelection.selection', function (e) {
                                    var $items = $('.pe-sug-item');
                                    var index = $items.index($(e.selectedElement));
                                    $('.selected-element').empty().append(index + 1);
                                });
                                $('.selection-container').on('keySelection.keyHover', function (e) {
                                    var $items = $('.pe-sug-item');
                                    var index = $items.index($(e.keyHoverElement));
                                    $('.key-hover-element').empty().append(index + 1);
                                    scope.queryTerm = '';
                                });

                                if(selectedQueryObj !== undefined){
                                    scope.queryTerm = selectedQueryObj.q;
                                    input.val(selectedQueryObj.q);
                                }

                                if(CONSOLEmode){
                                    console.log('selectedQueryObj -> '+JSON.stringify(selectedQueryObj));
                                }


                                if (event.keyCode === 13 && isEnterHit){

                                    if(selectedQueryObj.type === 'title'){
                                        scope.titleSelected(selectedQueryObj.dataSet)
                                    }
                                    else if(selectedQueryObj.type === 'suggestions'){
                                        scope.suggestionSelected(selectedQueryObj.dataSet)
                                    }
                                    else if(selectedQueryObj.type === 'entity'){
                                        scope.entitySelected(selectedQueryObj.dataSet)
                                    }else {
                                        scope.queryFunction();
                                    }
                                    if (scope.queryTerm.length !== 0) {
                                        angular.element('body').removeClass('ggl');
                                    }
                                }


                                /*$(".button.stop").on("click",function(){
                                    $(".selection-container").keySelection("stop");
                                    return false;
                                });
                                $(".button.start").on("click",function(){
                                    $(".selection-container").keySelection("start");
                                    return false;
                                });
                                $(".button.select").on("click",function(){
                                    $(".selection-container").keySelection("select");
                                    return false;
                                })*/;

                                /*validTitle = scope.suggestions.titles !== null && scope.suggestions.titles !== void 0 && scope.suggestions.titles.length > 0;

                                validSuggestion = scope.suggestions.suggestions;

                                if (!validTitle && !validSuggestion) {
                                    //return;
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

                                    var regex = /(<([^>]+)>)/ig,
                                        body = scope.suggestions.titles[scope.selectedTitleIndex].document_suggestion,
                                        clnresult = body.replace(regex, "");
                                    scope.queryTerm = clnresult;
                                    if (DEBUGmode) {
                                        console.log('scope.queryTerm scope.selectedTitleIndex >=0, this appears on search box ' + scope.queryTerm);
                                    }
                                }
                                if (scope.selectedTitleIndex === -1) {

                                    var regex = /(<([^>]+)>)/ig,
                                        body = scope.originalAutocompleteQueryTerm,
                                        clnresult = body.replace(regex, "");
                                    scope.queryTerm = clnresult;
                                    if (DEBUGmode) {
                                        console.log('scope.queryTerm scope.selectedTitleIndex === -1, this appears on search box ' + scope.queryTerm);
                                    }
                                }*/
                                scope.$digest();
                            }
                        });
                        btn.on('keyup', function (event) {
                            if ((event != null ? event.keyCode : void 0) === 13) {
                                scope.showSuggestions = false;
                                if (scope.queryTerm.length !== 0) {
                                    angular.element('body').removeClass('ggl');
                                }
                                suggestionsWrapper.fadeOut();
                            }
                        });
                        btn.on('click', function () {
                            if (scope.queryTerm.length !== 0) {
                                angular.element('body').removeClass('ggl');
                            }
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
                            scope.style = "padding-left: " + filtersWidth + "px";
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
        ]).directive("imagedrop", ['$parse',
            function ($parse) {
                return {
                    restrict: "EA",
                    link: function (scope, element, attrs) {
                        var loadFile, onDragEnd, onDragOver, onImageDrop;
                        onImageDrop = $parse(attrs.onImageDrop);
                        onDragOver = function (e) {
                            e.preventDefault();
                            $(element).addClass("drag-over");
                        };
                        onDragEnd = function (e) {
                            e.preventDefault();
                            $(element).removeClass("drag-over");
                        };
                        loadFile = function (file) {
                            scope.uploadedFile = file;
                            scope.$apply(onImageDrop(scope));
                        };
                        $(element).bind("dragover", onDragOver);
                        element.bind("dragleave", onDragEnd);
                        return element.bind("drop", function (e) {
                            onDragEnd(e);
                            loadFile(e.originalEvent.dataTransfer.files[0]);
                        });
                    }
                };
            }
        ]).directive("datasourceTab", ['$parse',
            function ($parse) {
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
                        style.innerHTML += '#sensefy .sources a.' + cssClassName + '{ background-color:' + colorArrayLight[scope.$index] + '; border-left:3px solid ' + colorArray[scope.$index] + '!important; }';
                        style.innerHTML += '#sensefy .sources a.active.' + cssClassName + '{ background-color:' + colorArray[scope.$index] + '; border-left:3px solid ' + colorArray[scope.$index] + '!important; }';
                        style.innerHTML += '.document.' + cssClassName + '{ border-left:2px solid ' + colorArrayLightRelation[scope.$index] + '!important;}';
                        style.innerHTML += '.document.' + cssClassName + ':hover { border-left:2px solid ' + colorArray[scope.$index] + '!important; }';
                        style.innerHTML += '.document.' + cssClassName + ':hover .hover-result{ color: ' + colorArray[scope.$index] + '; }';

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
        ]).directive("resultSet", ['$parse',
            function ($parse) {
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
        ]).directive("entityIsAvailable", ['$parse', 'DEBUGmode', 'CONSOLEmode', 'isJSON',
            function ($parse, DEBUGmode, CONSOLEmode, isJSON) {
                return {
                    restrict: "EA",
                    controller: function ($scope) {
                    },
                    link: function (scope, element, attrs) {

                        attrs.$observe('entityDataSet', function (val) {

                            var b = setTimeout(function () {
                                $('.et-row').removeClass('nodisplayrow');
                                $('.et-row').each( function(i,e) {
                                    var emptyCount = 0;

                                    var childrens = $(this).children('.et-value-wrap').children('.et-value:visible').length;

                                    if(childrens === 0) {
                                        $(this).addClass('nodisplayrow');
                                    }
                                });
                                clearTimeout(b);
                            }, 250);



                            if (CONSOLEmode) {
                                var a = setTimeout(function () {
                                    //console.log('entityData with json - '+val);
                                    clearTimeout(a);
                                }, 250);
                            }
                        });
                    }
                };
            }
        ]).directive('imageSwitch', ['$timeout',
            function($timeout) {
                return {
                    restrict: 'EA',
                    link: function(scope, element, attrs) {
                        $timeout(function() {
                            var imageUrl = '';
                            angular.element('.pe-check-image').addClass('hide-thumb');

                            scope.$watch(attrs.imageSwitch, function(value) {
                                imageUrl = value;
                                setImage();
                            });

                            function setImage() {
                                var image = new Image();
                                image.onerror = function () {
                                    angular.element('.pe-check-image').addClass('hide-thumb');
                                };
                                image.onload = function () {
                                    angular.element('.pe-check-image').removeClass('hide-thumb');
                                };
                                image.src = imageUrl;
                            }
                        });
                    }
                };
            }
        ]).directive( 'treeModel', ['$compile', function( $compile ) {
            return {
                restrict: 'A',
                controller: function ($scope) {

                },
                link: function ( scope, element, attrs ) {
                    //tree id
                    var treeId = attrs.treeId;

                    //tree model
                    var treeModel = attrs.treeModel;

                    //node id
                    var nodeId = attrs.nodeId || 'id';

                    //node label
                    var nodeLabel = attrs.nodeLabel || 'label';

                    //children
                    var nodeChildren = attrs.nodeChildren || 'children';

                    //tree template
                    var template =
                        '<ul>' +
                        '<li data-ng-repeat="node in ' + treeModel + '">' +
                        '<i class="collapsed" data-ng-show="node.' + nodeChildren + '.length && node.collapsed" data-ng-click="selectNodeHead(node)"></i>' +
                        '<i class="expanded" data-ng-show="node.' + nodeChildren + '.length && !node.collapsed" data-ng-click="selectNodeHead(node)"></i>' +
                        '<i class="normal" data-ng-hide="node.' + nodeChildren + '.length" data-ng-click="selectNodeLabel(node)"></i> ' +
                        '<span data-ng-class="node.selected" data-ng-click="selectNodeLabel(node)">{{node.topterms}}</span>' +
                        '<div data-ng-hide="node.collapsed" data-tree-id="' + treeId + '" data-tree-model="node.children" data-node-id="topicId" data-node-label="topterms" data-node-children="children"></div>' +
                        '</li>' +
                        '</ul>';


                    //check tree id, tree model
                    if( treeId && treeModel ) {
                        //Rendering template.
                        element.html('').append( $compile( template )( scope ) );
                    }
                }
            };
        }]);
}).call(this);