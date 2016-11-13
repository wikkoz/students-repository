(function () {
    'use strict';

    angular.module('projekt').directive('tableSubject', tableSubject);
    function tableSubject() {
        return {
            restrict: 'E',
            templateUrl: 'components/tableSubject/tableSubject.html',
            scope: {
                subject: '='
            },
            controllerAs: 'ctrl',
            bindToController: true,
            controller: tableSubjectCtrl
        };
    }

    /*@ngInject*/
    function tableSubjectCtrl($scope, $state, $resource) {
        var ctrl = this;

        var STATES = {
            'EMPTY': 'Wolny',
            'PENDING': 'Oczekujący',
            'FORMING': 'Tworzacy sie',
            'ACCPETED': 'Zaakceptowany'
        };
        
        var BASE_URL = '/project';
        var resource = $resource('', {}, {
            projects: {method: 'POST', url: BASE_URL + '/teams', isArray: true}
        });

        ctrl.newProject = true;
        ctrl.model = {};

        ctrl.click = click;
        ctrl.translate = translate;

        init();

        function init() {
            $scope.$watch('ctrl.subject', updateModel);
            $scope.$on('subject', updateModel)
        }

        function updateModel() {
            if(_.isUndefined(ctrl.subject))
                return;
            console.log(ctrl.subject);
            resource.projects({value: ctrl.subject.id}).$promise.then(function (response) {
                ctrl.model = response;
                ctrl.newProject = _.isEmpty(ctrl.model);
                console.log(response)
            })
        }

        function click() {
            console.log(ctrl);
            $state.go('newproject', {courseId: ctrl.subject.id});
        }

        function translate(code) {
            return STATES[code];
        }

    }
})();