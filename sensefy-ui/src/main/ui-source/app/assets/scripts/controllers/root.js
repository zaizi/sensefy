(function() {
    'use strict';
    var controllersModule = angular.module('RootController', ['pascalprecht.translate', 'tmh.dynamicLocale', 'LocalStorageModule']).config([
        'tmhDynamicLocaleProvider', function (tmhDynamicLocaleProvider) {
            return tmhDynamicLocaleProvider.localeLocationPattern('//code.angularjs.org/1.3.15/i18n/angular-locale_{{locale}}.js');
        }
    ]).config([
        'localStorageServiceProvider', function (localStorageServiceProvider) {
            return localStorageServiceProvider.setPrefix('sensefy');
        }
    ]).controller('RootController', [
        '$injector', '$scope', '$location', '$window', '$rootScope', '$translate', 'tmhDynamicLocale', 'localStorageService',
        function ($injector, $scope, $location, $window, $rootScope, $translate, tmhDynamicLocale, localStorageService) {
            $scope.errors = {};
            tmhDynamicLocale.set('en-us');
            $scope.$on('userLogged', function (event, data) {
                $rootScope.user = {
                    username: data.user,
                    token: data.token
                };
                //  console.log('$rootScope.user '+ JSON.stringify($rootScope.user));
                controllersModule.value("token", data.token);
                $location.path("/");
                return $location.search($location.search());
            });
            $scope.currentLanguage = 'English';
            $scope.changeLanguage = function (lang) {
                if (lang == null) {
                    lang = 'en-us';
                }
                switch (lang) {
                    case 'en-us':
                        $scope.currentLanguage = 'English';
                        break;
                    case 'es-es':
                        $scope.currentLanguage = 'Español';
                        break;
                    default:
                        $scope.currentLanguage = 'English';
                }
                tmhDynamicLocale.set(lang);
                return $translate.use(lang);
            };
            if (localStorageService.get('token') !== null && localStorageService.get('user') !== null) {
                return $scope.$emit('userLogged', {
                    user: localStorageService.get('user'),
                    token: localStorageService.get('token')
                });
            }
        }
    ]);
}).call(this);