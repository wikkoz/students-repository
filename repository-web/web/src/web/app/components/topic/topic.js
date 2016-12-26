(function () {
    'use strict';

    angular.module('projekt').directive('topic', topic);
    function topic() {
        return {
            restrict: 'E',
            templateUrl: 'components/topic/topic.html',
            scope: {},
            controllerAs: 'ctrl',
            bindToController: true,
            controller: topicCtrl
        };
    }

    /*@ngInject*/
    function topicCtrl($resource, Notification) {
        var ctrl = this;

        var BASE_URL = '/topic';
        var resource = $resource('', {}, {
            courses: {method: 'GET', url: BASE_URL + '/courses', isArray: true},
            topics: {method: 'POST', url: BASE_URL + '/topics', isArray: true},
            edit: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/edit/:id'},
            remove: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/remove/:id'},
            mark: {method: 'POST', params: {id: '@id'}, url: BASE_URL + '/mark/:id'},
            add: {method: 'POST', url: BASE_URL + '/add'}
        });

        ctrl.getTopics = getTopics;
        ctrl.markTopic = markTopic;
        ctrl.editTopic = editTopic;
        ctrl.removeTopic = removeTopic;
        ctrl.addTopic = addTopic;
        ctrl.showTopics = showTopics;

        init();

        function init() {
            Notification({
                message: 'Wybierz temat, aby pokazać informacje o nim <br> Jeśli jakiś temat jest wolny to wejście w niego powoduje przejęcie go oraz usunięcie innych zaproszeń.',
                delay: null, positionX: 'left', positionY: 'bottom', replaceMessage: true
            });
            resource.courses().$promise.then(function (data) {
                ctrl.courses = data;
            })
        }

        function getTopics() {
            resource.topics(ctrl.subject).$promise.then(function (data) {
                ctrl.topics = data;
            })
        }

        function markTopic(topic) {
            resource.mark({id: topic.id}, topic.chosen).$promise.then(function () {
                getTopics();
            })
        }

        function editTopic(topic) {
            resource.edit({id: topic.id}, getDtoFromTopic(topic)).$promise.then(function () {
                getTopics();
            })
        }

        function removeTopic(topic) {
            resource.remove({id: topic.id}).$promise.then(function () {
                getTopics();
            })
        }

        function addTopic() {
            resource.add({topic: ctrl.topic, course: ctrl.subject}).$promise.then(function () {
                getTopics();
                ctrl.topic.topic = '';
                ctrl.topic.description = '';
            });
        }

        function showTopics() {
            return ctrl.subject && !_.isEmpty(ctrl.topics)
        }

        function getDtoFromTopic(topic) {
            return {
                topic: topic.topic,
                description: topic.description
            }
        }
    }
})();