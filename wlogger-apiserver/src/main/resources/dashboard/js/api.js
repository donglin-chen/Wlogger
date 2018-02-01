Api = {
    http: function ($http, apitype, text, success) {
        var url = "http://wlogger.mob.com/wlogger/api/gateway";
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