(function () {
    'use strict';

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
    function tableLabCtrl($scope, $state, $resource, Notification) {
        var ctrl = this;

        var BASE_URL = '/tutor';
        var resource = $resource('', {}, {
            projects: {method: 'GET', url: BASE_URL + '/project', isArray: true}
        });
        
        ctrl.click = click;
        ctrl.newTeam = newTeam;

        init();
        
        function init() {
            load();
            $scope.$on('lab', load)
        }

        function load() {
            Notification({
                message: 'Wybierz temat, aby pokazać informacje o nim <br> Jeśli jakiś temat jest wolny to wejście w niego powoduje przejęcie go oraz usunięcie innych zaproszeń.',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            resource.projects().$promise.then(function (data) {
                ctrl.model = data;
            })
        }

        function click(id) {
            $state.go('project', {teamId: id});
        }

        function newTeam() {
            $state.go('newteam');
        }
    }
})();