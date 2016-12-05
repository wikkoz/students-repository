(function () {
  'use strict';

  angular.module('projekt').directive('labContent', labContent);
  function labContent(){
      return {
        restrict: 'E',
        templateUrl: 'components/labContent/labContent.html',
        bindToController: true,
        scope: {},
        controllerAs: 'ctrl',
        controller: labContentCtrl
      };
  }

  function labContentCtrl($resource, $stateParams){
      var ctrl = this;

      var BASE_URL = '/tutor';
      var resource = $resource('', {}, {
          data: {method: 'GET', url: BASE_URL + '/team/:id'}
      });

      init();

      function init() {
          resource.data(getParams()).$promise.then(function (response) {
              ctrl.model = response;
          });
      }

      function getParams() {
          return {
              id: $stateParams.teamId
          }
      }
  }

})(); 