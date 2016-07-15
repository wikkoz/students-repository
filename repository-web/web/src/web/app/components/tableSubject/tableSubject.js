(function () {
  'use strict'

  angular.module('projekt').directive('tableSubject', tableSubject);
  function tableSubject(){
      return {
        restrict: 'E',
        templateUrl: 'components/tableSubject/tableSubject.html',
        scope: {
          model: '=',
          number: '@'
        },
        controllerAs: 'ctrl',
        controller: tableSubjectCtrl
      };
  }

  /*@ngInject*/
  function tableSubjectCtrl($state){
    var ctrl = this;
    ctrl.click = click;
    ctrl.newProject = true;
    ctrl.model = [
    {
      'course': 'PIK',
      'name': "Projekt",
      'supervisor': "Jan Kowalski",
      'date': '11-11-2016'
    },
    {
      'course': 'PIK',
      'name': "Projekt",
      'supervisor': "Jan Kowalski",
      'date': '11-11-2016'
    }
    ];

    function click(){
      $state.go('newproject');
    }
  }
})();