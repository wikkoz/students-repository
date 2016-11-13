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
    function adminCtrl($resource, $state) {
        var ctrl = this;

        var resource = $resource('', {}, {
            createUsers: {method: 'POST', url: '/admin/users'},
            createCourses: {method: 'POST', url: '/admin/courses'},
            isLogged: {method: 'GET', url: '/user/logged'}
        });

        ctrl.model = {};
        ctrl.uploadUsers = uploadUsers;
        ctrl.uploadCourses = uploadCourses;

        function uploadUsers() {
            resource.isLogged().$promise.then(function (response) {
                if(response.value){
                    resource.createUsers(ctrl.model.userFile);
                } else {
                    $state.go('login');
                }
            });
        }

        function uploadCourses() {
            resource.isLogged().$promise.then(function (response) {
                if(response.value){
                    resource.createCourses(ctrl.model.courseFile);
                } else {
                    $state.go('login');
                }
            });
        }
    }
})();
