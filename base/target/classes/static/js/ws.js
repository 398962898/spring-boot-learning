var app = new Vue({
    el: "#app",
    data: {
        message: "hello world",
        webSocketResponses: [],
    },
    methods: {
        socketOpenFn: function () {
            var sock = new SockJS('/endpointHello');
            client = Stomp.over(sock);
            var header = {};
            client.connect(header,
                function (success) {
                    console.log('Connected:' + success);
                    client.subscribe('/topic/websockethello', function (response) {
                        var webSocketResponse = JSON.parse(response.body);
                        webSocketResponse.gmtCreate = new Date(webSocketResponse.gmtCreate).toLocaleDateString() + "  " + new Date(webSocketResponse.gmtCreate).toLocaleTimeString();
                        app.webSocketResponses.unshift(webSocketResponse);
                    });
                },
                function (error) {
                    console.log('Connected:' + error);
                });
        },
        socketCloseFn: function () {
            if (client != null) {
                client.disconnect();
            }
            console.log('Disconnected');
        },
        socketSendFn: function () {
            var header = {
                auth: localStorage.getItem("auth"),
            };
            client.send("/websockethello", header, app.message);
        },
    },
});
var client = null;