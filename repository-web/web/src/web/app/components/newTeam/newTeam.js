(function () {
  'use strict'

  angular.module('projekt').directive('newTeam', newTeam);
  function newTeam(){
      return {
        restrict: 'E',
        templateUrl: 'components/newTeam/newTeam.html',
        bindToController: true,
        scope: {},
        controllerAs: 'ctrl',
        controller: newTeamCtrl,
      };
  }

  function newTeamCtrl(){
      var ctrl = this;

  }

})(); 