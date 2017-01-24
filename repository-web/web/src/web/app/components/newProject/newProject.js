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
        ctrl.showConfirmButton = showConfirmButton;

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
            _.pullAt(ctrl.model.deadlines, ctrl.model.deadlines.length - 1);
            resource.data(getParams(), ctrl.model).$promise.then(function () {
                Notification.info("Stworzono projekt");
                $state.go('/');
            });
        }

        function showConfirmButton() {
            var temp = _.cloneDeep(ctrl.deadlines);

            var anyEmptyElelemt = _.some(_.dropRight(temp, 1), function(e) {
                return _.isNil(e.date) || _.isNil(e.points) || _.isNil(e.description)
            });
            var deadlinesValidation = ctrl.deadlines.length > 1 && !anyEmptyElelemt;
            var numberValidation = !_.isNil(ctrl.model.minStudentsNumber) && !_.isNil(ctrl.model.minStudentsNumber)
                    && ctrl.model.minStudentsNumber <= ctrl.model.maxStudentsNumber;
            return deadlinesValidation && numberValidation && !_.isEmpty(ctrl.model.tutorFile) && !_.isEmpty(ctrl.model.studentFile)
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
