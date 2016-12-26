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
    function tabCtrl($scope, AuthenticationService) {
        var ctrl = this;
        var roles = [];
        var ROLE_PREFIX = 'ROLE_';
        var ROLES = ['STUDENT', 'TUTOR', 'LECTURER', 'ADMIN'];
        var roleIndex;

        ctrl.reload = reload;
        ctrl.hasRole = hasRole;
        ctrl.setActive = setActive;

        init();

        function init() {
            roles = AuthenticationService.getRoles().$promise.then(function (data) {
                roles = data.roles;
                for(var i = 0; i < ROLES.length; ++i) {
                    if(_.includes(roles, ROLE_PREFIX + ROLES[i])) {
                        roleIndex = i;
                        break;
                    }
                }
            });
        }

        function setActive(role) {
            if(role === ROLES[roleIndex])
                return 'active in';
            else
                return ' ';
        }

        function hasRole(role) {
            return _.includes(roles, ROLE_PREFIX + role);
        }

        function reload(action) {
            $scope.$broadcast(action);
        }
    }
})();