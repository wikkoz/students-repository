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
    function tableStudentCtrl($scope, $state, $resource, Notification) {
        var ctrl = this;
        var STATES = {
            'EMPTY': 'Wolny',
            'PENDING': 'Oczekujący',
            'FORMING': 'Tworzacy sie',
            'ACCEPTED': 'Zaakceptowany'
        };

        var resource = $resource('', {}, {
            getTeams: {method: 'GET', url: '/student/projects'},
            takeTeam: {params: {id: '@id'}, method: 'POST', url: '/student/team/:id/takeTeam'}
        });

        ctrl.click = click;
        ctrl.translate = translate;

        init();

        function init() {
            load();
            $scope.$on('student', load)
        }

        function load() {
            Notification({
                message: 'Wybierz temat, aby pokazać informacje o nim <br> Jeśli jakiś temat jest wolny to wejście w niego powoduje przejęcie go oraz usunięcie innych zaproszeń.',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            ctrl.model = resource.getTeams();
        }

        function translate(code) {
            return STATES[code];
        }

        function click(team) {
            if (team.state == 'EMPTY') {
                resource.takeTeam({id: team.id})
                    .$promise.then(function() {
                    $state.go('team', {teamId: team.id})
                });
            } else {
                $state.go('team', {teamId: team.id});
            }
        }
    }
})();