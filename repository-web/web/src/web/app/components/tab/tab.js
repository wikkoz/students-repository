(function () {
    'use strict';

    angular.module('projekt').directive('tab', tab);
    function tab() {
        return {
            restrict: 'E',
            templateUrl: 'components/tab/tab.html',
            scope: {},
            controllerAs: 'ctrl',
            controller: tabCtrl
        };
    }

    /*@ngInject*/
    function tabCtrl($resource) {
        var ctrl = this;

        var resource = $resource('', {}, {
            admin: {method:'GET', url:'admin'},
            lab: {method:'GET', url:'lab'},
            student: {method:'GET', url:'/student/projects'},
            tutor: {method:'GET', url:'tutor'}
        });

        ctrl.lab = lab;
        ctrl.student = student;
        ctrl.tutor = tutor;

        ctrl.studentModel = {};
        ctrl.labTabModel = {};
        ctrl.labSideModel = {};
        ctrl.SubjectSideModel = {};
        ctrl.SubjectTabModel = {};


        function lab() {
            console.log("dupa2");
        }
        
        function student() {
            ctrl.studentModel = resource.student();
        }
        
        function tutor() {
            console.log("dupa4");
        }
    }
})();