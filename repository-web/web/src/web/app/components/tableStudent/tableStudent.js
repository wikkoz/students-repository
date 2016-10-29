(function () {
  'use strict'

  angular.module('projekt').directive('tableStudent', tableStudent);
  function tableStudent(){
      return {
        restrict: 'E',
        templateUrl: 'components/tableStudent/tableStudent.html',
        scope: {
          model: '=',
          number: '@'
        },
        controllerAs: 'ctrl',
        controller: tableStudentCtrl
      };
  }

  /*@ngInject*/
  function tableStudentCtrl($state, $resource){
    var ctrl = this;
    ctrl.click = click;
    ctrl.model = [
    {
      'course': 'PIK',
      'name': "Projekt",
      'supervisor': "Jan Kowalski",
      'date': '11-11-2016',
      'id': 1
    },
    {
      'course': 'PIK',
      'name': "Projekt",
      'supervisor': "Jan Kowalski",
      'date': '11-11-2016',
      'id': 1
    }
    ];

    function click(id){
      $state.go('team', {teamId: id});
    }
  }
})();