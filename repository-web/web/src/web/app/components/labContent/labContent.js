(function () {
    'use strict';

    angular.module('projekt').directive('labContent', labContent);
    function labContent() {
        return {
            restrict: 'E',
            templateUrl: 'components/labContent/labContent.html',
            bindToController: true,
            scope: {},
            controllerAs: 'ctrl',
            controller: labContentCtrl
        };
    }

    function labContentCtrl($resource, $stateParams, $window) {
        var ctrl = this;

        var BASE_URL = '/tutor';
        var resource = $resource('', {id: '@id'}, {
            data: {method: 'GET', url: BASE_URL + '/team/:id'},
            students: {method: 'GET', url: BASE_URL + '/team/:id/students', isArray: true},
            addStudent: {method: 'POST', url: BASE_URL + '/team/:id/addStudent'},
            changePoints: {method: 'POST', url: BASE_URL + '/team/:id/changePoints'}
        });

        ctrl.addStudent = addStudent;
        ctrl.savePoints = savePoints;
        ctrl.goGitlab = goGitlab;

        init();

        function init() {
            ctrl.newStudent = null;
            resource.data(getParams()).$promise.then(function (response) {
                ctrl.model = response;
            });
            resource.students(getParams()).$promise.then(function (response) {
                ctrl.students = response;
            })
        }

        function addStudent() {
            resource.addStudent(getParams(), ctrl.newStudent).$promise.then(function () {
                init();
            })
        }

        function getParams() {
            return {
                id: $stateParams.teamId
            }
        }

        function savePoints(form) {
            resource.changePoints(getParams(), {value: ctrl.model.points}).$promise.then(function () {
                init();
            });
            form.$setPristine();
        }

        function goGitlab() {
            $window.location.href = ctrl.model.gitlabPage;
        }
    }

})(); 