(function() {
    'use strict'

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
    function adminCtrl($resource) {
        var ctrl = this;

        var resource = $resource('', {}, {
            createUsers: {method: 'POST', url: '/admin/users'},
            createCourses: {method: 'POST', url: '/admin/courses'}
        });

        ctrl.model = {};
        ctrl.uploadUsers = uploadUsers;
        ctrl.uploadCourses = uploadCourses;

        function uploadUsers() {
            console.log(ctrl);
            resource.createUsers(ctrl.model.userFile);
        }

        function uploadCourses() {
            console.log(ctrl);
            resource.createCourses(ctrl.model.courseFile);
        }
    }
})();
