(function () {
  'use strict'

  angular.module('projekt').directive('labContent', labContent);
  function labContent(){
      return {
        restrict: 'E',
        templateUrl: 'components/labContent/labContent.html',
        bindToController: true,
        scope: {},
        controllerAs: 'ctrl',
        controller: labContentCtrl,
      };
  }

  function labContentCtrl(){
      var ctrl = this;
      ctrl.show = show;

      function show(){
        return true;
      }
  }

})(); 