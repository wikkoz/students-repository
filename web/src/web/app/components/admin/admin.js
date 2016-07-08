(function() {
    'use strict'

    angular.module('projekt').directive('admin', admin);

    function admin() {
        return {
            restrict: 'E',
            templateUrl: 'components/admin/admin.html',
            bindToController: true,
            scope: {
                model: '=',
            },
            controllerAs: 'ctrl',
            controller: adminCtrl
        };
    }

    /*@ngInject*/
    function adminCtrl(FileUploader) {
        var ctrl = this;
        ctrl.add = add;
        ctrl.selectedFile = selectedFile;
        ctrl.uploader = new FileUploader();

        function add() {
            var f = document.getElementById('file').files[0],
                r = new FileReader();
            r.onloadend = function(e) {
                var data = e.target.result;
                //addfile
            }
            r.readAsBinaryString(f);
        }

        function selectedFile(value) {
            ctrl.file = document.getElementById('file').files[0].name;
            console.log (ctrl.file);
        }
    }
})();
