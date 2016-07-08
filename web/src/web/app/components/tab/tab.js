(function () {
  'use strict'

  angular.module('projekt').directive('tab', tab);
  function tab(){
      return {
        restrict: 'E',
        templateUrl: 'components/tab/tab.html',
        scope: {
          model: '@',
          tab: '='
        },
        controllerAs: 'ctrl',
        controller: tabCtrl
      };
  }
  function tabCtrl(){
    var ctrl = this;
    ctrl.tabs = [
      { title:'Dynamic Title 1', content:'Dynamic content 1', show: true },
      { title:'Dynamic Title 2', content:'Dynamic content 2', show: true }
    ];

    ctrl.model = {
      name: 'Tabs'
    };
  }
})();