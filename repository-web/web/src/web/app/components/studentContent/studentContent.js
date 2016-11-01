(function () {
    'use strict';

    angular.module('projekt').directive('studentContent', studentContent);
    function studentContent() {
        return {
            restrict: 'E',
            templateUrl: 'components/studentContent/studentContent.html',
            bindToController: true,
            scope: {},
            controllerAs: 'ctrl',
            controller: studentContentCtrl
        };
    }

    /*ngInject*/
    function studentContentCtrl($stateParams, $resource) {
        var ctrl = this;
        console.log($stateParams);
        var BASE_URL = '/student';
        var resource = $resource('', {}, {
            data: {method: 'GET', url: BASE_URL + '/team/:id'},
            availableStudents: {method: 'GET', url: BASE_URL + '/team/:id/students'}
        });

        ctrl.show = show;
        ctrl.model = {};
        init();

        function init() {
            resource.data(getParams());
            resource.availableStudents(getParams());
        }

        function show() {
            return true;
        }
        
        function getParams(){
            console.log({
                id: $stateParams.teamId
            });
            return {
                id: $stateParams.teamId
            }
        }
    }

})(); 