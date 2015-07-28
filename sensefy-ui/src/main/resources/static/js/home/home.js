angular.module('home', []).controller('home', function($scope, $http) {

    $scope.authenticated = function() {
        return auth.authenticated;
    }

    if($scope.authenticated) {
        $http.get('/resource/').success(function (data) {
            $scope.greeting = data;
        });
    }
    else{
    	
       // $location.path('/login');
        $location.path(auth.loginPath);
    }
    
});
