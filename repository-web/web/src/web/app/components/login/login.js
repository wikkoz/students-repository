(function () {
    'use strict';

    angular.module('projekt').directive('login', login);
    function login() {
        return {
            restrict: 'E',
            templateUrl: 'components/login/login.html',
            scope: {},
            controllerAs: 'ctrl',
            controller: loginCtrl
        };
    }

    /*@ngInject*/
    function loginCtrl($resource, $rootScope, $state, Notification) {
        var ctrl = this;
        var resource = $resource('', {}, {
            login: {method: 'POST', url: '/user/login'}
        });

        ctrl.model = {};
        ctrl.login = login;


        init();

        function init() {
            $rootScope.$on('$stateChangeSuccess', function (event, to, toParams, from, fromParams) {
                ctrl.previousState = from;
                ctrl.previousParmas = fromParams;
            });
        }

        function login() {
            resource.login(ctrl.model).$promise.then(function (response) {
                if (response.value) {
                    $state.go('/');
                    Notification.primary({message:"Pomyślnie zalogowano się do GitLab-a", positionY: 'bottom', positionX: 'right'});
                }
            });
        }
    }
})();