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
    function tabCtrl($scope) {
        var ctrl = this;

        ctrl.reload = reload;

        function reload(action) {
            $scope.$broadcast(action);
        }
    }
})();