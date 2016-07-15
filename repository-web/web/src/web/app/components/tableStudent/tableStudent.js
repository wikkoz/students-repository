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
  function tableStudentCtrl($state){
    var ctrl = this;
    ctrl.click = click;
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
      $state.go('student');
    }
  }
})();