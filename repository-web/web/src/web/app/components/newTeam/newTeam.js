(function () {
    'use strict';

    angular.module('projekt').directive('newTeam', newTeam);
    function newTeam() {
        return {
            restrict: 'E',
            templateUrl: 'components/newTeam/newTeam.html',
            bindToController: true,
            scope: {},
            controllerAs: 'ctrl',
            controller: newTeamCtrl
        };
    }

    function newTeamCtrl($resource, $state, Notification, GitlabService) {

        var BASE_URL = '/tutor';
        var resource = $resource('', {}, {
            teams: {method: 'GET', url: BASE_URL + '/newTeams', isArray: true},
            accept: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/acceptTeam/:id'},
            isLogged: {method: 'GET', url: '/user/logged'},
            changeTopic: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/changeTopic'},
            changeDescription: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/changeDescription'}

        });

        var ctrl = this;

        ctrl.acceptTeam = acceptTeam;
        ctrl.changeTopic = changeTopic;
        ctrl.changeDescription = changeDescription;

        init();

        function init() {
            Notification({
                message: 'Wybierz temat, aby pokazać informacje o nim <br> Jeśli jakiś temat jest wolny to wejście w niego powoduje przejęcie go oraz usunięcie innych zaproszeń.',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            resource.teams().$promise.then(function (result) {
                ctrl.model = result;
                ctrl.team = result[0];
            });
        }

        function changeTopic(form) {
            resource.changeTopic({id: ctrl.team.id}, ctrl.team.topic);
            form.$setPristine();
        }

        function changeDescription(form) {
            resource.changeDescription({id: ctrl.team.id}, ctrl.team.description);
            form.$setPristine();
        }

        function acceptTeam() {
            var success = function () {
                resource.accept({id: ctrl.team.id}).$promise.then(function () {
                    Notification.info({message: "Pomyślnie dodano stworzono zespół"});
                    $state.go('/')
                });
            };
            var failure = function () {
                $state.go('login');
            };

            GitlabService.login(success, failure);
        }
    }
})(); 