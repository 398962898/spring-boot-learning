var app = new Vue({
    el: "#app",
    data: {
        message: "hello world",
        webSocketResponses: [],
    },
    methods: {
        socketOpenFn: function () {
            var sock = new SockJS(ApiPath.socketUrl);
            // var sock = new SockJS("/endpointHello");
            client = Stomp.over(sock);
            var headers = {};
            client.connect(headers,
                function (success) {
                    console.log('Connected:' + success);
                    // client.subscribe(ApiPath.socketConnectUrl, function (response) {
                    client.subscribe("/topic/websockethello", function (response) {
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
            var headers = {
                auth: localStorage.getItem("auth"),
            };
            // client.send(ApiPath.socketSendUrl, headers, app.message);
            client.send("/websockethello", headers, app.message);
        },
    },
});
var client = null;