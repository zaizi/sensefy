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