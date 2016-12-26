(function () {
    'use strict'

    angular.module('projekt').directive('header', header);
    function header() {
        return {
            restrict: 'E',
            templateUrl: 'components/header/header.html',
            scope: {
                model: '='
            },
            controllerAs: 'ctrl',
            controller: headerCtrl
        };
    }

    function headerCtrl($state) {
        var ctrl = this;
        ctrl.click = click;
        ctrl.model = {
            'name': 'Jan',
            'lastName': 'Kowalski'
        }

        function click() {
            $state.go('/');
        }
    }
})();