(function () {
    'use strict';

    angular.module('projekt').directive('sideLab', sideLab);
    function sideLab() {
        return {
            restrict: 'E',
            templateUrl: 'components/sideLab/sideLab.html',
            scope: {},
            controllerAs: 'ctrl',
            controller: sideLabCtrl
        };
    }

    function sideLabCtrl($resource, $scope) {
        var ctrl = this;

        var BASE_URL = '/tutor';
        var resource = $resource('', {}, {
            records: {method: 'GET', url: BASE_URL + '/records', isArray: true}
        });

        init();

        function init() {
            load();
            $scope.$on('lab', load)
        }

        function load() {
            resource.records().$promise.then(function (data) {
                ctrl.model = data;
            });
        }
    }
})();