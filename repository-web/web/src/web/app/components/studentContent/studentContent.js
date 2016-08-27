(function () {
  'use strict';

  angular.module('projekt').directive('studentContent', studentContent);
  function studentContent(){
      return {
        restrict: 'E',
        templateUrl: 'components/studentContent/studentContent.html',
        bindToController: true,
        scope: {},
        controllerAs: 'ctrl',
        controller: studentContentCtrl
      };
  }

  function studentContentCtrl(){
      var ctrl = this;
      ctrl.show = show;

      function show(){
        return true;
      }
  }

})(); 