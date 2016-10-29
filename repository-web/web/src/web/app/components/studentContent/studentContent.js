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
        var BASE_URL = '/student';
        var resource = $resource('', {}, {
            data: {method: 'GET', url: BASE_URL + '/team/{:id}'}
        });

        ctrl.show = show;
        ctrl.model = {};
        init();

        function init() {
            resource.data(getParams());
        }

        function show() {
            return true;
        }
        
        function getParams(){
            return {
                id: $stateParams.teamId
            }
        }
    }

})(); 