<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script src="../dist/js/head.js" type="text/javascript"></script>
	<script type="text/javascript">
	    headLoding.init('/dist/css/head.css','/dist/js/head.js');
	</script>
</head>
<body>
<div class="headBox">
    <div class="conBox">
        <a href="/app/index.html">
            <div class="logo"></div>
            <div class="title">冰穹开放平台</div>
        </a>
        <div class="menuBox">
            <a href="/app/index.html" class="menus cur">冰穹服务</a>
            <div style="float: left;">
            <a href="javascript:void(0);" id="manageDropdwon" class="menus" 
                style="float: none; position: relative;">管理控制台</a>
                <ul class="manageBox" style="display: none">
                    <li>
                        <a href="/merchant/addservice.html">手游</a>
                    </li>
                    <li>
                        <a href="/merchant/addservice.html?t=yeyougame">网页游戏</a>
                    </li>
                    <li>
                        <a href="/merchant/addservice.html?t=h5game">H5游戏</a>
                    </li>
                </ul>
            </div>
            <a id="channel-manage-menu" href="javascript:void(0);" class="menus @@channelnav">渠道管理</a>
            <a href="/app/contactus.html" class="menus">联系我们</a>
        </div>
        <div  class="login login_dl">
            <a href="/merchant/login.html" class="go">登录</a>
            <span class="line"></span>
            <a href="/merchant/registered.html" class="go">注册</a>
        </div>
        <div style="display: none" class="login_user login">
            <span id="userName" class="userName"></span>
            <ul class="menusBox" style="display: none">
                <li class="user_menu">
                    <a href="/usercenter/main.html">用户中心</a>
                </li>
                <li class="user_menu">
                    <a href="/merchant/resetPassword.html">修改密码</a>
                </li>
                <li class="user_menu user_exit">退出</li>
            </ul>
        </div>
    </div>
</div>
<div class="templateHide" id="templateHide" style="display: none"></div>

</body>

</html>