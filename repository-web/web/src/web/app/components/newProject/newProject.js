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
            data: {method: 'POST', params: {courseId: '@courseId'}, url: '/project/createUsers/:courseId'}
        });

        ctrl.model = {};
        ctrl.upload = upload;
        ctrl.tableChange = tableChange;

        init();

        function tableChange(index) {
            var e = ctrl.model.deadlines[index];

            if (ctrl.model.deadlines.length - 1 == index) {
                ctrl.model.deadlines.push({});
            } else if (_.isEmpty(e.date) && _.isEmpty(e.description) && _.isEmpty(e.points)) {
                _.pullAt(ctrl.model.deadlines, index);
            }
        }

        function init() {
            ctrl.model.deadlines = [{}];
        }

        function upload() {
            console.log(ctrl);
            _.pullAt(ctrl.model.deadlines, ctrl.model.deadlines.length - 1);
            resource.data(getParams(), ctrl.model);
        }

        function getParams() {
            return {
                courseId: $stateParams.courseId
            }
        }
    }
})();
