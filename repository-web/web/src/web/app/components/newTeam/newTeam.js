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
            changeTopic: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/changeTopic'}

        });

        var ctrl = this;

        ctrl.acceptTeam = acceptTeam;
        ctrl.changeTopic = changeTopic;

        init();

        function init() {
            resource.teams().$promise.then(function (result) {
                ctrl.model = result;
                ctrl.team = result[0];
            });
        }

        function changeTopic() {
            resource.changeTopic({id: ctrl.team.id}, ctrl.team.topic);
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