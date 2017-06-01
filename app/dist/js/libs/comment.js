var yuizAjax = {
    post: function (url, sendData, fn) {
        $.ajax({
            type: "POST",
            url: url,
            data: sendData,
            headers: {
                auth: localStorage.getItem(Auth.client),
            },
            success: function (result) {
                fn(result);
            }
        })
    },
    get: function (url, sendData, fn) {
        $.ajax({
            type: "GET",
            url: url,
            data: sendData,
            headers: {
                auth: localStorage.getItem("auth"),
            },
            success: function (result) {
                fn(result);
            }
        })
    },
};