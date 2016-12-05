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

    function newTeamCtrl($resource) {

        var BASE_URL = '/tutor';
        var resource = $resource('', {}, {
            teams: {method: 'GET', url: BASE_URL + '/newTeams', isArray: true},
            accept: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/acceptTeam/:id'}
        });

        var ctrl = this;

        ctrl.acceptTeam = acceptTeam;

        init();

        function init() {
            resource.teams().$promise.then(function (result) {
                ctrl.model = result;
                ctrl.team = result[0];
            });
        }

        function acceptTeam() {
            resource.accept({id: ctrl.team.id});
        }
    }
})(); 