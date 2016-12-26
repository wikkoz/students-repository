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
    function studentContentCtrl($state, $stateParams, $resource, Notification, $window) {
        var ctrl = this;

        var BASE_URL = '/student';
        var resource = $resource('', {}, {
            data: {method: 'GET', url: BASE_URL + '/team/:id'},
            availableStudents: {method: 'GET', url: BASE_URL + '/team/:id/students', isArray: true},
            topics: {method: 'GET', url: BASE_URL + '/team/:id/topics', isArray: true},
            addStudent: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/add'},
            deleteStudent: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/delete'},
            saveTopic: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/chooseTopic'},
            saveDescription: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/description'},
            acceptTeam: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/accept'},
            joinToTeam: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/acceptRequest'},
            rejectRequest: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/team/:id/rejectRequest'}
        });

        ctrl.model = {};
        ctrl.students = {};
        ctrl.topics = {};

        ctrl.addStudent = addStudent;
        ctrl.acceptTeam = acceptTeam;
        ctrl.deleteStudent = deleteStudent;
        ctrl.saveTopic = saveTopic;
        ctrl.showAcceptedTeam = showAcceptedTeam;
        ctrl.showLeaderOption = showLeaderOption;
        ctrl.showJoinToTeam = showJoinToTeam;
        ctrl.showFormingTeam = showFormingTeam;
        ctrl.showNewStudent = showNewStudent;
        ctrl.showSendButton = showSendButton;
        ctrl.acceptRequest = acceptRequest;
        ctrl.rejectRequest = rejectRequest;
        ctrl.saveDescription = saveDescription;
        ctrl.updateDescription = updateDescription;
        ctrl.showStudentsStates = showStudentsStates;
        ctrl.goGitlab = goGitlab;

        init();

        function init() {
            Notification({
                message: 'Informacje na temat zespołu <br> Liderzy grup mogą dodawać użytkowników oraz zmieniać temat. <br>' +
                'Każda dodana osoba musi zaakceptować zaproszenie. <br> Zespół jest automatycznie założny, <br>' +
                'jeśli temat i opis jest identyczny z zaproponowanym oraz liczba osób w zespole jest odpowiednia.',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            reload();
        }

        function reload() {
            resource.data(getParams()).$promise.then(function (response) {
                ctrl.model = response;
            });
            resource.availableStudents(getParams()).$promise.then(function (response) {
                ctrl.students = response;
            });
            resource.topics(getParams()).$promise.then(function (response) {
                ctrl.topics = response;
            });
        }

        function acceptTeam() {
            var topic = {
                topic: ctrl.model.topic,
                description: ctrl.model.description
            };
            resource.acceptTeam(getParams(), topic).$promise.then(function () {
                reload();
            });
        }

        function acceptRequest() {
            resource.joinToTeam(getParams()).$promise.then(function () {
                reload();
            });
        }

        function rejectRequest() {
            resource.rejectRequest(getParams()).$promise.then(function () {
                reload();
            });
        }

        function addStudent() {
            if (!_.isEmpty(ctrl.newPerson)) {
                resource.addStudent(getParams(), ctrl.newPerson).$promise.then(function () {
                    ctrl.newPerson = null;
                    reload();
                });
            }
        }

        function deleteStudent(student) {
            resource.deleteStudent(getParams(), student).$promise.then(function (data) {
                if (data.selfRemove) {
                    $state.go('/')
                } else {
                    reload();
                }
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

        function showStudentsStates() {
            return ctrl.model.teamState == 'FORMING';
        }

        function showNewStudent() {
            return ctrl.model.students.length < ctrl.model.maxNumberOfStudents;
        }

        function saveTopic(form) {
            resource.saveTopic(getParams(), {value: ctrl.model.topic});
            form.$setPristine();
        }

        function saveDescription(form) {
            resource.saveDescription(getParams(), {value: ctrl.model.description});
            form.$setPristine();
        }

        function showSendButton() {
            return showLeaderOption()
                && !_.isEmpty(ctrl.model.topic)
                && ctrl.model.canBeAccepted;
        }

        function getParams() {
            return {
                id: $stateParams.teamId
            }
        }

        function updateDescription() {
            var topic = _.find(ctrl.topics, ['topic', ctrl.model.topic]);
            if (topic) {
                ctrl.model.description = topic.description;
                resource.saveDescription(getParams(), {value: ctrl.model.description});
            }
        }

        function goGitlab() {
            $window.location.href = ctrl.model.gitlabPage;
        }
    }

})(); 