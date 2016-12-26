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
    function newProjectCtrl($resource, $stateParams, $state, Notification) {
        var ctrl = this;
        var resource = $resource('', {}, {
            data: {method: 'POST', params: {courseId: '@courseId'}, url: '/project/createProject/:courseId'}
    });

        ctrl.model = {};
        ctrl.upload = upload;
        ctrl.open = open;
        ctrl.tableChange = tableChange;

        init();

        function init() {
            Notification({
                message: 'Uzupełnij dane w celu stworzenia projektu',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            ctrl.options = {
                formatYear: 'yy',
                startingDay: 1
            };
            ctrl.datepicker = [];
            ctrl.deadlines = [{}];
        }

        function tableChange(index) {
            var e = ctrl.deadlines[index];

            if (ctrl.deadlines.length - 1 == index) {
                ctrl.deadlines.push({});
            } else if (_.isNil(e.date) && _.isEmpty(e.description) && _.isNil(e.points)) {
                _.pullAt(ctrl.deadlines, index);
            }
        }

        function upload() {
            ctrl.model.deadlines = _.cloneDeep(ctrl.deadlines);
            console.log(ctrl);
            _.pullAt(ctrl.model.deadlines, ctrl.model.deadlines.length - 1);
            resource.data(getParams(), ctrl.model).$promise.then(function () {
                Notification.info("Stworzono projekt");
                $state.go('/');
            });
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
