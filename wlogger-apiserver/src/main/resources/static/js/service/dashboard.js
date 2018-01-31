webApp.controller('dashboardController', function (cfpLoadingBar, $scope, $http, $interval) {
    
    $scope.levelList = [{
        name: 'ALL',
        value: ''
    }, {
        name: 'INFO',
        value: 'INFO'
    }, {
        name: 'DEBUG',
        value: 'DEBUG'
    }, {
        name: 'WARN',
        value: 'WARN'
    }, {
        name: 'ERROR',
        value: 'ERROR'
    }];
    
    $scope.paramsLevel = $scope.levelList[0];
    
    $scope.params = {
        level: $scope.paramsLevel.value,
        limit: 100,
        namespace: '',
        ip: '',
        like: '',
        startAt: '',
        endAt: ''
    };
    
    $scope.dataList = [];
    
    $scope.loadData = function () {
        $scope.params.level = $scope.paramsLevel.value;
        Api.http($http, "DASHBOARD", JSON.stringify($scope.params), function (data) {
            $scope.dataList = data;
        });
    };
    
    //fetch data
    $scope.loadData();
    
    $interval(function () {
        $scope.loadData();
        $scope.scrollWindow();
    }, 10);
    
    $scope.scrollWindow = function () {
        var _el = document.getElementById('chat_history');
        _el.scrollTop = _el.scrollHeight;
    };
    
    
});