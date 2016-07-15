(function () {
  'use strict'

  angular.module('projekt').directive('tableLab', tableLab);
  function tableLab(){
      return {
        restrict: 'E',
        templateUrl: 'components/tableLab/tableLab.html',
        scope: {
          model: '=',
          number: '@'
        },
        controllerAs: 'ctrl',
        controller: tableLabCtrl
      };
  }

  /*@ngInject*/
  function tableLabCtrl($state){
    var ctrl = this;
    ctrl.click = click;
    ctrl.newTeam = newTeam;
    ctrl.model = [
    {
      'course': 'PIK',
      'name': "Projekt",
      'date': '11-11-2016',
    },
    {
      'course': 'PIK',
      'name': "Projekt",
      'date': '11-11-2016',
    }
    ];

    function click(){
      $state.go('project');
    }

    function newTeam(){
      $state.go('newteam');
    }
  }
})();