(function () {
    'use strict';

    angular.module('projekt').directive('newProject', newProject);

    function newProject() {
        return {
            restrict: 'E',
            templateUrl: 'components/newProject/newProject.html',
            bindToController: true,
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
        ctrl.open = open;
        ctrl.tableChange = tableChange;

        init();

        function init() {
            ctrl.options = {
                formatYear: 'yy',
                startingDay: 1
            };
            ctrl.datepicker = [];
            ctrl.model.deadlines = [{}];
        }

        function tableChange(index) {
            var e = ctrl.model.deadlines[index];

            if (ctrl.model.deadlines.length - 1 == index) {
                ctrl.model.deadlines.push({});
            } else if (_.isEmpty(e.date) && _.isEmpty(e.description) && _.isEmpty(e.points)) {
                _.pullAt(ctrl.model.deadlines, index);
            }
        }

        function upload() {
            console.log(ctrl);
            _.pullAt(ctrl.model.deadlines, ctrl.model.deadlines.length - 1);
            resource.data(getParams(), ctrl.model);
        }

        function open(index) {
            ctrl.datepicker[index] = true;
        }

        function getParams() {
            return {
                courseId: $stateParams.courseId
            }
        }
    }
})();
