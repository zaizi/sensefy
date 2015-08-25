/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   main app js file to handle app
 */

(function () {
    angular.module('SensefySemanticSearch', [
        'SensefyConfiguration',
        'RootController',
        'SearchController',
        'LoginController',
        'SensefyDirectives',
        'SensefyFilters',
        'SensefyServices',
        'ngAnimate',
        'rzModule',
        'ngPDFViewer',
        'angularify.semantic.dropdown',
        'angularUtils.directives.dirPagination']).config([
        '$provide', function ($provide) {
            return $provide.decorator('pdfviewerDirective', [
                '$delegate', function ($delegate) {
                    if (($delegate != null ? $delegate.length : void 0) > 0) {
                        $delegate[0].scope.src = '=';
                    }
                    return $delegate;
                }
            ]);
        }
    ]);
}.call(this));