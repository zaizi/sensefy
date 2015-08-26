angular
		.module('sensefyApp', [ 'ngRoute', 'auth', 'home', 'search', 'navigation' ])
		.config(

				function($routeProvider, $httpProvider, $locationProvider) {

					$locationProvider.html5Mode(true);

					$routeProvider.when('/', {
						templateUrl : 'scripts/home/home.html',
						controller : 'home'
					}).when('/search', {
						templateUrl : 'scripts/search/search.html',
						controller : 'search'
					}).when('/login', {
						templateUrl : 'scripts/login/login.html',
						controller : 'navigation'
					}).otherwise('/');

					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

				}).run(function(auth) {

			// Initialize auth module with the home page and login/logout path
			// respectively
			auth.init('/', '/login', '/logout');

		});
