(function () {
    'use strict';
    angular.module('projekt').factory('AuthenticationService', AuthenticationService);

    /*@ngInject*/
    function AuthenticationService($resource) {
        var resource = $resource('', {}, {
            user: {method: 'GET', url: '/user/user'}
        });

        var service = {
            getRoles: getRoles,
            getName: getName
        };

        return service;

        function getRoles() {
            return resource.user();
        }

        function getName() {
            resource.user().$promise.then(function (data) {
                return data.name;
            });
        }
    }
})();
