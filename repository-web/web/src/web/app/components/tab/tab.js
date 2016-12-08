(function () {
    'use strict';

    angular.module('projekt').directive('tab', tab);
    function tab() {
        return {
            restrict: 'E',
            templateUrl: 'components/tab/tab.html',
            scope: {},
            controllerAs: 'ctrl',
            controller: tabCtrl
        };
    }

    /*@ngInject*/
    function tabCtrl($scope, AuthenticationService) {
        var ctrl = this;

        ctrl.reload = reload;
        ctrl.hasRole = hasRole;

        function reload(action) {
            $scope.$broadcast(action);
        }

        function hasRole(role){
            return true;
        }
    }
})();