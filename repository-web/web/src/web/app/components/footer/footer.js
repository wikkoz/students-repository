(function () {
    'use strict'

    angular.module('projekt').directive('footer', footer);
    function footer() {
        return {
            restrict: 'E',
            templateUrl: 'components/footer/footer.html',
            scope: {
                model: '='
            },
            controllerAs: 'ctrl',
            controller: footerCtrl
        };
    }

    function footerCtrl() {
        var ctrl = this;
    }
})();