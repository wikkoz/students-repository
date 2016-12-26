(function () {
    'use strict';

    angular.module('projekt').directive('email', email);
    function email() {
        return {
            restrict: 'E',
            templateUrl: 'components/email/email.html',
            bindToController: true,
            scope: {
                title: '@',
                addresses: '='
            },
            controllerAs: 'ctrl',
            controller: emailCtrl
        };
    }

    function emailCtrl($resource) {
        var ctrl = this;

        var BASE_URL = '/mail';
        var resource = $resource('', {}, {
            send: {method: 'POST', url: BASE_URL + '/send'}
        });

        ctrl.send = send;

        function send() {
            resource.send(ctrl.model);
        }
    }

})(); 