(function() {
    'use strict';

    angular.module('projekt').directive('admin', admin);

    function admin() {
        return {
            restrict: 'E',
            templateUrl: 'components/admin/admin.html',
            bindToController: true,
            scope: {},
            controllerAs: 'ctrl',
            controller: adminCtrl
        };
    }

    /*@ngInject*/
    function adminCtrl($resource, $state, Notification, GitlabService) {
        var ctrl = this;

        var resource = $resource('', {}, {
            createUsers: {method: 'POST', url: '/admin/users'},
            createCourses: {method: 'POST', url: '/admin/courses'}
        });

        ctrl.model = {};
        ctrl.uploadUsers = uploadUsers;
        ctrl.uploadCourses = uploadCourses;

        function uploadUsers() {
            var success = function () {
                resource.createUsers(ctrl.model.userFile).$promise.then(function () {
                    Notification.info({message: "Dodano nowych użytkowników"})
                });
            };

            GitlabService.login(success, failure);
        }

        function uploadCourses() {
            var success = function () {
                resource.createCourses(ctrl.model.courseFile).$promise.then(function () {
                    Notification.info({message: "Dodano nowe przedmioty"})
                });
            };

            GitlabService.login(success, failure);
        }

        function failure() {
            $state.go('login');
        }
    }
})();
