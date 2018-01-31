var webApp = angular.module('webApp', [
    'ngRoute',
    'ngAnimate',
    'ui.router',
    'toastr',
    'bw.paging',
    'duScroll',
    'chieffancypants.loadingBar'
]);

webApp.config(function ($routeProvider) {
    $routeProvider.when('/dashboard', {
        templateUrl: 'tpls/dashboard.html'
    }).otherwise({
            redirectTo: '/dashboard'
        }
    );
}).config(function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = true;
});
