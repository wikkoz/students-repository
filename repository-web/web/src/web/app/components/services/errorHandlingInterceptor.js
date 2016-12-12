(function () {
    'use strict';
    angular.module('projekt').factory('ErrorHandlingInterceptor', errorHandlingInterceptor);

    /*@ngInject*/
    function errorHandlingInterceptor($injector) {

        var service = {
            responseError: responseError
        };

        return service;

        function responseError(rejection) {
            inject('Notification').error(
            {
                title: 'Status: ' + rejection.status + '. ' + rejection.data.error,
                message: rejection.data.message
            });
        }

        function inject(key) {
            return $injector.get(key);
        }
    }
})();
