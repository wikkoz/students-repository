(function () {
    'use strict'

    angular.module('projekt').directive('sideSubject', sideSubject);
    function sideSubject() {
        return {
            restrict: 'E',
            templateUrl: 'components/sideSubject/sideSubject.html',
            scope: {
                subject: '='
            },
            controllerAs: 'ctrl',
            bindToController: true,
            controller: sideSubjectCtrl
        };
    }

    function sideSubjectCtrl($resource) {
        var ctrl = this;

        var BASE_URL = '/project';
        var resource = $resource('', {}, {
            projects: {method: 'GET', url: BASE_URL + '/names', isArray: true}
        });

        init();

        function init() {
            resource.projects().$promise.then(function (response) {
                ctrl.projects = response;
            });
        }
    }
})();