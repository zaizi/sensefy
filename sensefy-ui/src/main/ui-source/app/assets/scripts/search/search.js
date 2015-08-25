angular.module('search', ['ngRoute', 'auth']).controller('search', ['$scope', '$http', '$location', 'auth',
    function($scope, $http, $location, auth) {
        angular.element('.ui.dropdown.top-right-menu').dropdown();

        $scope.authenticated = function() {
            console.log('authenticated');
            return auth.authenticated;
        };

        $scope.logout = function(){
            auth.clear;
            console.log('logout fired');
            $location.path('/login');
        };

        if($scope.authenticated) {
            $http.get('/resource/').success(function (data) {
                $scope.greeting = data;
            });
            var myVar;

            myVar = setTimeout(function(){
                angular.element('body').addClass('ggl');
                console.log('Timeout id fired');
                clearTimeout(myVar);
            }, 20);

        }
        else{
            $location.path('/login');
        }
    }
]);
