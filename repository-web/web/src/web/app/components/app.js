(function () {
    'use strict';

    angular.module('projekt', [
        'ngAnimate',
        'ui.bootstrap',
        'ui.router',
        'ngResource',
        'naif.base64',
        'ui-notification'
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

    function notificationConfig(NotificationProvider) {
        NotificationProvider.setOptions({
            delay: 2000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'right',
            positionY: 'bottom'
        });
    }

    /*@ngInject*/
    function config($stateProvider, $httpProvider, $urlRouterProvider, NotificationProvider) {
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
            })
            .state('login', {
                url: '/login',
                templateUrl: 'components/view/loginPageView.html'
            });
        $urlRouterProvider.otherwise('/');
        notificationConfig(NotificationProvider);
        $httpProvider.interceptors.push('ErrorHandlingInterceptor');
    }
}());