(function () {
  'use strict'

  angular.module('projekt').directive('sideSubject', sideSubject);
  function sideSubject(){
      return {
        restrict: 'E',
        templateUrl: 'components/sideSubject/sideSubject.html',
        scope: {
          model: '='
        },
        controllerAs: 'ctrl',
        controller: sideSubjectCtrl
      }; 
  }
  function sideSubjectCtrl(){
    var ctrl = this;

  }
})();