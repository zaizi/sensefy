/*
 #   by Sameera @ zaizi.com
 #   for Sensefy Search
 #   main app js file to handle app
 */

(function () {
    angular.module('SensefySemanticSearch', [
        'SensefyConfiguration',
        'SensefyControllers',
        'SensefyDirectives',
        'SensefyFilters',
        'SensefyServices',
        'ngAnimate',
        //'ngPDFViewer',
        'angularUtils.directives.dirPagination',
        'angularMoment',
        'auth'

        //,
        //'ui.bootstrap',
        //'rzModule',
        //'ngPDFViewer',
        //'infinite-scroll'
    ]).config([
        '$provide', function ($provide) {
            /*return $provide.decorator('pdfviewerDirective', [
                '$delegate', function ($delegate) {
                    if (($delegate != null ? $delegate.length : void 0) > 0) {
                        $delegate[0].scope.src = '=';
                    }
                    return $delegate;
                }
            ]);*/
        }
    ]);

}).call(this);