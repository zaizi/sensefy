angular.module('navigation', ['ngRoute', 'auth']).controller(
		'navigation',

		function($scope, $route, auth) {

			$scope.credentials = {};

			$scope.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			$scope.authenticated = function() {
				return auth.authenticated;
			}

			$scope.login = function() {
				auth.authenticate($scope.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded - login page");
						$scope.error = false;
					} else {
						console.log("Login failed - login page");
						$scope.error = true;
					}
				})
			};

			$scope.logout = auth.clear;

		});
