(function () {
    'use strict'

    angular.module('projekt').directive('tableLab', tableLab);
    function tableLab() {
        return {
            restrict: 'E',
            templateUrl: 'components/tableLab/tableLab.html',
            scope: {
                model: '=',
                number: '@'
            },
            controllerAs: 'ctrl',
            controller: tableLabCtrl
        };
    }

    /*@ngInject*/
    function tableLabCtrl($scope, $state, $resource) {
        var ctrl = this;

        var BASE_URL = '/project';
        var resource = $resource('', {}, {
            projects: {method: 'GET', url: BASE_URL + '/', isArray: true}
        });
        
        ctrl.click = click;
        ctrl.newTeam = newTeam;
        ctrl.model = [
            {
                'course': 'PIK',
                'name': "Projekt",
                'date': '11-11-2016'
            },
            {
                'course': 'PIK',
                'name': "Projekt",
                'date': '11-11-2016'
            }
        ];
        
        init();
        
        function init() {
            $scope.$on('lab', load)
        }

        function load() {

        }

        function click() {
            $state.go('project', {teamId: 1});
        }

        function newTeam() {
            $state.go('newteam', {teamId: 1});
        }
    }
})();