/**
 * Created by leilihuang on 16/5/23.
 */
var headLoding = {
    baseUrl: 'http://open.domestore.cn/',
    channelUrl: 'http://channel.domestore.cn/',
    cookie:{},
    //baseUrl:'http://192.168.58.88/',
    init: function(css, js) {
        var head = document.getElementsByClassName('util_head_box');
        this.getCookie();
        if (!head || head.length === 0) {
            this.css(css);
            this.js(js);
            this.isLogin();
            this.exitBind();
            this.userNameBind();
            this.manageDropdwon();
        }
    },
    getCookie: function() {
        var cookie = document.cookie.split(";");
        for(var i=0;i<cookie.length;i++){
            this.cookie[cookie[i].split('=')[0].trim()] = cookie[i].split('=')[1];
        }
    },
    userNameBind: function() {
        var _this = this;
        setTimeout(function() {
            $('#userName').on('click', function() {
                $(this).next().show();
                return false;
            });
            _this.channelBind();
        }, 500);
        $(document).on('click', function(e) {
            $('#userName').next().hide();
        });
    },
    manageDropdwon: function() {
        setTimeout(function() {
            $('#manageDropdwon').on('click', function() {
                $(this).next().show();
                return false;
            });
        }, 500);
        $(document).on('click', function(e) {
            $('#manageDropdwon').next().hide();
        });
    },
    exitBind: function() {
        var _this = this;
        setTimeout(function() {
            $('.user_exit').on('click', function() {
                $.ajax({
                    url: _this.baseUrl + 'user/logout',
                    type: 'POST',
                    data: {},
                    dataType: 'json',
                    success: function(d) {
                        if (d.responseCode == 1000) {
                            window.location.href = '/app/index';
                        } else if (d.responseCode == 1004) {
                            $('.headBox').find('.login_dl').show();
                            $('.headBox').find('.login_user').hide();
                            window.location.href = 'login.html?url=' + location.href;
                        } else {
                            //util.tipsAlert(null,d.errorMsg,'tips_error');
                        }
                    },
                    error: function(e) {
                        //util.tipsAlert(null,"系统异常，请刷新重试!",'tips_error');
                    }
                });
            });
        }, 500);

    },
    css: function(path) {
        if (!path || path.length === 0) {
            throw new Error('没有添加头部css路径');
        }
        var head = document.getElementsByTagName('head')[0],
            link = document.createElement('link');
        link.href = path;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        head.appendChild(link);
    },
    js: function(path) {
        if (!path || path.length === 0) {
            throw new Error('没有添加头部j路径');
        }
        var head = document.getElementsByTagName('head')[0],
            script = document.createElement('script');
        script.src = path;
        script.type = 'text/javascript';
        head.appendChild(script);
    },
    isLogin: function() {
        $.ajax({
            url: this.baseUrl + 'user/getUserInfo',
            type: 'GET',
            data: {},
            dataType: 'json',
            success: function(d) {
                if (d.responseCode == 1000) {
                    $('.headBox').find('.login_dl').hide();
                    $('.headBox').find('.login_user').find('.menusBox').hide();
                    $('.headBox').find('.login_user').show();
                    $('#userName').text(d.data.user.loginName);
                } else if (d.responseCode == 1004) {
                    $('.headBox').find('.login_dl').show();
                    // window.location.href='login.html?url='+location.href;
                } else {
                    alert('系统异常，请刷新重试！');
                }
            },
            error: function(e) {
                //util.tipsAlert(null,"系统异常，请刷新重试!",'tips_error');
            }
        })
    },
    channelBind: function() {
        var _this = this;
        $('#channel-manage-menu').on('click', function() {
            $.ajax({
                url: _this.channelUrl + 'channel/query',
                type: 'GET',
                dataType: 'json',
                headers:{
                    "domeAccessToken":_this.cookie.dome_access_token || ''
                },
                success: function(d) {
                    if (d.responseCode == 1000) {
                        if (d.data.status == '0') {
                            window.location.href = '/channel/channelmanage-subapply.html';
                        } else if (d.data.status == '1') {
                            window.location.href = '/channel/channelmanage-reviewing.html';
                        } else if (d.data.status == '2') {
                            window.location.href = '/channel/channelmanage-applygame.html';
                        } else if (d.data.status == '3') {
                            window.location.href = '/channel/channelmanage-notrougth.html';
                        } else {
                            alert('拒绝访问请联系客服!');
                        }
                    } else if (d.responseCode == 1004) {
                        var url = 'http://open.domestore.cn/merchant/login.html??channel';
                        window.location.href = url;
                    } else {
                        alert('系统异常，请刷新重试！');
                    }
                },
                error: function(e) {

                }
            })
        })
    }
};
//headLoding.init('/dist/css/head.css','/dist/js/head.js');
