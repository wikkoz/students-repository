(function () {
    'use strict'

    angular.module('projekt').directive('newProject', newProject);

    function newProject() {
        return {
            restrict: 'E',
            templateUrl: 'components/newProject/newProject.html',
            bindToController: true,
            scope: {},
            controllerAs: 'ctrl',
            controller: newProjectCtrl
        };
    }

    /*ngInject*/
    function newProjectCtrl($resource, $stateParams) {
        var ctrl = this;
        var resource = $resource('', {}, {
            data: {method: 'POST', url: '/project/createUsers/dupa'}
        });

        ctrl.model = {};
        ctrl.upload = upload;

        function upload() {
            console.log(ctrl);
            resource.data(ctrl.model)
        }

        function getParams() {
            return {
                id: $stateParams.id
            }
        }
    }
})();
