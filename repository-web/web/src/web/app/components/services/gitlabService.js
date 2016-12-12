(function () {
    'use strict';
    angular.module('projekt').factory('GitlabService', GitlabService);

    /*@ngInject*/
    function GitlabService($resource) {
        var resource = $resource('', {}, {
            isLogged: {method: 'GET', url: '/user/logged'}
        });

        var service = {
            login: login
        };

        return service;

        function login(successFunction, failueFunction) {
            resource.isLogged().$promise.then(function (response) {
                if (response.value) {
                    successFunction()
                } else {
                    failueFunction()
                }
            });
        }
    }
})();
