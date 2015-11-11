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
angular.module('auth', []).factory('auth',
    function ($rootScope, $http, $location) {

        var enter = function () {
            if ($location.path() !== auth.loginPath) {
                auth.path = $location.path();
                if (!auth.authenticated) {
                    $location.path(auth.loginPath);
                }
            }
        };

        var auth = {

            authenticated: false,

            loginPath: '/login',
            logoutPath: '/logout',
            homePath: '/',
            searchPath: '/search',
            path: $location.path(),

            authenticate: function (credentials, callback) {
                var headers = credentials && credentials.username ? {
                    authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
                } : {};
                auth.authenticated = true;
                $location.path(auth.path === auth.loginPath ? auth.homePath : auth.searchPath);
            },

            clear: function () {
                $location.path(auth.loginPath);
                auth.authenticated = false;
                $http.post(auth.logoutPath, {}).success(function () {
                    //  console.log("Logout succeeded");
                }).error(function (data) {
                    //  console.log("Logout failed");
                });
            },

            init: function (homePath, loginPath, logoutPath) {

                auth.homePath = homePath;
                auth.loginPath = loginPath;
                auth.logoutPath = logoutPath;

                auth.authenticate({}, function (authenticated) {
                    if (authenticated) {
                        $location.path(auth.path);
                    }
                });

                // Guard route changes and switch to login page if unauthenticated
                $rootScope.$on('$routeChangeStart', function () {
                    enter();
                });

            }

        };

        return auth;

    });