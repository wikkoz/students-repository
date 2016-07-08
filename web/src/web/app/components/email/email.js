(function () {
  'use strict'

  angular.module('projekt').directive('email', email);
  function email(){
      return {
        restrict: 'E',
        templateUrl: 'components/email/email.html',
        bindToController: true,
        scope: {
          title: '@'
        },
        controllerAs: 'ctrl',
        controller: emailCtrl,
      };
  }

  function emailCtrl(){

  }

})(); 