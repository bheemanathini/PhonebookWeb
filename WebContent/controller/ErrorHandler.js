angular
    .module('Phonebook')
    .config(['$httpProvider', function ($httpProvider) {

 $httpProvider.interceptors.push(['$q', '$location', function ($q, $location) {
        return {
            'responseError': function(response) {
                if(response.status === 402 || response.status === 403) {
                    $location.path('/error.html'); // Replace with whatever should happen
                }
                return $q.reject(response);
            }
        };
    }]);
}]);