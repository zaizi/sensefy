angular.module('message', ['ngRoute', 'auth']).controller('message', function($scope, $http) {

    
    
    if($scope.authenticated) {
        $http.get('/resource/').success(function (data) {
            $scope.greeting = data;
        });
    }
    else{
    	$location.path(auth.loginPath);
        //$location.path('/login');
    }
	
	
	$http.get('/user/').success(function(data) {
		$scope.user = data.name;
	});
});
