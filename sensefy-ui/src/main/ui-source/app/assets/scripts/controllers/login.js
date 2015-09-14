(function() {
    'use strict';
    var controllersModule;

    controllersModule = angular.module('LoginController', ['pascalprecht.translate', 'tmh.dynamicLocale', 'LocalStorageModule']).config([
        'tmhDynamicLocaleProvider', function (tmhDynamicLocaleProvider) {
            return tmhDynamicLocaleProvider.localeLocationPattern('//code.angularjs.org/1.3.15/i18n/angular-locale_{{locale}}.js');
        }
    ]).config([
        'localStorageServiceProvider', function (localStorageServiceProvider) {
            return localStorageServiceProvider.setPrefix('sensefy');
        }
    ]).controller('LoginController', [
        '$scope', 'LoginService', 'localStorageService',
        function ($scope, LoginService, localStorageService) {
            $scope.userform = {};
            $scope.loginError = false;
            $scope.doingLogin = false;
            $scope.timezone = jstz.determine().name();
            $scope.isCaptcha = false;
            $scope.captchaImgSrc = '';
            angular.element('#username').focus();
            return $scope.doLogin = function () {
                if (!$scope.userform.username || $scope.userform.username === null || $scope.userform.username === '' || !$scope.userform.password || $scope.userform.password === null || $scope.userform.password === '') {
                    $scope.errors['login'] = true;
                    $scope.errors['loginMessage'] = 'errorBadUsernameOrPassword';
                    if ($scope.userform.captcha === void 0 && $scope.isCaptcha) {
                        $scope.errors['loginCaptcha'] = true;
                        return $scope.errors['loginMessageCaptcha'] = 'errorBadUsernameOrPasswordOrCaptcha';
                    } else {
                        return $scope.errors['loginCaptcha'] = false;
                    }
                } else {
                    $scope.doingLogin = true;
                    if ($scope.userform.captcha === void 0 && $scope.isCaptcha) {
                        $scope.errors['loginCaptcha'] = true;
                        $scope.errors['loginMessageCaptcha'] = 'errorBadUsernameOrPasswordOrCaptcha';
                        $scope.errors['login'] = false;
                        return false;
                    } else {
                        $scope.errors['loginCaptcha'] = false;
                    }
                    return LoginService.login($scope.userform.username, $scope.userform.password, {
                        timezone: $scope.timezone,
                        captcha: $scope.userform.captcha
                    }).then(function (response) {
                        $scope.doingLogin = false;
                        if (response.data.captchaImage === null && response.data.error === null && response.data.token.value !== '') {
                            $scope.isCaptcha = false;
                            $scope.captchaImgSrc = '';
                            $scope.errors['login'] = false;
                            localStorageService.set('token', response.data.token.value);
                            localStorageService.set('user', $scope.userform.username);
                            return $scope.$emit('userLogged', {
                                user: localStorageService.get('user'),
                                token: localStorageService.get('token')
                            });
                        } else if (response.data.captchaImage === null && response.data.error.msg !== null && response.data.token === null) {
                            $scope.errors['login'] = true;
                            $scope.errors['loginMessage'] = response.data.error.msg;
                            $scope.isCaptcha = false;
                            $scope.errors['loginCatcha'] = false;
                            return $scope.captchaImgSrc = '';
                        } else if (response.data.captchaImage !== null && response.data.error.msg !== null && response.data.token === null) {
                            $scope.errors['login'] = true;
                            $scope.errors['loginMessage'] = response.data.error.msg;
                            $scope.errors['loginCatcha'] = false;
                            $scope.isCaptcha = true;
                            return $scope.captchaImgSrc = response.data.captchaImage;
                        }
                    }, function (response) {
                        $scope.doingLogin = false;
                        $scope.errors['login'] = true;
                        if (response.status !== 200) {
                            return $scope.errors['loginMessage'] = 'errorAPINotAvailable';
                        }
                    });
                }
            };
        }
    ]);

}).call(this);