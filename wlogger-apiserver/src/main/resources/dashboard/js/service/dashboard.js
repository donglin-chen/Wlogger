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
    
    $scope.freshOpen = true;
    var timer;
    $scope.loadData = function () {
        $scope.params.level = $scope.paramsLevel.value;
        Api.http($http, "DASHBOARD", JSON.stringify($scope.params), function (data) {
            $scope.dataList = data;
            if ($scope.freshOpen == true) {
                $scope.scrollWindow();
            } else {
                $interval.cancel(timer);
            }
        });
    };
    
    //fetch data
    $scope.loadData();
    
    $interval(function () {
        $scope.loadData();
        
    }, 100);
    
    $scope.scrollWindow = function () {
        var _el = document.getElementById('chat_history');
        _el.scrollTop = _el.innerHTML.length;
        
    };
    
});