Api = {
    http: function ($http, apitype, text, success) {
        var url = "http://192.168.104.178:8080/wlogger/api/gateway";
        $.ajax({
            type: 'POST',
            url: url,
            data: {
                apitype: apitype,
                text: text
            },
            success: function (data) {
                success(data.res);
            }
        });
    }
};