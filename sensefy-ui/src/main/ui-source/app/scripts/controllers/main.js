/*******************************************************************************
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *******************************************************************************/
/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   main controller js file to handle all modules
 */

(function () {
    var controllersModule;

    controllersModule = angular.module('SensefyControllers', [
        'pascalprecht.translate',
        'tmh.dynamicLocale',
        'LocalStorageModule',
        'auth'
    ])
        .config(['tmhDynamicLocaleProvider',
            function (tmhDynamicLocaleProvider) {
                return tmhDynamicLocaleProvider.localeLocationPattern('//code.angularjs.org/1.3.15/i18n/angular-locale_{{locale}}.js');
            }
        ]).config([
            'localStorageServiceProvider', function (localStorageServiceProvider) {
                return localStorageServiceProvider.setPrefix('sensefy');
            }
        ]).controller('RootController', [
            '$http', '$injector', '$scope', '$location', '$window', '$rootScope', '$translate', 'SensefyFavicon', 'SensefyDesktopLogo', 'SensefyDeviceLogo', 'tmhDynamicLocale', 'localStorageService', 'auth', 'SensefySearchLogin', 'DEBUGmode', 'CONSOLEmode', 'isJSON',
            function ($http, $injector, $scope, $location, $window, $rootScope, $translate, SensefyFavicon, SensefyDesktopLogo, SensefyDeviceLogo, tmhDynamicLocale, localStorageService, auth, SensefySearchLogin, DEBUGmode, CONSOLEmode, isJSON) {

                if(!auth.authenticated){
                    auth.clear;
                    document.location.href = SensefySearchLogin;
                }

                $scope.errors = {};
                tmhDynamicLocale.set('en-us');

                $scope.user = null;

                $scope.desktopLogo = SensefyDesktopLogo;
                $scope.deviceLogo = SensefyDeviceLogo;
                $scope.sensefyFavicon = SensefyFavicon;

                $http.get('/user/').success(function(data) {
                    $scope.user = data.name;
                    if(CONSOLEmode) {
                        console.log('rootcontroller and user GET request fired -> $scope.user '+$scope.user)
                        console.log('rootcontroller and user GET request fired -> data.name '+data.name)
                    }
                });

                /*$rootScope.user = {
                    username: $scope.user
                };*/

                $scope.changeLanguage = function (lang) {
                    if (lang === null) {
                        lang = 'en-us';
                    }
                    tmhDynamicLocale.set(lang);
                    return $translate.use(lang);
                };
            }
        ]).controller('LoginController', [
            '$scope', 'LoginService', 'localStorageService', 'auth', 'SensefySearchLogin', 'DEBUGmode', 'CONSOLEmode', 'isJSON',
            function ($scope, LoginService, localStorageService, auth, SensefySearchLogin, DEBUGmode, CONSOLEmode, isJSON) {

                if(!auth.authenticated){
                    auth.clear;
                    document.location.href = SensefySearchLogin;
                }

                $scope.credentials = {};

                $scope.authenticated = function () {
                    return auth.authenticated;
                };

                $scope.currentLanguage = ' : English';

                $scope.login = function () {
                    if(CONSOLEmode) {
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify($scope.credentials);
                        }
                        else{
                            a = $scope.credentials;
                        }
                        console.log('$scope.login -> user login : ' + a)
                    }
                    auth.authenticate($scope.credentials, function (authenticated) {
                        if (authenticated) {
                            $scope.error = false;
                        } else {
                            $scope.error = true;
                        }
                    });
                };

                $scope.logout = auth.clear;
            }
        ]).controller('SearchController', [
            '$scope', '$http', '$location', 'dataSources', '$rootScope', 'SemanticSearchService', 'SensefyFacetsPerGroup', 'SensefySortOptions', 'SensefyAPIUrl', 'SensefySearchIsClustering', 'SensefyDocsPreview', 'localStorageService', 'ApiService', 'SensefyPreviewDoc', 'auth', 'tmhDynamicLocale', '$translate', 'SensefySearchLogin', 'SensefyUNIXdate', 'DEBUGmode', 'CONSOLEmode', 'isJSON', 'SensefyDocSecurity', 'SensefySearchResponseFailedIsLogout', '$timeout',
            function ($scope, $http, $location, dataSources, $rootScope, SemanticSearchService, SensefyFacetsPerGroup, SensefySortOptions, SensefyAPIUrl, SensefySearchIsClustering, SensefyDocsPreview, localStorageService, ApiService, SensefyPreviewDoc, auth, tmhDynamicLocale, $translate, SensefySearchLogin, SensefyUNIXdate, DEBUGmode, CONSOLEmode, isJSON, SensefyDocSecurity, SensefySearchResponseFailedIsLogout, $timeout) {
            //PDFViewerService, pdf
                var FILTER_LABEL_SEPARATOR, HTMLtagCleaner, addMissingFacets, allSource, cleanLocationSearchParameters, entityMap, escapeHtmlExceptB, fillLocationSearchParameters, getActiveSource, getDataSourceByValue, initDataSources, parseFacets, parseSimpleFacet, processHighlightInfo, removeCluster, removeFilter, resetSelectedValues;
                FILTER_LABEL_SEPARATOR = "$$";

                $scope.authenticated = function () {
                    return auth.authenticated;
                };

                if(!auth.authenticated){
                    auth.clear;
                    document.location.href = SensefySearchLogin;
                }

                $scope.logout = function () {
                    auth.clear;
                    document.location.href = SensefySearchLogin;
                };

                if ($scope.authenticated) {
                    var addGGLclass;

                    addGGLclass = setTimeout(function () {
                        angular.element('body').addClass('ggl');
                        clearTimeout(addGGLclass);
                    }, 20);
                }
                else {
                    document.location.href = SensefySearchLogin;
                }

                $scope.currentLanguage = ' : English';

                angular.element('.ui.menu .dropdown')
                    .dropdown({
                        on: 'hover'
                    });
                angular.element('.ui.dropdown').dropdown({
                    onChange: function (value, text, $selectedItem) {
                        // TO DO
                    }
                });

                angular.element('.dropdown.input-select').dropdown({
                    onChange: function (value, text, $selectedItem) {
                        var sortOptions = value.split(",");
                        $scope.setSorting(sortOptions[0].trim(), sortOptions[1].trim());
                    }
                });

                if(!dataSources){
                    return false;
                }

                $http.get('/user/').success(function(data) {
                    $scope.user = data.name;
                    $scope.nickName = data.name;
                });


                $scope.changeLanguage = function (lang) {
                    var langInfo = ' : English';
                    if (lang === null) {
                        lang = 'en-us';
                    }
                    switch(lang){
                        case 'en-us':
                            langInfo = ' : English';
                            break;
                        case 'es-es':
                            langInfo = ' : Spanish';
                            break;
                        default:
                            langInfo = ' : English';
                    }
                    $scope.currentLanguage = langInfo;
                    tmhDynamicLocale.set(lang);
                    return $translate.use(lang);
                };

                if (Object.keys($location.search()).length !== 0) {
                    angular.element('header').removeClass('ggl');
                }

                allSource = {
                    value: 'All',
                    occurrence: 0,
                    filter: '',
                    active: true
                };
                $scope.allOccurrence = 0;
                initDataSources = function (isForce) {
                    if (isForce == null) {
                        isForce = false;
                    }
                    var facet, sources, _i, _k, _len, _ref, _lenK;
                    sources = [];
                    $scope.isDatasourceTab = false;
                    if(dataSources.data.facets && !isForce){
                        if(CONSOLEmode){
                            var a = '';
                            if(isJSON) {
                                a = JSON.stringify(dataSources.data.facets);
                            }
                            else{
                                a = dataSources.data.facets;
                            }
                            console.log('initDataSources -> dataSources.data.facets : '+ a)
                        }
                        _ref = dataSources.data.facets;
                        $scope.sources = [];
                        $scope.allOccurrence = 0;
                        $scope.isDatasourceTab = true;
                        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                            facet = _ref[_i];
                            if (facet.label === 'Data Source') {
                                sources = facet.facetItems;
                                for (_k = 0, _lenK = sources.length; _k < _lenK; _k++) {
                                    $scope.allOccurrence += eval(sources[_k].occurrence);
                                }
                            }
                        }
                        allSource.occurrence = $scope.allOccurrence;
                        sources.unshift(allSource);
                        if(CONSOLEmode){
                            var a = '';
                            if(isJSON) {
                                a = JSON.stringify(sources);
                            }
                            else{
                                a = sources;
                            }
                            console.log('initDataSources -> dataSources.data.facets -> @param:isForce=false - $scope.sources : '+ a)
                        }
                        $timeout(function () {
                            $scope.isDatasourceTab = false;
                        }, 0);
                        return $scope.sources = sources;
                    }

                    if($scope.responsedData && isForce){
                        if(CONSOLEmode){
                            var a = '';
                            if(isJSON) {
                                a = JSON.stringify($scope.responsedData.facets);
                            }
                            else{
                                a = $scope.responsedData.facets;
                            }
                            console.log('initDataSources -> $scope.responsedData.facets : '+a)
                        }
                        $scope.sources = [];
                        $scope.isDatasourceTab = true;

                        _ref = $scope.responsedData.facets;
                        $scope.sources = [];
                        $scope.allOccurrence = 0;
                        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                            facet = _ref[_i];
                            if (facet.label === 'Data Source') {
                                sources = facet.facetItems;
                                for (_k = 0, _lenK = sources.length; _k < _lenK; _k++) {
                                    $scope.allOccurrence += eval(sources[_k].occurrence);
                                }
                            }
                        }
                        allSource.occurrence = $scope.allOccurrence;
                        sources.unshift(allSource);

                        if(CONSOLEmode){
                            var a = '';
                            if(isJSON) {
                                a = JSON.stringify(sources);
                            }
                            else{
                                a = sources;
                            }
                            console.log('initDataSources -> $scope.responsedData.facets -> @param:isForce=true - $scope.sources : '+a)
                        }

                        $timeout(function () {
                            $scope.isDatasourceTab = false;
                        }, 0);

                        return $scope.sources = sources;
                    }
                };
                getActiveSource = function (defaultSource) {
                    var active, s, _i, _len, _ref;
                    if (defaultSource == null) {
                        defaultSource = allSource;
                    }
                    _ref = $scope.sources;
                    if(_ref) {
                        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                            s = _ref[_i];
                            if (s.active === true) {
                                active = s;
                            }
                        }
                        if (active) {
                            return active;
                        } else {
                            return defaultSource;
                        }
                    }
                };
                getDataSourceByValue = function (sources, value) {
                    var source, _i, _len;
                    for (_i = 0, _len = sources.length; _i < _len; _i++) {
                        source = sources[_i];
                        if (source.value === value) {
                            return source;
                        }
                    }
                };
                $scope.clusters = [];
                $scope.originalCluster = [];
                parseFacets = function (response) {
                    var activeSource, facet, _i, _len, _ref;
                    activeSource = getActiveSource();
                    $scope.facets = [];
                    if ($scope.clusterSelected === false) {
                        $scope.clusters = response.clusters;
                        $scope.originalCluster = angular.copy(response.clusters);
                    }
                    _ref = response.facets;
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        facet = _ref[_i];
                        if (facet.label !== 'Data Source') {
                            $scope.facets.push(parseSimpleFacet(facet));
                        }
                    }
                    return addMissingFacets();
                };
                parseSimpleFacet = function (facet) {
                    var fItem, fItems, fItemsFilters, filtered, humanReadableSize, intervalValues, itemValue, iv, lastSelected, res, x, _i, _j, _k, _len, _len1, _len2, _ref;
                    humanReadableSize = function (size) {
                        var i, sizes;
                        if (size) {
                            sizes = [
                                'B',
                                'KB',
                                'MB',
                                'GB',
                                'TB'
                            ];
                            i = 0;
                            if (typeof size === 'string') {
                                size = parseInt(size);
                            }
                            while (++i && size > 1000) {
                                size = size / 1000;
                            }
                            return size.toFixed(2) + ' ' + sizes[i - 1];
                        }
                    };
                    fItems = [];
                    _ref = facet.facetItems;
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        x = _ref[_i];
                        if (facet.label === 'Size') {
                            itemValue = x.value;
                            intervalValues = itemValue.slice(1, -1).split(' TO ');
                            res = [];
                            for (_j = 0, _len1 = intervalValues.length; _j < _len1; _j++) {
                                iv = intervalValues[_j];
                                res.push(humanReadableSize(iv));
                            }
                            x.value = res.join(' - ');
                        }
                        if ($scope.isFacetSelect[x.filter]) {
                            fItems.unshift(x);
                        } else {
                            fItems.push(x);
                        }
                    }
                    filtered = $scope.filtersToShow.filter(function (e) {
                        return e.facetLabel === facet.label;
                    });
                    fItemsFilters = fItems.map(function (e) {
                        return e.filter;
                    });
                    for (_k = 0, _len2 = filtered.length; _k < _len2; _k++) {
                        lastSelected = filtered[_k];
                        if (fItemsFilters.indexOf(lastSelected.value) === -1) {
                            fItem = {
                                value: lastSelected.label,
                                occurrence: 0,
                                filter: lastSelected.value
                            };
                            fItems.unshift(fItem);
                        }
                    }
                    facet.facetItems = fItems;
                    return facet;
                };
                addMissingFacets = function () {
                    var f, facetLabel, filter, found, missingFacet, missingFacets, _i, _j, _len, _len1, _ref, _ref1, _results;
                    missingFacets = {};
                    _ref = $scope.filtersToShow;
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        filter = _ref[_i];
                        _ref1 = $scope.facets;
                        for (_j = 0, _len1 = _ref1.length; _j < _len1; _j++) {
                            f = _ref1[_j];
                            if (f.label === filter.facetLabel) {
                                found = f;
                            }
                        }
                        if (!!(found == null)) {
                            missingFacet = missingFacets[filter.facetLabel];
                            if (missingFacet === null || missingFacet === void 0) {
                                missingFacets[filter.facetLabel] = {
                                    label: filter.facetLabel,
                                    facetItems: []
                                };
                            }
                            missingFacets[filter.facetLabel].facetItems.push({
                                value: filter.label,
                                ocurrence: 0,
                                filter: filter.value
                            });
                        }
                    }
                    _results = [];
                    for (facetLabel in missingFacets) {
                        missingFacet = missingFacets[facetLabel];
                        _results.push($scope.facets.push(missingFacet));
                    }
                    return _results;
                };
                $scope.searchOptions = {
                    rows: 10,
                    fields: 'id,name,title,mimetype,size,description,author,container_url,ds_creator,ds_creation_date,ds_last_modifier,ds_last_modified,url,preview_url,path,thumbnail_base64,data_source,data_source_type,language,image_length,image_width,Sharpness,focal_length,geo_lat,geo_long',
                    start: 0
                };
                $scope.documents = [];
                $scope.queryTerm = '';
                $scope.sources = initDataSources();
                $scope.facets = [];
                $scope.clusters = [];
                $scope.filters = [];
                $scope.clusterFilters = [];
                $scope.clusterFiltersToShow = [];
                $scope.cluterQuery = '';
                $scope.clusterSelected = false;
                $scope.clusterSelectedSize = '';
                $scope.selectedCluterObj = [];
                $scope.filtersToShow = [];
                $scope.tmpFiltersToShow = [];
                $scope.showSuggestions = false;
                $scope.selectedEntity = null;
                $scope.selectedEntityType = null;
                $scope.selectedEntityTypeAttribute = null;
                $scope.selectedEntityTypeAttributeValue = null;
                $scope.selectedTitle = null;
                $scope.totalDocuments = -1;
                $scope.documentsPerPage = 10;
                $scope.documentsOffsetStart = 1;
                $scope.pagerOffsetStart = 1;
                $scope.documentsOffsetEnd = 10;
                $scope.currentPage = 1;
                $scope.collatedQuery = null;
                $scope.sorting = null;
                $scope.titleSorting = "";
                $scope.maxSize = 7;
                $scope.searching = false;
                $scope.sortings = SensefySortOptions;
                $scope.selectSortings = $scope.sortings[0];
                $scope.isFacetSelect = [];
                $scope.responsedData = [];
                $scope.isFacetSelectCluster = [];

                var q = $location.search().query;
                if(q){
                    $scope.queryTerm = q;
                }

                angular.element('.ui.dropdown.pe-itemsPerPage').dropdown({
                    onChange: function (value, text, $selectedItem) {
                        $scope.documentsPerPage = value;
                        $scope.updateDocumentOffset(false);
                        $scope.runCurrentQuery();
                    }
                });
                $scope.infiniteScroll = function () {
                    if ($scope.totalDocuments > 0 && ($scope.currentPage * $scope.documentsPerPage < $scope.totalDocuments) && ($scope.searching === false)) {
                        $scope.updateDocumentOffset(false);
                        $scope.currentPage++;
                        return $scope.runCurrentQuery();
                    }
                };
                $scope.setCurrentPage = function (pageToStart) {
                    window.scrollTo(0, 0);
                    $scope.updateDocumentOffset(false);
                    $scope.currentPage = pageToStart;
                    $scope.runCurrentQuery();
                };
                $scope.cleanFilters = function () {
                    var activeSource;
                    $scope.filters = [];
                    activeSource = getActiveSource();
                    if(activeSource){
                        if (activeSource.filter !== '') {
                            $scope.filters.push(activeSource.filter);
                        }
                        angular.forEach($scope.filtersToShow, function (filter, index) {
                            return $scope.isFacetSelect[filter.value] = false;
                        });
                        return $scope.filtersToShow = [];
                    }
                };
                $scope.setActiveSource = function (source, runQuery) {
                    var activeSource, pos, s, _i, _len, _ref;
                    if (runQuery == null) {
                        runQuery = true;
                    }
                    activeSource = getActiveSource();
                    if(activeSource) {
                        if (source.value !== activeSource.value) {
                            pos = $scope.filters.indexOf(activeSource.filter);
                            if (pos >= 0) {
                                $scope.filters.splice(pos, 1) >= 0;
                            }
                            _ref = $scope.sources;
                            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                                s = _ref[_i];
                                s.active = false;
                            }
                            source.active = true;
                            if (source.filter !== '') {
                                $scope.filters.push(source.filter);
                            }
                            if (runQuery) {
                                fillLocationSearchParameters();
                            }
                            if (runQuery) {
                                return $scope.runCurrentQuery(false);
                            }
                        }
                    }
                };
                $scope.setSorting = function (sortId, sortType) {
                    if (sortId === null || sortId === '') {
                        sortId = 'score';
                    }
                    if (sortType === null || sortType === 'undefined') {
                        sortType = 'DESC';
                    }
                    $scope.titleSorting = sortId + ' ' + sortType;
                    //$scope.sorting = sortType.trim();
                    $scope.updateDocumentOffset(true);
                    return $scope.runCurrentQuery(false);
                };

                $scope.addCluster = function (clusterItem, runQuery, index) {
                    var cpos;
                    if (runQuery == null) {
                        runQuery = true;
                    }
                    $scope.selectedCluterObj = clusterItem;
                    cpos = $scope.clusterFilters.indexOf(clusterItem.label);
                    if (cpos >= 0) {
                        $scope.cluterQuery = '';
                        $scope.clusterSelected = false;
                        removeCluster(clusterItem);
                        $scope.isFacetSelectCluster[index] = false;
                        return false;
                    } else {
                        $scope.cluterQuery = clusterItem.filterQuery;
                        $scope.clusters = [clusterItem];
                        $scope.clusterSelected = true;
                        $scope.clusterSelectedSize = clusterItem.size;
                        $scope.isFacetSelectCluster[index] = true;
                    }
                    $scope.clusterFilters.push(clusterItem.label);
                    $scope.updateDocumentOffset(runQuery);
                    fillLocationSearchParameters();
                    $scope.runCurrentQuery(false, true);
                };
                removeCluster = function (clusterItem) {
                    var cposr;
                    cposr = $scope.clusterFilters.indexOf(clusterItem.label);
                    if (cposr >= 0) {
                        $scope.clusterFilters.splice(cposr, 1);
                    }
                    fillLocationSearchParameters();
                    $scope.updateDocumentOffset(true);
                    $scope.runCurrentQuery(false, true);
                };
                $scope.addFilter = function (facetItem, facetLabel, runQuery) {
                    var filter, pos;
                    if (runQuery == null) {
                        runQuery = true;
                    }

                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify(facetItem);
                        }
                        else{
                            a = facetItem;
                        }
                        console.log('addFilter -> facetItem : '+a)
                    }

                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify(facetLabel);
                        }
                        else{
                            a = facetLabel;
                        }
                        console.log('addFilter -> facetLabel : '+a)
                    }


                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify(facetItem.filter);
                        }
                        else{
                            a = facetItem.filter;
                        }
                        console.log('addFilter -> facetItem.filter : '+a)
                    }
                    pos = $scope.filters.indexOf(facetItem.filter);

                    if (pos >= 0) {
                        removeFilter(facetItem);
                        return false;
                    } else {
                        filter = {
                            facetLabel: facetLabel,
                            label: facetItem.value,
                            value: facetItem.filter,
                            uriValue: facetItem.filter + FILTER_LABEL_SEPARATOR + facetItem.value + FILTER_LABEL_SEPARATOR + facetLabel
                        };
                        $scope.filters.push(filter.value);
                        $scope.filtersToShow.push(filter);

                        if(CONSOLEmode) {
                            var a = '';
                            if(isJSON) {
                                a = JSON.stringify($scope.filters);
                            }
                            else{
                                a = $scope.filters;
                            }
                            console.log('$scope.filters ' + JSON.stringify(a))
                        }
                        if(CONSOLEmode) {
                            var a = '';
                            if(isJSON) {
                                a = JSON.stringify($scope.filtersToShow);
                            }
                            else{
                                a = $scope.filtersToShow;
                            }
                            console.log('$scope.filtersToShow ' + JSON.stringify(a))
                        }
                    }
                    $scope.updateDocumentOffset(runQuery);
                    if (runQuery) {
                        fillLocationSearchParameters();
                        $scope.runCurrentQuery(false);
                    }
                    $scope.isFacetSelect[facetItem.filter] = true;
                };
                removeFilter = function (filter) {
                    var index, posf;
                    posf = $scope.filters.indexOf(filter.filter);
                    if (posf >= 0) {
                        $scope.filters.splice(posf, 1);
                    }
                    index = $scope.filtersToShow.map(function (e) {
                        return e.value;
                    }).indexOf(filter.filter);
                    if (index >= 0) {
                        $scope.filtersToShow.splice(index, 1);
                    }
                    fillLocationSearchParameters();
                    $scope.updateDocumentOffset(true);
                    $scope.runCurrentQuery(false);
                    $scope.isFacetSelect[filter.filter] = false;
                };
                HTMLtagCleaner = function (richHtml) {
                    var body, regex, result;
                    regex = /(<([^>]+)>)/ig;
                    if(CONSOLEmode){
                        console.log('HTMLtagCleaner -> richHtml '+richHtml);
                    }
                    if(richHtml!==null && richHtml !== undefined){
                        body = richHtml;
                        result = body.replace(regex, "");
                    }
                    else{
                        result = '';
                    }
                    return result;
                };
                $scope.descriptionExcerpt = function (text, numCharacters, end) {
                    if (numCharacters === null) {
                        numCharacters = 100;
                    }
                    if (end === null) {
                        end = "...";
                    }
                    if(text !== null || text !== '') {
                        if (text.length > numCharacters) {
                            return text.substring(0, numCharacters) + end;
                        } else {
                            return text;
                        }
                    }
                    else{
                        return false;
                    }
                };
                $scope.arrayToString = function (typeArray){
                    if(typeof typeArray !== 'undefined' && typeArray !== null && typeof typeArray.length === 'number'){
                        return typeArray.join(", ");
                    }
                    else{
                        return typeArray;
                    }
                };
                $scope.docFromTo = 10;
                $scope.updateDocumentOffset = function (restoreCurrentPage) {
                    if (restoreCurrentPage == null) {
                        restoreCurrentPage = true;
                    }
                    if (restoreCurrentPage) {
                        $scope.currentPage = 1;
                    }
                    $scope.documentsOffsetStart = ($scope.currentPage - 1) * $scope.documentsPerPage;
                    $scope.pagerOffsetStart = ($scope.currentPage - 1) * $scope.documentsPerPage;

                    if($scope.pagerOffsetStart==0){
                        $scope.docFromTo = parseInt($scope.pagerOffsetStart) + parseInt($scope.documentsPerPage);

                        $scope.pagerOffsetStart = 1;
                        $scope.documentsOffsetEnd = $scope.documentsPerPage;
                    }
                    else{
                        $scope.documentsOffsetEnd = $scope.documentsOffsetStart + $scope.documentsPerPage;
                        $scope.docFromTo = parseInt($scope.pagerOffsetStart) + parseInt($scope.documentsPerPage);
                    }

                    if($scope.documentsOffsetEnd <= $scope.totalDocuments){
                        $scope.documentsOffsetEnd = $scope.documentsOffsetStart + $scope.documentsPerPage;
                    }
                };
                $scope.titleSelected = function (title, removeFilters) {
                    var clustering;
                    if (removeFilters == null) {
                        removeFilters = true;
                    }
                    $scope.normalSearch = false;
                    $scope.searching = true;
                    $scope.queryTerm = HTMLtagCleaner(title.document_suggestion);
                    $scope.selectedTitle = title;
                    if (removeFilters) {
                        $scope.cleanFilters();
                    }
                    $scope.facets = {};
                    $scope.updateDocumentOffset(removeFilters);
                    return SemanticSearchService.search('id:"' + $scope.selectedTitle.id + '"', $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(
                    function (response) {
                        $scope.documents = response.data.searchResults.documents || [];
                        processHighlightInfo($scope.documents, response.data.searchResults.highlight);
                        $scope.selectedEntity = response.data.searchResults.entity || $scope.selectedEntity;
                        $scope.totalDocuments = response.data.searchResults.numFound;
                        parseFacets(response.data);
                        $scope.searching = false;
                    },
                    function (response) {
                        if(SensefySearchResponseFailedIsLogout){
                            $scope.logout();
                            if(CONSOLEmode){
                                console.log('$scope.titleSelected = function (title, removeFilters) is fired, but FAILED');
                            }
                            if(DEBUGmode){
                                debugger;
                            }
                        }
                    });
                };
                $scope.suggestionSelected = function (suggestion, removeFilters) {
                    var clustering;
                    if (removeFilters == null) {
                        removeFilters = true;
                    }
                    $scope.normalSearch = false;
                    $scope.searching = true;
                    $scope.queryTerm = HTMLtagCleaner(suggestion);
                    $scope.selectedTitle = null;
                    if (removeFilters) {
                        $scope.cleanFilters();
                    }
                    $scope.facets = {};
                    $scope.updateDocumentOffset(removeFilters);
                    return SemanticSearchService.search('"' + $scope.queryTerm + '"', $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(
                    function (response) {
                        $scope.documents = response.data.searchResults.documents || [];
                        processHighlightInfo($scope.documents, response.data.searchResults.highlight);
                        $scope.selectedEntity = response.data.searchResults.entity || $scope.selectedEntity;
                        $scope.totalDocuments = response.data.searchResults.numFound;
                        parseFacets(response.data);
                        $scope.searching = false;
                    },
                    function (response) {
                        if(SensefySearchResponseFailedIsLogout){
                            $scope.logout();
                            if(CONSOLEmode){
                                console.log('$scope.suggestionSelected = function (suggestion, removeFilters) is fired, but FAILED');
                            }
                            if(DEBUGmode){
                                debugger;
                            }
                        }
                    });
                };
                $scope.entitySelected = function (entity, removeFilters) {
                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify(entity);
                        }
                        else{
                            a = entity;
                        }
                        console.log('$scope.entitySelected = function (entity, removeFilters) { at CTRLer}')
                        console.log('$scope.entitySelected -> @param - entity at CTRLer : '+ a);
                    }
                    var clustering, security;
                    if (removeFilters == null) {
                        removeFilters = true;
                    }
                    $scope.normalSearch = false;
                    $scope.searching = true;
                    $scope.selectedEntity = entity;
                    if (removeFilters) {
                        $scope.cleanFilters();
                    }
                    $scope.facets = {};
                    $scope.queryTerm = HTMLtagCleaner(entity.label);
                    $scope.updateDocumentOffset(removeFilters);
                    return SemanticSearchService.searchByEntity($scope.selectedEntity.id, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true, security=SensefyDocSecurity).then(
                    function (response) {
                        if(CONSOLEmode){
                            console.log('SemanticSearchService.searchByEntity($scope.selectedEntity.id, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true, security=SensefyDocSecurity)');
                        }
                        $scope.documents = response.data.searchResults.documents || [];
                        processHighlightInfo($scope.documents, response.data.searchResults.highlight);
                        $scope.selectedEntity = response.data.searchResults.entity || $scope.selectedEntity;
                        $scope.totalDocuments = response.data.searchResults.numFound;
                        parseFacets(response.data);
                        $scope.responsedData = response.data;
                        initDataSources(true);
                    },
                    function (response) {
                        if(SensefySearchResponseFailedIsLogout){
                            $scope.logout();
                            if(CONSOLEmode){
                                console.log('$scope.entitySelected = function (entity, removeFilters) {} is fired, but FAILED');
                            }
                            if(DEBUGmode){
                                debugger;
                            }
                        }
                    });
                };
                $scope.entityTypeSelected = function (entityType, removeFilters) {
                    var clustering, security;
                    if (removeFilters == null) {
                        removeFilters = true;
                    }
                    $scope.normalSearch = false;
                    $scope.searching = true;
                    $scope.selectedEntityType = entityType;
                    if (removeFilters) {
                        $scope.cleanFilters();
                    }
                    $scope.selectedEntity = null;
                    $scope.queryTerm = HTMLtagCleaner(entityType.label);
                    $scope.updateDocumentOffset(removeFilters);
                    var encodedURI = encodeURIComponent($scope.selectedEntityType.id);
                    return SemanticSearchService.searchByEntityType(encodedURI,$scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true, security = SensefyDocSecurity).then(
                    function (response) {
                        if(CONSOLEmode){
                            console.log('SemanticSearchService.searchByEntityType(encodedURI,$scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true, security = SensefyDocSecurity)');
                        }
                        $scope.documents = response.data.searchResults.documents || [];
                        $scope.selectedEntityType = response.data.searchResults.entityType || $scope.selectedEntityType;
                        $scope.totalDocuments = response.data.searchResults.numFound;
                        parseFacets(response.data);
                        $scope.searching = false;
                    },
                    function (response) {
                        if(SensefySearchResponseFailedIsLogout){
                            $scope.logout();
                            if(CONSOLEmode){
                                console.log('$scope.entityTypeSelected -> (entityType, removeFilters) {} is fired, but FAILED');
                            }
                            if(DEBUGmode){
                                debugger;
                            }
                        }
                    });
                };
                $scope.entityTypeAttributeSelected = function (entityTypeAttribute, removeFilters) {
                    if (removeFilters == null) {
                        removeFilters = true;
                    }
                    $scope.normalSearch = false;
                    $scope.selectedEntityTypeAttribute = entityTypeAttribute;
                    if (removeFilters) {
                        //return $scope.cleanFilters();
                    }
                };
                $scope.entityTypeAttributeValueSelected = function (value, removeFilters) {
                    var clustering;
                    if (removeFilters == null) {
                        removeFilters = true;
                    }
                    $scope.normalSearch = false;
                    $scope.selectedEntityTypeAttributeValue = value;
                    $scope.selectedEntity = null;
                    $scope.updateDocumentOffset(removeFilters);
                    return SemanticSearchService.searchByEntityQuery($scope.selectedEntityType.id, $scope.selectedEntityTypeAttribute, $scope.selectedEntityTypeAttributeValue, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(function (response) {
                        $scope.documents = response.data.searchResults.documents || [];
                        $scope.selectedEntityType = response.data.searchResults.entityType || $scope.selectedEntityType;
                        $scope.totalDocuments = response.data.searchResults.numFound;
                        //initDataSources(response.data);
                        return parseFacets(response.data);
                    }, function (response) {
                        //return $scope.logout();
                    });
                };
                $scope.query = function (restorePagination, cluster) {
                    var apiQuery, clustering;
                    if (restorePagination === null) {
                        restorePagination = true;
                    }
                    if (cluster === null) {
                        cluster = SensefySearchIsClustering;
                    }
                    else{
                        cluster = cluster;
                    }
                    $scope.searching = true;
                    if ($scope.queryTerm.length > 0) {
                        if(CONSOLEmode){
                            console.log('$scope.query = function (restorePagination, cluster) { if ($scope.queryTerm.length > 0) {}}');
                        }

                        $scope.normalSearch = true;
                        $scope.collatedQuery = null;
                        resetSelectedValues();
                        if (restorePagination) {
                            $scope.cleanFilters();
                        }
                        cleanLocationSearchParameters();
                        fillLocationSearchParameters();
                        $scope.updateDocumentOffset(restorePagination);
                        if (restorePagination) {
                            $scope.documents = [];
                        }
                        if ($scope.cluterQuery !== '') {
                            apiQuery = $scope.cluterQuery;
                        } else {
                            apiQuery = $scope.queryTerm;
                        }
                        if (auth.authenticated) {
                            angular.element('#sensefy').removeClass('ggl');
                            SemanticSearchService.search(apiQuery, $scope.documentsOffsetStart, $scope.documentsPerPage, $scope.searchOptions.fields, $scope.filters, true, $scope.titleSorting, clustering = cluster).then(function (response) {

                                $scope.responsedData = response.data;

                                if(response.data.searchResults === null){
                                    if(CONSOLEmode){
                                        console.log('$scope.query -> response.data.searchResults as NULL');
                                    }
                                    return
                                }

                                /*if (restorePagination || $scope.currentPage === 1) {
                                    $scope.documents = response.data.searchResults.documents || [];
                                } else {
                                    $scope.documents = $scope.documents.concat(response.data.searchResults.documents);
                                }*/
                                $scope.documents = response.data.searchResults.documents || [];
                                processHighlightInfo($scope.documents, response.data.searchResults.highlight);
                                $scope.totalDocuments = response.data.searchResults.numFound;
                                if ($scope.totalDocuments > 0) {

                                    var addGGLclass2;

                                    addGGLclass2 = setTimeout(function () {
                                        angular.element('#sensefy').removeClass('ggl');
                                        clearTimeout(addGGLclass2);
                                    }, 25);
                                }

                                if (response.data.searchResults.collationQuery) {
                                    $scope.collatedQuery = response.data.searchResults.collationQuery;
                                }
                                parseFacets(response.data);
                                if(CONSOLEmode){
                                    console.log('initDataSources is fired from Query()');
                                }
                                initDataSources(true);
                                $scope.searching = false;
                                if ($scope.clusterSelectedSize !== '') {
                                    if ($scope.clusterSelectedSize > $scope.totalDocuments) {
                                        $scope.selectedCluterObj.size = $scope.totalDocuments;
                                        $scope.clusters = [$scope.selectedCluterObj];
                                    }
                                }
                            }, function (response) {
                                $scope.searching = false;
                                $scope.logout();
                                if(CONSOLEmode){
                                    console.log('$scope.query  is fired, but FAILED');
                                }
                                if(DEBUGmode){
                                    debugger;
                                }
                            });
                        }
                    } else {
                        return $scope.searching = false;
                    }
                };
                entityMap = {
                    '<': '&lt;',
                    '>': '&gt;'
                };
                escapeHtmlExceptB = function (string) {
                    string = string.replace(/[<>]/g, function (s) {
                        return entityMap[s];
                    });
                    string = string.replace('&lt;b&gt;', '<b class="test">').replace('&lt;/b&gt;', '</b>');
                    return string;
                };
                processHighlightInfo = function (documents, highlightInfo) {
                    var doc, _i, _j, _k, _len, _len1, _len2, _results;
                    if (highlightInfo == null) {
                        highlightInfo = [];
                    }
                    if (highlightInfo === null || highlightInfo.length === 0) {
                        return;
                    }
                    for (_i = 0, _len = documents.length; _i < _len; _i++) {
                        doc = documents[_i];
                        if (highlightInfo[doc.id] != null) {
                            doc.hls = highlightInfo[doc.id];
                        }
                    }
                    for (_j = 0, _len1 = documents.length; _j < _len1; _j++) {
                        doc = documents[_j];
                        doc.hls.content[0] = escapeHtmlExceptB(doc.hls.content[0]);
                    }
                    _results = [];
                    for (_k = 0, _len2 = documents.length; _k < _len2; _k++) {
                        doc = documents[_k];
                        if (doc.content) {
                            _results.push(doc.content[0] = escapeHtmlExceptB(doc.content[0]));
                        }
                    }
                    return _results;
                };
                $scope.loadEntities = function (document) {
                    var filters;
                    filters = [];
                    if ($scope.selectedEntityType) {
                        filters.push('type:"' + $scope.selectedEntityType.id + '"');
                    }
                    if ($scope.selectedEntityTypeAttribute && $scope.selectedEntityTypeAttributeValue) {
                        filters.push($scope.selectedEntityTypeAttribute + ':"' + $scope.selectedEntityTypeAttributeValue + '"');
                    }
                    return SemanticSearchService.getEntities(document.id, 0, 10, "id,label,thumbnail", filters.join(" AND ")).then(function (response) {
                        return document.entities = response.data.searchResults.documents || [];
                    }, function (response) {
                        document.entities = [];
                        return $scope.logout();
                    })["finally"](function () {
                        return document.entitiesLoaded = true;
                    });
                };
                $scope.runCurrentQuery = function (restorePagination, cluster) {
                    if (restorePagination == null) {
                        restorePagination = false;
                    }
                    if (cluster == null) {
                        cluster = true;
                    }
                    $scope.searching = true;
                    if ($scope.selectedEntityType && $scope.selectedEntityTypeAttribute && $scope.selectedEntityTypeAttributeValue) {
                        return $scope.entityTypeAttributeValueSelected($scope.selectedEntityTypeAttributeValue, false);
                    }
                    if ($scope.selectedEntityType !== null && $scope.selectedEntityTypeAttribute === null && $scope.selectedEntityTypeAttributeValue === null) {
                        return $scope.entityTypeSelected($scope.selectedEntityType, false);
                    }
                    if ($scope.selectedEntity !== null) {
                        return $scope.entitySelected($scope.selectedEntity, false);
                    }
                    if ($scope.selectedTitle !== null) {
                        return $scope.titleSelected($scope.selectedTitle, false);
                    }
                    if(CONSOLEmode){
                        console.log('$scope.runCurrentQuery = function (restorePagination, cluster) {}');
                    }
                    return $scope.query(restorePagination, cluster);
                };
                $scope.$watch('selectSortings', function (newValue, oldValue) {
                    return $scope.setSorting($scope.sortings[0].sortId, $scope.sortings[0].defaultSort);
                    //$scope.setSorting(newValue.defaultSort);
                });
                $scope.$on('entitySelected', function (event, entity) {
                    if(CONSOLEmode){
                        console.log('$scope.$on(entitySelected, function (event, entity) {}): at controller 976');
                    }
                    resetSelectedValues();
                    $scope.entitySelected(entity);
                    cleanLocationSearchParameters();
                    fillLocationSearchParameters();
                });
                $scope.$on('entityTypeSelected', function (event, entityType) {
                    resetSelectedValues();
                    $scope.entityTypeSelected(entityType);
                    cleanLocationSearchParameters();
                    fillLocationSearchParameters();
                });
                $scope.$on('entityTypeAttributeSelected', function (event, entityTypeAttribute) {
                    $scope.entityTypeAttributeSelected(entityTypeAttribute);
                    cleanLocationSearchParameters();
                    fillLocationSearchParameters();
                });
                $scope.$on('entityTypeAttributeValueSelected', function (event, attribute, value) {
                    $scope.entityTypeAttributeValueSelected(value);
                    cleanLocationSearchParameters();
                    fillLocationSearchParameters();
                });
                $scope.$on('titleSelected', function (event, title) {
                    resetSelectedValues();
                    $scope.titleSelected(title);
                    cleanLocationSearchParameters();
                    fillLocationSearchParameters();
                });
                $scope.$on('suggestionSelected', function (event, suggestion) {
                    resetSelectedValues();
                    $scope.queryTerm = suggestion;
                    cleanLocationSearchParameters();
                    fillLocationSearchParameters();
                });
                $scope.$on('entityTypeCleaned', function () {
                    $scope.selectedEntityType = null;
                    $scope.selectedEntityTypeAttribute = null;
                    $scope.selectedEntityTypeAttributeValue = null;
                    $scope.queryTerm = '';
                });
                $scope.$on('entityTypeAttributeCleaned', function () {
                    $scope.selectedEntityTypeAttribute = null;
                    $scope.selectedEntityTypeAttributeValue = null;
                     $scope.queryTerm = '';
                });
                $scope.$on('entityTypeAttributeCleaned', function () {
                    $scope.selectedEntityTypeAttributeValue = null;
                    $scope.queryTerm = '';
                });
                resetSelectedValues = function () {
                    $scope.selectedEntity = null;
                    $scope.selectedEntityType = null;
                    $scope.selectedEntityTypeAttribute = null;
                    $scope.selectedEntityTypeAttributeValue = null;
                    $scope.selectedTitle = null;
                };
                cleanLocationSearchParameters = function () {
                    $location.search('query', null);
                    $location.search('entityId', null);
                    $location.search('entityType', null);
                    $location.search('entityTypeAttribute', null);
                    $location.search('entityTypeAttributeValue', null);
                    $location.search('title', null);
                    $location.search('source', null);
                    $location.search('filters', null);
                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify($location.search());
                        }
                        else{
                            a = $location.search();
                        }
                        console.log('cleanLocationSearchParameters -> $location.search : '+a)
                    }
                };
                fillLocationSearchParameters = function () {
                    var filters;
                    if ($scope.queryTerm) {
                        $location.search('query', HTMLtagCleaner($scope.queryTerm));
                    }
                    if ($scope.selectedEntity) {
                        $location.search('entityId', $scope.selectedEntity.id);
                    }
                    if ($scope.selectedEntityType) {
                        $location.search('entityType', $scope.selectedEntityType.id);
                    }
                    if ($scope.selectedEntityTypeAttribute) {
                        $location.search('entityTypeAttribute', $scope.selectedEntityTypeAttribute);
                    }
                    if ($scope.selectedEntityTypeAttributeValue) {
                        $location.search('entityTypeAttributeValue', $scope.selectedEntityTypeAttributeValue);
                    }
                    if ($scope.selectedTitle) {
                        $location.search('title', $scope.selectedTitle.id);
                    }
                    if ($scope.selectedTitle) {
                        $location.search('query', HTMLtagCleaner($scope.selectedTitle.document_suggestion));
                    }
                    $location.search('source', $scope.activeSource);
                    filters = [];
                    angular.forEach($scope.filtersToShow, function (filter, index) {
                        return filters.push(filter.uriValue);
                    });
                    $location.search('filters', null);
                    if (filters.length > 0) {
                        return $location.search('filters', filters.join(','));
                    }
                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify($location.search());
                        }
                        else{
                            a = $location.search();
                        }
                        console.log('fillLocationSearchParameters -> $location.search : '+ a)
                    }
                };
                $scope.cleanSearchParameters = function () {
                    resetSelectedValues();
                    cleanLocationSearchParameters();
                    angular.element('header').addClass('ggl');
                    $scope.queryTerm = '';
                    $scope.documents = [];
                    $scope.facets = [];
                    $scope.clusters = [];
                    $scope.filters = [];
                    $scope.clusterFilters = [];
                    $scope.clusterFiltersToShow = [];
                    $scope.selectedCluterObj = [];
                    $scope.filtersToShow = [];
                    $scope.tmpFiltersToShow = [];
                    $scope.showSuggestions = false;
                    $scope.totalDocuments = -1;
                    $scope.currentPage = 1;
                    $scope.collatedQuery = null;
                    $scope.sorting = null;
                    $scope.titleSorting = '';
                    $scope.isFacetSelect = [];
                    $scope.isFacetSelectCluster = [];
                    $location.path('/search');
                };
                $scope.initialize = function () {
                    var entity, params, parsedFilters;
                    params = $location.search();
                    if(CONSOLEmode){
                        var a = '';
                        if(isJSON) {
                            a = JSON.stringify(params);
                        }
                        else{
                            a = params;
                        }
                        console.log('$scope.initialize -> params  : '+ a);
                    }
                    if (params.query !== void 0) {
                        $scope.queryTerm = params.query;
                    }
                    if (params.entityId) {
                        entity = {
                            id: params.entityId,
                            label: $scope.queryTerm
                        };
                        $scope.selectedEntity = entity;
                    }
                    if (params.entityType) {
                        $scope.selectedEntityType = {
                            id: params.entityType
                        };
                        if (params.entityTypeAttribute !== void 0 && params.entityTypeAttributeValue !== void 0) {
                            $scope.selectedEntityTypeAttribute = params.entityTypeAttribute;
                            $scope.selectedEntityTypeAttributeValue = params.entityTypeAttributeValue;
                        }
                    }
                    if (params.title) {
                        $scope.selectedTitle = {
                            id: params.title
                        };
                        if ($scope.queryTerm !== void 0) {
                            $scope.selectedTitle.document_suggestion = $scope.queryTerm;
                        }
                    }
                    if (params.source) {
                        $scope.setActiveSource(params.source, false);
                    }
                    if (params.filters) {
                        parsedFilters = params.filters.split(',');
                        angular.forEach(parsedFilters, function (filter, key) {
                            var facetEntity, parts, value;
                            if (filter !== '') {
                                parts = filter.split(FILTER_LABEL_SEPARATOR);
                                if (parts.length === 3) {
                                    value = parts[1];
                                    facetEntity = {
                                        value: value,
                                        filter: parts[0]
                                    };
                                    return $scope.addFilter(facetEntity, parts[2], false);
                                }
                            }
                        });
                    }
                    if(CONSOLEmode){
                        console.log('$scope.entitySelected === null --> $scope.initialize()');
                    }
                    $scope.runCurrentQuery();
                };
                if($scope.entitySelected === null){
                    $scope.initialize();
                    if(CONSOLEmode){
                        console.log('$scope.initialize = function () {}');
                    }
                }
                $scope.$on('$locationChangeSuccess', function (event) {
                    $scope.cleanFilters();
                    $scope.initialize();
                    if(CONSOLEmode){
                        console.log('$scope.$on($locationChangeSuccess, function (event) {}');
                    }
                });
                $scope.logoClick = function () {
                    $scope.cleanFilters();
                    window.history.go(-1);
                    $scope.queryTerm = '';
                };
                $scope.SensefyFacetsPerGroupMin = SensefyFacetsPerGroup;
                $scope.humanReadableSize = function (size) {
                    var i, sizes;
                    if (size) {
                        sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
                        i = 0;
                        while (++i && size > 1024) {
                            size = size / 1024;
                        }
                        return size.toFixed(2) + ' ' + sizes[i - 1];
                    }
                };


                $scope.dateTimeFormatter = function(unixtime){
                    var timestamp = moment.unix(unixtime/SensefyUNIXdate);
                    return timestamp.fromNow(); //  return date or time based on himunalized
                };

                $scope.dateTimeFormatterAcc = function(unixtime){
                    var timestampAcc = moment.unix(unixtime/SensefyUNIXdate);
                    return timestampAcc.utc().format("YYYY-MM-DD HH:mm"); //    return date or time based on UTC
                };

                var mobDSswitchFlag = true;
                $scope.mobDSswitch = function(){
                    if(CONSOLEmode) {
                        console.log('mobDSswitchFlag');
                    }
                    if(mobDSswitchFlag){
                        angular.element('.ui.menu.ds .content').show();
                        angular.element('.ui.menu.ds .content').addClass('pe-ds-switch');
                        mobDSswitchFlag = false;
                    }
                    else{
                        angular.element('.ui.menu.ds .content').removeClass('pe-ds-switch');
                        angular.element('.ui.menu.ds .content').hide();
                        mobDSswitchFlag = true;
                    }
                };


                $scope.urlTruncate = function (fullStr, strLen, separator) {
                    if (fullStr.length <= strLen) return fullStr;

                    strLen = strLen || 50;
                    separator = separator || '...';

                    var sepLen = separator.length,
                        charsToShow = strLen - sepLen,
                        frontChars = Math.ceil(charsToShow / 2),
                        backChars = Math.floor(charsToShow / 2);

                    return fullStr.substr(0, frontChars) + separator + fullStr.substr(fullStr.length - backChars);
                };

                $scope.pickDocIcon = function (docType) {
                    var docIcon;
                    switch (docType) {
                        case 'application/msword':
                            docIcon = "word";
                            break;
                        case 'application/pdf':
                            docIcon = "pdf";
                            break;
                        case 'application/vnd.ms-excel':
                            docIcon = "excel";
                            break;
                        case 'application/zip':
                            docIcon = "archive";
                            break;
                        case 'application/x-tex':
                            docIcon = "text";
                            break;
                        case 'image/tiff':
                            docIcon = "image";
                            break;
                        case 'text/xml':
                            docIcon = "code";
                            break;
                        case 'image/png':
                            docIcon = "image";
                            break;
                        case 'image/jpeg':
                            docIcon = "image";
                            break;
                        case 'image/png':
                            docIcon = "image";
                            break;
                        case 'image/jpg':
                            docIcon = "image";
                            break;
                        case 'video/mp4':
                            docIcon = "video";
                            break;
                        case 'text/html':
                            docIcon = "code";
                            break;
                        case 'application/x-javascript':
                            docIcon = "code";
                            break;
                        case 'text/css':
                            docIcon = "code";
                            break;
                    }

                    return docIcon;
                };
                /*$scope.viewer = pdf.Instance("viewer");
                $scope.isClose = false;
                $scope.previewNextPage = function () {
                    return $scope.viewer.nextPage();
                };
                $scope.previewPreviousPage = function () {
                    return $scope.viewer.prevPage();
                };
                $scope.pageLoaded = function (curPage, totalPages) {
                    $scope.previewCurrentPage = curPage;
                    return $scope.previewTotalPages = totalPages;
                };
                $scope.loadProgress = function (state, loaded, total) {
                    return $scope.previewLoading = state !== 'finished';
                };
                $scope.resultContent = function (document, size) {
                    var modalInstance;
                    $scope.resultContent = document.content;
                    $scope.documentPrevName = document.name;*/
                    /*
                     return modalInstance = $modal.open({
                     templateUrl: 'views/content.html',
                     controller: 'ModalInstanceCtrl',
                     size: size,
                     scope: $scope
                     });
                     */
                /*};
                $scope.open = function (document, size) {
                    var modalInstance;
                    $scope.previewNotSupported = false;
                    $scope.documentPrevName = document.name;
                    $scope.document = document;
                    $scope.documentPrevUrl = {};
                    $scope.prev_url = document.preview_url;
                    $scope.mimeType = document.mimetype;
                    if (document.data_source_type === 'Alfresco') {
                        $scope.documentPrevType = 'default';
                        $scope.previewLoading = false;
                        if ((document.preview_url !== void 0 && document.preview_url.indexOf("thumbnails/pdf") !== -1) || document.mimetype === "application/pdf") {
                            $scope.documentPrevType = 'pdf';
                        }
                        if (document.preview_url !== void 0 && (document.preview_url.indexOf("thumbnails/imgpreview") !== -1 || document.mimetype.indexOf('image') > -1)) {
                            $scope.documentPrevType = 'imgpreview';
                        }
                        $scope.documentPrevUrl = {
                            url: SensefyAPIUrl + SensefyDocsPreview + '?docId=' + document.id,
                            httpHeaders: {
                                token: $scope.user.token
                            }
                        };
                        if ($scope.documentPrevType === 'pdf') {
                            $scope.previewLoading = true;
                        }
                        if ($scope.documentPrevType === 'imgpreview') {
                            $scope.previewLoading = true;
                            if (document.mimetype.indexOf('image/') === -1) {
                                $scope.mimeType = 'image/png';
                            }
                            ApiService.get(SensefyPreviewDoc, {}, {
                                docId: document.id,
                                base64: true
                            }, {
                                token: $scope.user.token
                            }).then(function (response) {
                                $scope.documentPrevUrl.base64 = response.data;
                                return $scope.previewLoading = false;
                            });
                        }
                        $scope.document = document;
                    } else {
                        $scope.previewNotSupported = true;
                    }*/
                    /*
                     return modalInstance = $modal.open({
                     templateUrl: 'views/preview.html',
                     controller: 'ModalInstanceCtrl',
                     size: size,
                     scope: $scope
                     });
                     */
                /*};

                if ($scope.queryTerm !== '') {
                    angular.element('body').removeClass('ggl');
                }
                else {
                    angular.element('body').addClass('ggl');
                }

                return $scope.hightlight = function ($event) {
                    //return console.log($event.currentTarget);
                };*/
            }
        ]).controller('MltController', [
            '$scope', '$location', 'document', 'SemanticSearchService', 'SemanticMoreLikeThisService', '$translate', 'SensefyAPIUrl', 'SensefyDocsPreview', 'ApiService', 'SensefyPreviewDoc', '$rootScope', 'PDFViewerService', function ($scope, $location, document, SemanticSearchService, SemanticMoreLikeThisService, $translate, SensefyAPIUrl, SensefyDocsPreview, ApiService, SensefyPreviewDoc, $rootScope, pdf) {
                /*
                 var filterDocuments, normalizeDocumentsScore, sliderLabels;
                 $scope.searchOptions = {
                 rows: 10,
                 fields: "id,score,name,title,mimetype,size,description,author,container_url,ds_creator,ds_creation_date,ds_last_modifier,ds_last_modified,url,preview_url,path,thumbnail_base64,data_source,data_source_type,language,image_length,image_width,Sharpness,focal_length,geo_lat,geo_long",
                 start: 0
                 };
                 sliderLabels = {};
                 $translate('txtLow').then(function (result) {
                 return sliderLabels['low'] = result;
                 });
                 $translate('txtHigh').then(function (result) {
                 return sliderLabels['high'] = result;
                 });
                 $scope.humanReadableSize = function (size) {
                 var i, sizes;
                 if (size) {
                 sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
                 i = 0;
                 if (typeof size === "string") {
                 size = parseInt(size);
                 }
                 while (++i && size > 1000) {
                 size = size / 1000;
                 }
                 return size.toFixed(2) + " " + sizes[i - 1];
                 }
                 };
                 normalizeDocumentsScore = function (documents) {
                 var max, min;
                 min = 10000;
                 max = 0;
                 documents.forEach(function (doc) {
                 if (doc.score < min) {
                 min = doc.score;
                 }
                 if (doc.score > max) {
                 return max = doc.score;
                 }
                 });
                 documents.forEach(function (doc) {
                 return doc.score = (doc.score - min) / (max - min);
                 });
                 return documents;
                 };
                 filterDocuments = function (documents, threshold) {
                 var filtered;
                 filtered = [];
                 documents.forEach(function (doc) {
                 if (doc.score >= threshold / 100) {
                 return filtered.push(doc);
                 }
                 });
                 return filtered;
                 };
                 $scope.documents = [];
                 $scope.document = {};
                 $scope.docId = document.id;
                 $scope.init = false;
                 $scope.loading = true;
                 $scope.threshold = 70;
                 $scope.searching = false;
                 $scope.translate = function (value) {
                 if (value === 0) {
                 return sliderLabels['low'];
                 } else if (value === 100) {
                 return sliderLabels['high'];
                 }
                 return '';
                 };
                 $scope.isClose = false;
                 $scope.resultContent = function (document, size) {
                 var modalInstance;
                 $scope.resultContent = document.content;
                 $scope.documentPrevName = document.name;
                 return modalInstance = $modal.open({
                 templateUrl: 'views/content.html',
                 controller: 'ModalInstanceCtrl',
                 size: size,
                 scope: $scope
                 });
                 };
                 $scope.open = function (document, size) {
                 var childScope, modalInstance;
                 childScope = $rootScope.$new(true, $scope);
                 childScope.humanReadableSize = function (size) {
                 var i, sizes;
                 if (size) {
                 sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
                 i = 0;
                 while (++i && size > 1024) {
                 size = size / 1024;
                 }
                 return size.toFixed(2) + " " + sizes[i - 1];
                 }
                 };
                 childScope.viewer = pdf.Instance("viewer");
                 childScope.previewNextPage = function () {
                 return childScope.viewer.nextPage();
                 };
                 childScope.previewPreviousPage = function () {
                 return childScope.viewer.prevPage();
                 };
                 childScope.pageLoaded = function (curPage, totalPages) {
                 childScope.previewCurrentPage = curPage;
                 return childScope.previewTotalPages = totalPages;
                 };
                 childScope.loadProgress = function (state, loaded, total) {
                 return childScope.previewLoading = state !== 'finished';
                 };
                 childScope.previewNotSupported = false;
                 childScope.documentPrevName = document.name;
                 childScope.document = document;
                 childScope.documentPrevUrl = {};
                 childScope.prev_url = document.preview_url;
                 childScope.mimeType = document.mimetype;
                 if (document.data_source_type === 'Alfresco') {
                 childScope.documentPrevType = 'default';
                 childScope.previewLoading = false;
                 if ((document.preview_url !== void 0 && document.preview_url.indexOf("thumbnails/pdf") !== -1) || document.mimetype === "application/pdf") {
                 childScope.documentPrevType = 'pdf';
                 }
                 if (document.preview_url !== void 0 && (document.preview_url.indexOf("thumbnails/imgpreview") !== -1 || document.mimetype.indexOf('image') > -1)) {
                 childScope.documentPrevType = 'imgpreview';
                 }
                 childScope.documentPrevUrl = {
                 url: SensefyAPIUrl + SensefyDocsPreview + '?docId=' + document.id,
                 httpHeaders: {
                 token: $scope.user.token
                 }
                 };
                 if ($scope.documentPrevType === 'pdf') {
                 childScope.previewLoading = true;
                 }
                 if (childScope.documentPrevType === 'imgpreview') {
                 childScope.previewLoading = true;
                 if (document.mimetype.indexOf('image/') === -1) {
                 childScope.mimeType = 'image/png';
                 }
                 ApiService.get(SensefyPreviewDoc, {}, {
                 docId: document.id,
                 base64: true
                 }, {
                 token: $scope.user.token
                 }).then(function (response) {
                 childScope.documentPrevUrl.base64 = response.data;
                 return childScope.previewLoading = false;
                 });
                 }
                 } else {
                 childScope.previewNotSupported = true;
                 }
                 return modalInstance = $modal.open({
                 templateUrl: 'views/preview.html',
                 controller: 'ModalInstanceCtrl',
                 size: size,
                 scope: childScope
                 });
                 };
                 $scope.initialize = function () {
                 if (document.data.searchResults.numFound >= 1) {
                 $scope.document = document.data.searchResults.documents[0];
                 }
                 return SemanticMoreLikeThisService.getSimilarDocuments($scope.document.id, $scope.user.token, $scope.searchOptions.fields).then(function (response) {
                 $scope.allDocuments = normalizeDocumentsScore(response.data.searchResults.documents);
                 return $scope.documents = filterDocuments($scope.allDocuments, $scope.threshold);
                 }, function (response) {
                 $scope.documents = [];
                 return $scope.logout();
                 })["finally"](function () {
                 $scope.loading = false;
                 return $scope.init = true;
                 });
                 };
                 $scope.loadEntities = function (document) {
                 return SemanticSearchService.getEntities(document.id, $scope.user.token, 0, 10, "id,label,thumbnail").then(function (response) {
                 return document.entities = response.data.searchResults.documents || [];
                 }, function (response) {
                 document.entities = [];
                 return $scope.logout();
                 })["finally"](function () {
                 return document.entitiesLoaded = true;
                 });
                 };
                 $scope.initialize();
                 return $scope.$watch('threshold', function (newValue, oldValue) {
                 if ($scope.allDocuments !== void 0) {
                 return $scope.documents = filterDocuments($scope.allDocuments, $scope.threshold);
                 }
                 });
                 */
            }
        ]).controller('ImageSearchController', [
            '$scope', 'ImageSearchService', function ($scope, ImageSearchService) {
                /*
                 var getFileContentAsDataURL, humanReadableSize, search;
                 $scope.documents = [];
                 $scope.numFound = null;
                 $scope.selectedFile = null;
                 $scope.selectedSearchField = 'cl_ha';
                 $scope.searchFields = {
                 'cl_ha': 'ColorLayout',
                 'ph_ha': 'PHOG',
                 'oh_ha': 'OpponentHistogram',
                 'eh_ha': 'EdgeHistogram',
                 'jc_ha': 'JCD',
                 'sc_ha': 'Scalable Color',
                 'ce_ha': 'CEDD',
                 'fc_ha': 'FCTH'
                 };
                 getFileContentAsDataURL = function (file, selectedFile) {
                 var reader;
                 reader = new FileReader();
                 reader.onloadend = function () {
                 return selectedFile.dataURI = this.result;
                 };
                 return reader.readAsDataURL(file);
                 };
                 humanReadableSize = function (size) {
                 var i, sizes;
                 if (size) {
                 sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
                 i = 0;
                 while (++i && size > 1024) {
                 size = size / 1024;
                 }
                 return size.toFixed(2) + " " + sizes[i - 1];
                 }
                 };
                 search = function (file, field) {
                 $scope.loading = true;
                 return ImageSearchService.search(file, field, null).then(function (response) {
                 $scope.documents = response.data.searchResults.documents;
                 $scope.numFound = response.data.searchResults.numFound;
                 return $scope.loading = false;
                 }, function (response) {
                 return $scope.logout();
                 });
                 };
                 $scope.imageDropped = function () {
                 var file;
                 file = $scope.uploadedFile;
                 $scope.selectedFile = {
                 name: file.name,
                 type: file.type,
                 size: humanReadableSize(file.size)
                 };
                 getFileContentAsDataURL(file, $scope.selectedFile);
                 return search(file, $scope.selectedSearchField, null);
                 };
                 return $scope.searchByField = function (field) {
                 $scope.selectedSearchField = field;
                 return search($scope.uploadedFile, $scope.selectedSearchField);
                 };
                 */
            }
        ]).controller('ModalInstanceCtrl', [
            '$scope', '$modalInstance', function ($scope, $modalInstance) {
                $scope.ok = function () {
                    $modalInstance.close();
                };
                return $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        ]);

}).call(this);