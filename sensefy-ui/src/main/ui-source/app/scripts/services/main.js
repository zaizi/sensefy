(function () {
    angular.module('SensefyServices', []).factory('ApiService', [
        'SensefyAPIUrl', '$http', '$q', 'SensefySearchIsSemantic',
        function (SensefyAPIUrl, $http, $q, SensefySearchIsSemantic) {
            return {
                request: function (method, path, data, params, headers) {
                    var deferred;
                    if (data == null) {
                        data = {};
                    }
                    if (params == null) {
                        params = {};
                    }
                    if (headers == null) {
                        headers = {};
                    }
                    if ('POST' === method || 'PUT' === method) {
                        if (data instanceof FormData) {
                            headers = angular.extend(headers, {
                                'Content-Type': void 0
                            });
                        } else {
                            headers = angular.extend(headers, {
                                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                            });
                            data = $.param(data);
                        }
                    }
                    deferred = $q.defer();
                    $http({
                        method: method,
                        headers: headers,
                        transformRequest: angular.identity,
                        url: SensefyAPIUrl + path,
                        data: data,
                        params: params
                    }).success(function (data, status, headers, config) {
                        return deferred.resolve({
                            data: data,
                            status: status,
                            headers: headers,
                            config: config
                        });
                    }).error(function (data, status, headers, config) {
                        return deferred.reject({
                            data: data,
                            status: status,
                            headers: headers,
                            config: config
                        });
                    });
                    return deferred.promise;
                },
                "delete": function (path, data, params, headers) {
                    if (headers == null) {
                        headers = {};
                    }
                    return this.request('DELETE', path, data, params, headers);
                },
                get: function (path, data, params, headers) {
                    if (params == null) {
                        params = {};
                    }
                    if (headers == null) {
                        headers = {};
                    }
                    params.semantic = SensefySearchIsSemantic;
                    return this.request('GET', path, data, params, headers);
                },
                post: function (path, data, params, headers) {
                    if (headers == null) {
                        headers = {};
                    }
                    return this.request('POST', path, data, params, headers);
                },
                put: function (path, data, params, headers) {
                    if (headers == null) {
                        headers = {};
                    }
                    return this.request('PUT', path, data, params, headers);
                },
                jsonp: function (path, data, params) {
                    if (params == null) {
                        params = {};
                    }
                    params.callback = 'JSON_CALLBACK';
                    return this.request('jsonp', path, data, params);
                }
            };
        }
    ]).factory('LoginService', [
        'SensefyTokenCreatePath', 'ApiService',
        function (SensefyTokenCreatePath, ApiService) {
            return {
                login: function (username, password, params) {
                    var parameters;
                    if (params == null) {
                        params = null;
                    }
                    parameters = {
                        username: username,
                        password: password
                    };
                    if (params != null) {
                        angular.extend(parameters, params);
                    }
                    return ApiService.get(SensefyTokenCreatePath, {}, parameters);
                }
            };
        }
    ]).factory('SemanticSearchService', [
        'SensefySemanticSearchKeywordBased', 'SensefyEntityDrivenSearch', 'SensefyGetEntitiesByDoc', 'ApiService',
        function (SensefySemanticSearchKeywordBased, SensefyEntityDrivenSearch, SensefyGetEntitiesByDoc, ApiService) {
            return {
                search: function (query, start, rows, fields, filters, facet, sort, clustering) {
                    if (start == null) {
                        start = 0;
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    if (fields == null) {
                        fields = "*";
                    }
                    if (filters == null) {
                        filters = [];
                    }
                    if (facet == null) {
                        facet = true;
                    }
                    if (sort == null) {
                        sort = "";
                    }
                    if (clustering == null) {
                        clustering = false;
                    }
                    if (angular.isArray(filters)) {
                        filters = filters.join(",");
                    }
                    return ApiService.get(SensefySemanticSearchKeywordBased, {}, {
                        query: query,
                        start: start,
                        rows: rows,
                        filters: filters,
                        fields: fields,
                        facet: facet,
                        order: sort,
                        spellcheck: true,
                        clustering: clustering
                    });
                },
                topicSearch: function (highlightQuery, start, rows, fields, filters, facet, sort, clustering) {
                    if (start == null) {
                        start = 0;
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    if (fields == null) {
                        fields = "*";
                    }
                    if (filters == null) {
                        filters = [];
                    }
                    if (facet == null) {
                        facet = true;
                    }
                    if (sort == null) {
                        sort = "";
                    }
                    if (clustering == null) {
                        clustering = false;
                    }
                    if (angular.isArray(filters)) {
                        filters = filters.join(",");
                    }
                    return ApiService.get(SensefySemanticSearchKeywordBased, {}, {
                        query: highlightQuery,
                        start: start,
                        rows: rows,
                        filters: filters,
                        fields: fields,
                        facet: facet,
                        order: sort,
                        spellcheck: true,
                        clustering: clustering
                    });
                },
                searchByEntity: function (entityId, start, rows, fields, filters, facet, sort, clustering, security) {
                    if (start == null) {
                        start = 0;
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    if (fields == null) {
                        fields = "*";
                    }
                    if (filters == null) {
                        filters = [];
                    }
                    if (facet == null) {
                        facet = true;
                    }
                    if (sort == null) {
                        sort = "";
                    }
                    if (clustering == null) {
                        clustering = false;
                    }
                    if (security == null) {
                        security = true;
                    }
                    if (angular.isArray(filters)) {
                        filters = filters.join(",");
                    }
                    return ApiService.get(SensefyEntityDrivenSearch, {}, {
                        entityId: entityId,
                        start: start,
                        rows: rows,
                        filters: filters,
                        fields: fields,
                        facet: facet,
                        order: sort,
                        clustering: clustering,
                        security: security
                    });
                },
                searchByEntityQuery: function (entityType, entityAttribute, entityAttributeValue, start, rows, fields, filters, facet, sort, clustering, security) {
                    if (start == null) {
                        start = 0;
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    if (fields == null) {
                        fields = "*";
                    }
                    if (filters == null) {
                        filters = [];
                    }
                    if (facet == null) {
                        facet = true;
                    }
                    if (sort == null) {
                        sort = "";
                    }
                    if (clustering == null) {
                        clustering = false;
                    }
                    if (security == null) {
                        security = true;
                    }
                    if (angular.isArray(filters)) {
                        filters = filters.join(",");
                    }
                    return ApiService.get(SensefyEntityDrivenSearch, {}, {
                        entityType: entityType,
                        entityAttribute: entityAttribute,
                        entityAttributeValue: entityAttributeValue,
                        start: start,
                        rows: rows,
                        filters: filters,
                        fields: fields,
                        facet: facet,
                        order: sort,
                        clustering: clustering,
                        security: security
                    });
                },
                searchByEntityType: function (entityTypeId, start, rows, fields, filters, facet, sort, clustering, security) {
                    if (start == null) {
                        start = 0;
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    if (fields == null) {
                        fields = "*";
                    }
                    if (filters == null) {
                        filters = [];
                    }
                    if (facet == null) {
                        facet = true;
                    }
                    if (sort == null) {
                        sort = "";
                    }
                    if (clustering == null) {
                        clustering = false;
                    }
                    if (security == null) {
                        security = true;
                    }
                    if (angular.isArray(filters)) {
                        filters = filters.join(",");
                    }
                    return ApiService.get(SensefyEntityDrivenSearch, {}, {
                        entityType: entityTypeId,
                        start: start,
                        rows: rows,
                        filters: filters,
                        fields: fields,
                        facet: facet,
                        order: sort,
                        clustering: clustering,
                        security: security
                    });
                },
                getEntities: function (docId, start, rows, fields, filters, facet, sort, clustering, security) {
                    if (start == null) {
                        start = 0;
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    if (fields == null) {
                        fields = "*";
                    }
                    if (filters == null) {
                        filters = [];
                    }
                    if (facet == null) {
                        facet = true;
                    }
                    if (sort == null) {
                        sort = "";
                    }
                    if (clustering == null) {
                        clustering = false;
                    }
                    if (security == null) {
                        security = true;
                    }
                    return ApiService.get(SensefyGetEntitiesByDoc, {}, {
                        docId: docId,
                        start: start,
                        rows: rows,
                        filters: filters,
                        fields: fields,
                        facet: facet,
                        order: sort,
                        clustering: clustering,
                        security: security
                    });
                }
            };
        }
    ]).factory('SmartAutocompleteService', [
        'SensefySmartAutocompletePhase1', 'SensefySmartAutocompletePhase2', 'SensefySmartAutocompletePhase3', 'ApiService',
        function (SensefySmartAutocompletePhase1, SensefySmartAutocompletePhase2, SensefySmartAutocompletePhase3, ApiService) {
            return {
                phase1: function (termToComplete, numberOfSuggestions) {
                    if (numberOfSuggestions == null) {
                        numberOfSuggestions = 3;
                    }
                    return ApiService.get(SensefySmartAutocompletePhase1, {}, {
                        termToComplete: termToComplete,
                        numberOfSuggestions: numberOfSuggestions
                    });
                },
                phase2: function (entityTypeId, termToComplete, numberOfSuggestions) {
                    if (numberOfSuggestions == null) {
                        numberOfSuggestions = 3;
                    }
                    return ApiService.get(SensefySmartAutocompletePhase2, {}, {
                        entityTypeId: entityTypeId,
                        termToComplete: termToComplete,
                        numberOfSuggestions: numberOfSuggestions
                    });
                },
                phase3: function (entityTypeId, entityTypeAttribute, termToComplete, numberOfSuggestions) {
                    if (numberOfSuggestions == null) {
                        numberOfSuggestions = 3;
                    }
                    return ApiService.get(SensefySmartAutocompletePhase3, {}, {
                        entityTypeId: entityTypeId,
                        entityAttributeField: entityTypeAttribute,
                        termToComplete: termToComplete,
                        numberOfSuggestions: numberOfSuggestions
                    });
                }
            };
        }
    ]).factory('SemanticMoreLikeThisService', [
        'SensefyMlt', 'ApiService',
        function (SensefyMlt, ApiService) {
            return {
                getSimilarDocuments: function (docId, fields, rows) {
                    if (fields == null) {
                        fields = "*";
                    }
                    if (rows == null) {
                        rows = 10;
                    }
                    return ApiService.get(SensefyMlt, {}, {
                        docId: docId,
                        fields: fields,
                        rows: rows
                    });
                }
            };
        }
    ]).factory('ImageSearchService', [
        'SensefyImageSearchPath', 'ApiService',
        function (SensefyImageSearchPath, ApiService) {
            return {
                search: function (file, field) {
                    var formData, params;
                    if (field == null) {
                        field = null;
                    }
                    formData = new FormData();
                    formData.append("file", file);
                    params = {
                        token: 'QPKR9AA3TNQ_YnErAnTlsUzblIw3Uaz6OF8bu7Mn-EaouAdVLraf3rNnTbFDco6zZb8XkAkXT9SPRQLc2KvEcYqELM8NOFjYGQ9KzrYVzYsr1FymSC619Jt9Rx76Dkr9iqyIvEsk-r5GMfERJ-9uifXF8lkRUZthUoCSglBQhYk'
                    };
                    if (field !== null) {
                        params['field'] = field;
                    }
                    return ApiService.post(SensefyImageSearchPath, formData, params);
                }
            };
        }
    ]).factory('FileBrowserService', [
        '$http', 'ApiService',
        function($http, ApiService) {
            var promise = null;
            return {
                fileTree: function(){
                    if (promise) {
                        // If we've already asked for this data once,
                        // return the promise that already exists.
                        return promise;
                    } else {
                        promise = $http.get('hierarchy.json');
                        return promise;
                    }
                }
            };
        }
    ]);

}).call(this);