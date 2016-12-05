(function () {
    'use strict';
    angular.module('projekt').factory('AuthenticationService', AuthenticationService);

    /*@ngInject*/
    function AuthenticationService($resource) {
        var resource = $resource('', {}, {
            user: {method: 'GET', url: '/user/user'}
        });

        var service = {
            hasRole: hasRole
        };

        return service;

        function hasRole(role) {
            return resource.user().$promise.then(function (data) {
                _.contains(data.roles, role);
            });
        }
    }
})();
