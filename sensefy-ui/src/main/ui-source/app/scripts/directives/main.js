/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   search directive is functioned.
 */

(function () {
    angular.module('SensefyDirectives', [])
        .directive('sensefyAutocomplete', ['$timeout', 'DEBUGmode',
            function ($timeout, DEBUGmode) {
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
                        return $scope.queryTerm = clnresult;
                    };
                    phase1 = function () {
                        if ($scope.queryTerm.length === 0) {
                            return 0;
                        }
                        if(DEBUGmode) {
                            console.log('phase1 is fired')
                        }
                        $scope.originalAutocompleteQueryTerm = $scope.queryTerm;
                        return SmartAutocompleteService.phase1($scope.queryTerm).then(function (response) {
                            if (response.data.header === undefined) {
                                if(DEBUGmode) {
                                    console.log('phase1 is fired - response.data.header: undefined')
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
                            return $scope.$parent.logout();
                        });
                    };
                    phase2 = function () {
                        return SmartAutocompleteService.phase2($scope.selectedEntityType.hierarchy.join(","), $scope.queryTerm).then(function (response) {
                            if(DEBUGmode) {
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
                            return $scope.$parent.logout();
                        });
                    };
                    phase3 = function () {
                        return SmartAutocompleteService.phase3($scope.selectedEntityType.id, $scope.selectedEntityTypeAttribute, $scope.queryTerm).then(function (response) {
                            if(DEBUGmode) {
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
                        $scope.queryTerm = "";
                        $scope.autocompletePhase = "phase2";
                        $scope.$emit("entityTypeSelected", entityType);
                        return $scope.autocomplete();
                    };
                    $scope.entityTypeAttributeSelected = function (attribute) {
                        $scope.selectedEntityTypeAttribute = attribute;
                        $scope.suggestions = {};
                        $scope.autocompletePhase = "phase3";
                        $scope.queryTerm = "";
                        $scope.$emit("entityTypeAttributeSelected", attribute);
                        return $scope.autocomplete();
                    };
                    $scope.entityTypeAttributeValueSelected = function (value) {
                        $scope.selectedEntityTypeAttributeValue = value;
                        $scope.suggestions = {};
                        $scope.queryTerm = "";
                        $scope.selectedEntity = null;
                        $scope.autocompletePhase = "";
                        return $scope.$emit("entityTypeAttributeValueSelected", $scope.selectedEntityTypeAttribute, $scope.selectedEntityTypeAttributeValue);
                    };
                    $scope.titleSelected = function (title) {
                        $scope.queryTerm = title.document_suggestion;
                        return $scope.$emit("titleSelected", title);
                    };
                    $scope.suggestionSelected = function (suggestion) {
                        $scope.queryTerm = suggestion;
                        return $scope.$emit("suggestionSelected", suggestion);
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
                        $scope.queryTerm = "";
                        $scope.autocompletePhase = "phase2";
                        return $scope.$emit("entityTypeAttributeCleaned");
                    };
                    return $scope.cleanEntityTypeAttributeValue = function () {
                        $scope.selectedEntityTypeAttributeValue = null;
                        $scope.queryTerm = "";
                        $scope.autocompletePhase = "phase3";
                        return $scope.$emit("entityTypeAttributeValueCleaned");
                    };
                },
                link: function (scope, element, attrs) {
                    var fontSize, getWidthFilters, input, originalPaddingLeft, placeholder, span, suggestionsWrapper;
                    input = element.find("input");
                    span = element.find("span.search-btn");
                    originalPaddingLeft = input.css('paddingLeft');
                    fontSize = input.css('fontSize');
                    fontSize = parseInt(fontSize.substring(0, fontSize.length - 2));
                    placeholder = input.attr('placeholder');
                    suggestionsWrapper = element.find(".suggestions-wrapper");
                    $timeout(function () {
                        return input.focus();
                    }, 0);
                    input.on('focus', function () {
                        if (scope.autocompletePhase !== "") {
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
                            console.log('sdfdsf sdfds fds')
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

                                var regex = /(<([^>]+)>)/ig,
                                    body = scope.suggestions.titles[scope.selectedTitleIndex].document_suggestion,
                                    clnresult = body.replace(regex, "");
                                scope.queryTerm = clnresult;


                                console.log('scope.queryTerm scope.selectedTitleIndex >=0 '+scope.queryTerm)
                            }
                            if (scope.selectedTitleIndex === -1) {

                                var regex = /(<([^>]+)>)/ig,
                                    body = scope.originalAutocompleteQueryTerm,
                                    clnresult = body.replace(regex, "");
                                scope.queryTerm = clnresult;

                                console.log('scope.queryTerm scope.selectedTitleIndex === -1 '+scope.queryTerm)
                            }
                            return scope.$digest();
                        }
                    });
                    span.on('keyup', function (event) {
                        if ((event != null ? event.keyCode : void 0) === 13) {
                            scope.showSuggestions = false;
                            if (scope.queryTerm.length !== 0) {
                                angular.element('body').removeClass('ggl');
                            }
                            return suggestionsWrapper.fadeOut();
                        }
                    });
                    span.on('click', function () {
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
    ]).directive("imagedrop", ['$parse',
            function ($parse) {
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
    ]).directive('repoBrowserTree', function($window){
        return{
            restrict:'EA',
            template:"<svg width='850' height='200'></svg>",
            link: function(scope, elem, attrs){
                treeJSON = d3.json("flare.json", function(error, treeData) {

                    // Calculate total nodes, max label length
                    var totalNodes = 0;
                    var maxLabelLength = 0;
                    // variables for drag/drop
                    var selectedNode = null;
                    var draggingNode = null;
                    // panning variables
                    var panSpeed = 200;
                    var panBoundary = 20; // Within 20px from edges will pan when dragging.
                    // Misc. variables
                    var i = 0;
                    var duration = 750;
                    var root;

                    // size of the diagram
                    var viewerWidth = $(document).width();
                    var viewerHeight = $(document).height();

                    viewerWidth = viewerWidth - 250;
                    viewerHeight = viewerHeight + 150;

                    var tree = d3.layout.tree()
                        .size([viewerHeight, viewerWidth]);

                    // define a d3 diagonal projection for use by the node paths later on.
                    var diagonal = d3.svg.diagonal()
                        .projection(function(d) {
                            return [d.y, d.x];
                        });

                    // A recursive helper function for performing some setup by walking through all nodes

                    function visit(parent, visitFn, childrenFn) {
                        if (!parent) return;

                        visitFn(parent);

                        var children = childrenFn(parent);
                        if (children) {
                            var count = children.length;
                            for (var i = 0; i < count; i++) {
                                visit(children[i], visitFn, childrenFn);
                            }
                        }
                    }

                    // Call visit function to establish maxLabelLength
                    visit(treeData, function(d) {
                        totalNodes++;
                        maxLabelLength = Math.max(d.name.length, maxLabelLength);

                    }, function(d) {
                        return d.children && d.children.length > 0 ? d.children : null;
                    });


                    // sort the tree according to the node names

                    function sortTree() {
                        tree.sort(function(a, b) {
                            return b.name.toLowerCase() < a.name.toLowerCase() ? 1 : -1;
                        });
                    }
                    // Sort the tree initially incase the JSON isn't in a sorted order.
                    sortTree();

                    // TODO: Pan function, can be better implemented.

                    function pan(domNode, direction) {
                        var speed = panSpeed;
                        if (panTimer) {
                            clearTimeout(panTimer);
                            translateCoords = d3.transform(svgGroup.attr("transform"));
                            if (direction == 'left' || direction == 'right') {
                                translateX = direction == 'left' ? translateCoords.translate[0] + speed : translateCoords.translate[0] - speed;
                                translateY = translateCoords.translate[1];
                            } else if (direction == 'up' || direction == 'down') {
                                translateX = translateCoords.translate[0];
                                translateY = direction == 'up' ? translateCoords.translate[1] + speed : translateCoords.translate[1] - speed;
                            }
                            scaleX = translateCoords.scale[0];
                            scaleY = translateCoords.scale[1];
                            scale = zoomListener.scale();
                            svgGroup.transition().attr("transform", "translate(" + translateX + "," + translateY + ")scale(" + scale + ")");
                            d3.select(domNode).select('g.node').attr("transform", "translate(" + translateX + "," + translateY + ")");
                            zoomListener.scale(zoomListener.scale());
                            zoomListener.translate([translateX, translateY]);
                            panTimer = setTimeout(function() {
                                pan(domNode, speed, direction);
                            }, 50);
                        }
                    }

                    // Define the zoom function for the zoomable tree

                    function zoom() {
                        svgGroup.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
                    }


                    // define the zoomListener which calls the zoom function on the "zoom" event constrained within the scaleExtents
                    var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);

                    function initiateDrag(d, domNode) {
                        draggingNode = d;
                        d3.select(domNode).select('.ghostCircle').attr('pointer-events', 'none');
                        d3.selectAll('.ghostCircle').attr('class', 'ghostCircle show');
                        d3.select(domNode).attr('class', 'node activeDrag');

                        svgGroup.selectAll("g.node").sort(function(a, b) { // select the parent and sort the path's
                            if (a.id != draggingNode.id) return 1; // a is not the hovered element, send "a" to the back
                            else return -1; // a is the hovered element, bring "a" to the front
                        });
                        // if nodes has children, remove the links and nodes
                        if (nodes.length > 1) {
                            // remove link paths
                            links = tree.links(nodes);
                            nodePaths = svgGroup.selectAll("path.link")
                                .data(links, function(d) {
                                    return d.target.id;
                                }).remove();
                            // remove child nodes
                            nodesExit = svgGroup.selectAll("g.node")
                                .data(nodes, function(d) {
                                    return d.id;
                                }).filter(function(d, i) {
                                    if (d.id == draggingNode.id) {
                                        return false;
                                    }
                                    return true;
                                }).remove();
                        }

                        // remove parent link
                        parentLink = tree.links(tree.nodes(draggingNode.parent));
                        svgGroup.selectAll('path.link').filter(function(d, i) {
                            if (d.target.id == draggingNode.id) {
                                return true;
                            }
                            return false;
                        }).remove();

                        dragStarted = null;
                    }

                    // define the baseSvg, attaching a class for styling and the zoomListener
                    var baseSvg = d3.select("#tree-container").append("svg")
                        .attr("width", viewerWidth)
                        .attr("height", viewerHeight)
                        .attr("class", "overlay")
                        .call(zoomListener);


                    // Define the drag listeners for drag/drop behaviour of nodes.
                    dragListener = d3.behavior.drag()
                        .on("dragstart", function(d) {
                            /*if (d == root) {
                                return;
                            }
                            dragStarted = true;
                            nodes = tree.nodes(d);
                            d3.event.sourceEvent.stopPropagation();*/
                            // it's important that we suppress the mouseover event on the node being dragged. Otherwise it will absorb the mouseover event and the underlying node will not detect it d3.select(this).attr('pointer-events', 'none');
                        })
                        .on("drag", function(d) {
                            /*if (d == root) {
                                return;
                            }
                            if (dragStarted) {
                                domNode = this;
                                initiateDrag(d, domNode);
                            }

                            // get coords of mouseEvent relative to svg container to allow for panning
                            relCoords = d3.mouse($('svg').get(0));
                            if (relCoords[0] < panBoundary) {
                                panTimer = true;
                                pan(this, 'left');
                            } else if (relCoords[0] > ($('svg').width() - panBoundary)) {

                                panTimer = true;
                                pan(this, 'right');
                            } else if (relCoords[1] < panBoundary) {
                                panTimer = true;
                                pan(this, 'up');
                            } else if (relCoords[1] > ($('svg').height() - panBoundary)) {
                                panTimer = true;
                                pan(this, 'down');
                            } else {
                                try {
                                    clearTimeout(panTimer);
                                } catch (e) {

                                }
                            }

                            d.x0 += d3.event.dy;
                            d.y0 += d3.event.dx;
                            var node = d3.select(this);
                            node.attr("transform", "translate(" + d.y0 + "," + d.x0 + ")");
                            updateTempConnector();*/
                        })
                        .on("dragend", function(d) {
                            /*if (d == root) {
                                return;
                            }
                            domNode = this;
                            if (selectedNode) {
                                // now remove the element from the parent, and insert it into the new elements children
                                var index = draggingNode.parent.children.indexOf(draggingNode);
                                if (index > -1) {
                                    draggingNode.parent.children.splice(index, 1);
                                }
                                if (typeof selectedNode.children !== 'undefined' || typeof selectedNode._children !== 'undefined') {
                                    if (typeof selectedNode.children !== 'undefined') {
                                        selectedNode.children.push(draggingNode);
                                    } else {
                                        selectedNode._children.push(draggingNode);
                                    }
                                } else {
                                    selectedNode.children = [];
                                    selectedNode.children.push(draggingNode);
                                }
                                // Make sure that the node being added to is expanded so user can see added node is correctly moved
                                expand(selectedNode);
                                sortTree();
                                endDrag();
                            } else {
                                endDrag();
                            }*/
                        });

                    function endDrag() {
                        selectedNode = null;
                        d3.selectAll('.ghostCircle').attr('class', 'ghostCircle');
                        d3.select(domNode).attr('class', 'node');
                        // now restore the mouseover event or we won't be able to drag a 2nd time
                        d3.select(domNode).select('.ghostCircle').attr('pointer-events', '');
                        updateTempConnector();
                        if (draggingNode !== null) {
                            update(root);
                            centerNode(draggingNode);
                            draggingNode = null;
                        }
                    }

                    // Helper functions for collapsing and expanding nodes.

                    function collapse(d) {
                        if (d.children) {
                            d._children = d.children;
                            d._children.forEach(collapse);
                            d.children = null;
                        }
                    }

                    function expand(d) {
                        if (d._children) {
                            d.children = d._children;
                            d.children.forEach(expand);
                            d._children = null;
                        }
                    }

                    var overCircle = function(d) {
                        selectedNode = d;
                        updateTempConnector();
                    };
                    var outCircle = function(d) {
                        selectedNode = null;
                        updateTempConnector();
                    };

                    // Function to update the temporary connector indicating dragging affiliation
                    var updateTempConnector = function() {
                        var data = [];
                        if (draggingNode !== null && selectedNode !== null) {
                            // have to flip the source coordinates since we did this for the existing connectors on the original tree
                            data = [{
                                source: {
                                    x: selectedNode.y0,
                                    y: selectedNode.x0
                                },
                                target: {
                                    x: draggingNode.y0,
                                    y: draggingNode.x0
                                }
                            }];
                        }
                        var link = svgGroup.selectAll(".templink").data(data);

                        link.enter().append("path")
                            .attr("class", "templink")
                            .attr("d", d3.svg.diagonal())
                            .attr('pointer-events', 'none');

                        link.attr("d", d3.svg.diagonal());

                        link.exit().remove();
                    };

                    // Function to center node when clicked/dropped so node doesn't get lost when collapsing/moving with large amount of children.

                    function centerNode(source) {
                        scale = zoomListener.scale();
                        x = -source.y0;
                        y = -source.x0;
                        x = x * scale + viewerWidth / 2;
                        y = y * scale + viewerHeight / 2;
                        d3.select('g').transition()
                            .duration(duration)
                            .attr("transform", "translate(" + x + "," + y + ")scale(" + scale + ")");
                        zoomListener.scale(scale);
                        zoomListener.translate([x, y]);
                    }

                    // Toggle children function

                    function toggleChildren(d) {
                        if (d.children) {
                            d._children = d.children;
                            d.children = null;
                        } else if (d._children) {
                            d.children = d._children;
                            d._children = null;
                        }
                        return d;
                    }

                    // Toggle children on click.

                    function click(d) {
                        if (d3.event.defaultPrevented) return; // click suppressed
                        d = toggleChildren(d);
                        update(d);
                        centerNode(d);
                    }

                    function update(source) {
                        // Compute the new height, function counts total children of root node and sets tree height accordingly.
                        // This prevents the layout looking squashed when new nodes are made visible or looking sparse when nodes are removed
                        // This makes the layout more consistent.
                        var levelWidth = [1];
                        var childCount = function(level, n) {

                            if (n.children && n.children.length > 0) {
                                if (levelWidth.length <= level + 1) levelWidth.push(0);

                                levelWidth[level + 1] += n.children.length;
                                n.children.forEach(function(d) {
                                    childCount(level + 1, d);
                                });
                            }
                        };
                        childCount(0, root);
                        var newHeight = d3.max(levelWidth) * 25; // 25 pixels per line
                        tree = tree.size([newHeight, viewerWidth]);

                        // Compute the new tree layout.
                        var nodes = tree.nodes(root).reverse(),
                            links = tree.links(nodes);

                        // Set widths between levels based on maxLabelLength.
                        nodes.forEach(function(d) {
                            d.y = (d.depth * (maxLabelLength * 10)); //maxLabelLength * 10px
                            // alternatively to keep a fixed scale one can set a fixed depth per level
                            // Normalize for fixed-depth by commenting out below line
                            // d.y = (d.depth * 500); //500px per level.
                        });

                        // Update the nodes…
                        node = svgGroup.selectAll("g.node")
                            .data(nodes, function(d) {
                                return d.id || (d.id = ++i);
                            });

                        // Enter any new nodes at the parent's previous position.
                        var nodeEnter = node.enter().append("g")
                            .call(dragListener)
                            .attr("class", "node")
                            .attr("transform", function(d) {
                                return "translate(" + source.y0 + "," + source.x0 + ")";
                            })
                            .on('click', click);

                        nodeEnter.append("circle")
                            .attr('class', 'nodeCircle')
                            .attr("r", 0)
                            .style("fill", function(d) {
                                return d._children ? "lightsteelblue" : "#fff";
                            });

                        nodeEnter.append("text")
                            .attr("x", function(d) {
                                return d.children || d._children ? -10 : 10;
                            })
                            .attr("dy", ".35em")
                            .attr('class', 'nodeText')
                            .attr("text-anchor", function(d) {
                                return d.children || d._children ? "end" : "start";
                            })
                            .text(function(d) {
                                return d.name;
                            })
                            .style("fill-opacity", 0);

                        // phantom node to give us mouseover in a radius around it
                        nodeEnter.append("circle")
                            .attr('class', 'ghostCircle')
                            .attr("r", 30)
                            .attr("opacity", 0.2) // change this to zero to hide the target area
                            .style("fill", "red")
                            .attr('pointer-events', 'mouseover')
                            .on("mouseover", function(node) {
                                overCircle(node);
                            })
                            .on("mouseout", function(node) {
                                outCircle(node);
                            });

                        // Update the text to reflect whether node has children or not.
                        node.select('text')
                            .attr("x", function(d) {
                                return d.children || d._children ? -10 : 10;
                            })
                            .attr("text-anchor", function(d) {
                                return d.children || d._children ? "end" : "start";
                            })
                            .text(function(d) {
                                var value = '';
                                if(d.size){
                                    value = d.size;
                                    value = '('+value+')';
                                }
                                return d.name+value;
                            });

                        // Change the circle fill depending on whether it has children and is collapsed
                        node.select("circle.nodeCircle")
                            .attr("r", 4.5)
                            .style("fill", function(d) {
                                return d._children ? "lightsteelblue" : "#fff";
                            });

                        // Transition nodes to their new position.
                        var nodeUpdate = node.transition()
                            .duration(duration)
                            .attr("transform", function(d) {
                                return "translate(" + d.y + "," + d.x + ")";
                            });

                        // Fade the text in
                        nodeUpdate.select("text")
                            .style("fill-opacity", 1);

                        // Transition exiting nodes to the parent's new position.
                        var nodeExit = node.exit().transition()
                            .duration(duration)
                            .attr("transform", function(d) {
                                return "translate(" + source.y + "," + source.x + ")";
                            })
                            .remove();

                        nodeExit.select("circle")
                            .attr("r", 0);

                        nodeExit.select("text")
                            .style("fill-opacity", 0);

                        // Update the links…
                        var link = svgGroup.selectAll("path.link")
                            .data(links, function(d) {
                                return d.target.id;
                            });

                        // Enter any new links at the parent's previous position.
                        link.enter().insert("path", "g")
                            .attr("class", "link")
                            .attr("d", function(d) {
                                var o = {
                                    x: source.x0,
                                    y: source.y0
                                };
                                return diagonal({
                                    source: o,
                                    target: o
                                });
                            });

                        // Transition links to their new position.
                        link.transition()
                            .duration(duration)
                            .attr("d", diagonal);

                        // Transition exiting nodes to the parent's new position.
                        link.exit().transition()
                            .duration(duration)
                            .attr("d", function(d) {
                                var o = {
                                    x: source.x,
                                    y: source.y
                                };
                                return diagonal({
                                    source: o,
                                    target: o
                                });
                            })
                            .remove();

                        // Stash the old positions for transition.
                        nodes.forEach(function(d) {
                            d.x0 = d.x;
                            d.y0 = d.y;
                        });
                    }

                    // Append a group which holds all nodes and which the zoom Listener can act upon.
                    var svgGroup = baseSvg.append("g");

                    // Define the root
                    root = treeData;
                    root.x0 = viewerHeight / 2;
                    root.y0 = 0;

                    // Layout the tree initially and center on the root node.
                    update(root);
                    centerNode(root);

                    //  document size change
                    $(document).resize(function(){
                        update(root);
                        centerNode(root);
                    });
                });
            }
        };
    }).directive("entityIsAvailable", ['$parse', 'DEBUGmode',
        function ($parse, DEBUGmode) {
            return {
                restrict: "EA",
                controller: function ($scope) {
                },
                link: function (scope, element, attrs) {

                    /*function logArrayElements(element, index, array) {
                        console.log('a[' + index + '] = ' + element);
                    }*/

                    attrs.$observe('entityDataSet', function(val) {
                        var emptyCount = 0;
                        $('.et-row').each(function() {
                            var cell = $.trim($(this).find('.cell.info').text());
                            if (cell.length == 0){
                                emptyCount = emptyCount+1;
                                if(emptyCount==2){
                                    $(this).addClass('nodisplayrow');
                                }
                            }
                        });

                        //entityData = val;
                        //entityData = val.replace(/\\/g, '');

                        /*var keys = [];
                        for (var key in val) {
                            if (val.hasOwnProperty(key)) {
                                keys.push(key);
                            }
                        }

                        console.log('keys '+keys)*/

                        /*var returnObj = JSON.parse(val);

                        for (var i=0; i<returnObj.length; i++) { // iterate on the array
                            var obj = returnObj[i];
                            for (var key in obj) { // iterate on object properties
                                var value = obj[key];
                                if (value=='is_place') return key;
                            }

                            console.log('key found '+key)
                        }*/

                        //val = JSON.parse(JSON.stringify(val));


                        if(DEBUGmode){
                            var a = setTimeout(function(){
                                //console.log('entityData with json - '+val);
                                clearTimeout(a);
                            },250);
                        }
                    });
                }
            };
        }
    ]);
}).call(this);