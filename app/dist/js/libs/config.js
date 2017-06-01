var RootPath = "http://127.0.0.1:9001";

var Auth = {
    server: "auth",
    client: "auth",
};

var ApiPath = {
    //用户注册地址
    registerUrl: RootPath + "/register",
    //用户登录地址
    loginUrl: RootPath + "/login",
    //获取登录用户信息地址
    infoUrl: RootPath + "/info",
    //用户退出登录地址
    logoutUrl: RootPath + "/logout",
    //获取国家地区列表
    countryListUrl: RootPath + "/country/list",
    //websocket的注册端点
    socketUrl: RootPath + "/endpointHello",
    //订阅消息地址
    socketConnectUrl: RootPath + "/topic/websockethello",
    //发送消息地址
    socketSendUrl: RootPath + "/websockethello",
};