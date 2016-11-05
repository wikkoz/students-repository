(function () {
    'use strict';

    angular.module('projekt').directive('tableStudent', tableStudent);
    function tableStudent() {
        return {
            restrict: 'E',
            templateUrl: 'components/tableStudent/tableStudent.html',
            scope: {
                model: '=',
                number: '@'
            },
            controllerAs: 'ctrl',
            controller: tableStudentCtrl
        };
    }

    /*@ngInject*/
    function tableStudentCtrl($state, $resource) {
        var ctrl = this;
        var STATES = {
            'EMPTY': 'Wolny',
            'PENDING': 'Oczekujący',
            'FORMING': 'Tworzacy sie',
            'ACCPETED': 'Zaakceptowany'
        };

        var resource = $resource('', {}, {
            getTeams: {method: 'GET', url: '/student/projects'},
            takeTeam: {params: {id: '@id'}, method: 'POST', url: '/student/team/:id/takeTeam'}
        });

        ctrl.click = click;
        ctrl.translate = translate;

        init();

        function init() {
            ctrl.model = resource.getTeams();
            console.log(ctrl);
        }

        function translate(code) {
            return STATES[code];
        }

        function click(team) {
            if (team.state == 'EMPTY') {
                resource.takeTeam({id: team.id})
                    .$promise.then($state.go('team', {teamId: team.id}));
            } else {
                $state.go('team', {teamId: team.id});
            }
        }
    }
})();