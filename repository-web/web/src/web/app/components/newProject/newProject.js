(function() {
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

    function newProjectCtrl() {
        var ctrl = this;
        ctrl.uploader = new FileUploader()
    }
})();
