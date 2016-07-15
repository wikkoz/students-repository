(function () {
  'use strict'

  angular.module('projekt').directive('sideLab', sideLab);
  function sideLab(){
      return {
        restrict: 'E',
        templateUrl: 'components/sideLab/sideLab.html',
        scope: {
          model: '=',
        },
        controllerAs: 'ctrl',
        controller: sideLabCtrl
      }; 
  }
  function sideLabCtrl(){
    var ctrl = this;

  }
})();