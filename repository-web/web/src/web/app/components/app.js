(function (){
	'use strict';

	angular.module('projekt',[
		'ngAnimate',
		'ui.bootstrap',
		'ui.router',
		'angularFileUpload',
		'ngResource',
		'naif.base64'
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
			.state('team', {
				url: '/team/{teamId:int}',
				templateUrl: 'components/view/studentView.html'
			})
			.state('newteam', {
				url: '/newteam/',
				templateUrl: 'components/view/newTeamView.html'
			})
			.state('project', {
				url: '/project/{teamId:int}',
				templateUrl: 'components/view/projectView.html'
			})
			.state('newproject', {
				url: '/newproject/{courseId:int}',
				templateUrl: 'components/view/newProjectView.html'
			});
		$urlRouterProvider.otherwise('/');
	}
}());