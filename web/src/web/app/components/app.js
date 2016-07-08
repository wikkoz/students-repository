(function (){
	'use strict';

	angular.module('projekt',[
		'ngAnimate',
		'ui.bootstrap',
		'ui.router',
		'angularFileUpload'
	]);
	angular.module('projekt')
		.config(config)
		.run(run);

	/*@ngInject*/
	function run($rootScope, $state, $stateParams) {
		$rootScope.appCode = 'projekt';
		$rootScope.$state = $state;
		$rootScope.$stateParams = $stateParams;
	}

	/*@ngInject*/
	function config($stateProvider, $httpProvider, $urlRouterProvider) {
		$stateProvider
			.state('/', {
				url: '/',
				templateUrl: 'components/view/tabView.html'
			})
			.state('student', {
				url: '/student',
				templateUrl: 'components/view/studentView.html'
			})
			.state('newteam', {
				url: '/newteam',
				templateUrl: 'components/view/newTeamView.html'
			})
			.state('project', {
				url: '/project',
				templateUrl: 'components/view/projectView.html'
			})
			.state('newproject', {
				url: '/newproject',
				templateUrl: 'components/view/newProjectView.html'
			});
		$urlRouterProvider.otherwise('/');
	}
}());