(function () {
    'use strict';

    angular.module('projekt').directive('studentContent', studentContent);
    function studentContent() {
        return {
            restrict: 'E',
            templateUrl: 'components/studentContent/studentContent.html',
            bindToController: true,
            scope: {},
            controllerAs: 'ctrl',
            controller: studentContentCtrl
        };
    }

    /*ngInject*/
    function studentContentCtrl($stateParams, $resource) {
        var ctrl = this;
        console.log($stateParams);
        var BASE_URL = '/student';
        var resource = $resource('', {}, {
            data: {method: 'GET', url: BASE_URL + '/team/:id'},
            availableStudents: {method: 'GET', url: BASE_URL + '/team/:id/students', isArray: true},
            topics: {method: 'GET', url: BASE_URL + '/team/:id/topics', isArray: true},
            addStudent: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/add'},
            deleteStudent: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/delete'}
        });

        ctrl.model = {};
        ctrl.students = {};
        ctrl.topics = {};

        ctrl.addStudent = addStudent;
        ctrl.deleteStudent = deleteStudent;
        ctrl.showAcceptedTeam = showAcceptedTeam;
        ctrl.showLeaderOption = showLeaderOption;
        ctrl.showJoinToTeam = showJoinToTeam;
        ctrl.showFormingTeam = showFormingTeam;
        ctrl.showNewStudent = showNewStudent;
        ctrl.showSendButton = showSendButton;

        init();

        function init() {
            resource.data(getParams()).$promise.then(function (response) {
                ctrl.model = response;
            });
            resource.availableStudents(getParams()).$promise.then(function (response) {
                ctrl.students = response;
            });
            resource.topics(getParams()).$promise.then(function (response) {
                ctrl.students = response;
            });
        }
        
        function addStudent() {
            if(!_.isEmpty(ctrl.newPerson)) {
                resource.addStudent(getParams(), ctrl.newPerson).$promise.then(function() {
                    init();
                });
            }
        }

        function deleteStudent(student) {
            resource.deleteStudent(getParams(), student).$promise.then(function() {
                init();
            });
        }
        
        function showAcceptedTeam() {
            return ctrl.model.teamState == 'ACCEPTED';
        }

        function showLeaderOption() {
            return ctrl.model.leader && ctrl.model.teamState == 'FORMING';
        }

        function showJoinToTeam() {
            return !ctrl.model.confirmedUser;
        }

        function showFormingTeam() {
            return ctrl.model.teamState == 'FORMING' || ctrl.model.teamState == 'PENDING';
        }

        function showNewStudent() {
            return ctrl.model.students.length < ctrl.model.numberOfStudents;
        }

        function showSendButton() {
            return showLeaderOption()
                && ctrl.model.students.length === ctrl.model.numberOfStudents
                && !_.isEmpty(ctrl.model.topic);
        }
        
        function getParams(){
            return {
                id: $stateParams.teamId
            }
        }
    }

})(); 