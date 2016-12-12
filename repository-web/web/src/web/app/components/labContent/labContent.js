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

    function labContentCtrl($resource, $stateParams) {
        var ctrl = this;

        var BASE_URL = '/tutor';
        var resource = $resource('', {id: '@id'},  {
            data: {method: 'GET', url: BASE_URL + '/team/:id'},
            students: {method: 'GET', url: BASE_URL + '/team/:id/students', isArray: true},
            addStudent: {method: 'POST', url: BASE_URL + '/team/:id/addStudent'}
        });

        ctrl.addStudent = addStudent;

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
            resource.addStudent(getParams(), ctrl.newStudent).$promise.then(function (){
                init();
            })
        }

        function getParams() {
            return {
                id: $stateParams.teamId
            }
        }
    }

})(); 