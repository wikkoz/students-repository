(function () {
    'use strict';

    angular.module('projekt').directive('tableSubject', tableSubject);
    function tableSubject() {
        return {
            restrict: 'E',
            templateUrl: 'components/tableSubject/tableSubject.html',
            scope: {
                subject: '=',
                addresses: '='
            },
            controllerAs: 'ctrl',
            bindToController: true,
            controller: tableSubjectCtrl
        };
    }

    /*@ngInject*/
    function tableSubjectCtrl($scope, $state, $resource, Notification) {
        var ctrl = this;

        var STATES = {
            'EMPTY': 'Wolny',
            'PENDING': 'Oczekujący',
            'FORMING': 'Tworzacy sie',
            'ACCEPTED': 'Zaakceptowany',
            'ENDED': 'Zakończony'
        };
        
        var BASE_URL = '/project';
        var resource = $resource('', {}, {
            projects: {method: 'POST', url: BASE_URL + '/teams'}
        });

        ctrl.newProject = true;
        ctrl.model = {};

        ctrl.click = click;
        ctrl.gotoTeam = gotoTeam;
        ctrl.translate = translate;
        ctrl.canCreateNewProject = canCreateNewProject;

        init();

        function init() {
            $scope.$watch('ctrl.subject', updateModel);
            $scope.$on('subject', reload);
        }

        function reload() {
            Notification({
                message: 'Jakies tam dlugie info co trzeba zrobic na tej stronie itd <br> Jakies tam dlugie info co trzeba zrobic na tej stronie itd',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            updateModel();
        }

        function updateModel() {
            if(_.isUndefined(ctrl.subject))
                return;

            resource.projects({value: ctrl.subject.id}).$promise.then(function (response) {
                ctrl.model = response.teams;
                ctrl.newProject = _.isEmpty(ctrl.model);
                ctrl.addresses = response.mailAddresses;
            })
        }

        function click() {
            $state.go('newproject', {courseId: ctrl.subject.id});
        }

        function translate(code) {
            return STATES[code];
        }
        
        function gotoTeam(row) {
            if(row.status == 'ACCEPTED') {
                $state.go('project', {teamId: row.id});
            }
        }

        function canCreateNewProject() {
            return _.isEmpty(ctrl.model) || _.findIndex(ctrl.model, function (t) {
                return t.status !== 'ENDED'
            }) === -1;
        }
    }
})();