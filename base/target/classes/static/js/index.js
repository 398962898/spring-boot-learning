var app = new Vue({
    el: "#app",
    data: {
        message: '你好啊',
        countrys: [],
        pageNum: 1,
        pageSize: 10,
        pages: 1,
        navigatepageNums: [],
        username: '',
        password: '',
        flag: true,
        user: {},
    },
    methods: {
        showFn: function () {
            app.flag = true;
        },
        hideFn: function () {
            app.flag = false;
        },
        registerFn: function () {
            var url = "/register";
            var sendData = {};
            sendData.username = app.username;
            sendData.password = app.password;
            $.post(url, sendData, function (result) {
                console.log(result);
                app.message = result.msg;
                if (result.code == 200) {

                } else {
                    app.user = {};
                }
            });
        },
        loginFn: function () {
            var url = "/login";
            var sendData = {};
            sendData.username = app.username;
            sendData.password = app.password;
            $.post(url, sendData, function (result) {
                console.log(result);
                app.message = result.msg;
                if (result.code == 200) {

                } else {
                    app.user = {};
                }
            });
        },
        infoFn: function () {
            var url = "/info";
            var sendData = {};
            $.get(url, sendData, function (result) {
                console.log(result);
                app.message = result.msg;
                if (result.code == 200) {
                    app.user = result.data;
                    if (app.user.sex == 0) {
                        app.user.sex = '未设定';
                    } else if (app.user.sex == 1) {
                        app.user.sex = '男';
                    } else if (app.user.sex == 2) {
                        app.user.sex = '女';
                    }
                    app.user.birthday = new Date(app.user.birthday).toLocaleDateString();
                } else {

                }
            });
        },
        logoutFn: function () {
            var url = "/logout";
            var sendData = {};
            $.post(url, sendData, function (result) {
                console.log(result);
                app.message = result.msg;
                if (result.code == 200) {

                } else {

                }
            });
        },
        getCountrysFn: function () {
            var url = "/country/list";
            var sendData = {};
            sendData.pageNum = app.pageNum;
            sendData.pageSize = app.pageSize;
            $.get(url, sendData, function (result) {
                    console.log(result);
                    app.message = result.msg;
                    if (result.code == 200) {
                        app.countrys = result.data.list;
                        app.pages = result.data.pages;
                        app.navigatepageNums = result.data.navigatepageNums;
                    } else {

                    }
                }
            )
        },
        setPageNumFn: function (pageNum) {
            app.pageNum = pageNum;
            app.getCountrysFn();
        },
        lastPageFn: function () {
            if (app.pageNum > 1) {
                app.pageNum--;
            }
            app.getCountrysFn();
        },
        nextPageFn: function () {
            if (app.pageNum < app.pages) {
                app.pageNum++;
            }
            app.getCountrysFn();
        },
    },
    mounted: function () {
        this.$nextTick(function () {
            app.getCountrysFn();
        })
    },
});