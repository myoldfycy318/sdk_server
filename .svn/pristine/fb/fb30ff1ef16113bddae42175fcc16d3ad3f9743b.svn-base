<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <title></title>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <!--<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>-->
        <meta name="renderer" content="webkit">
        <script src="../js/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../js/bootstrap/css/bootstrap.min.css">
        <script src="../js/bootstrap/js/bootstrap.min.js"></script>
        <script src="../js/is.min.js"></script>
        <script src="../js/base.js"></script>
        <script src="../js/util.js"></script>
        <script src="../js/jquery.placeholder.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/base.css">
        <link rel="stylesheet" type="text/css" href="../css/qianbao.css">
        <!--[if lt IE 9]>
        <script src="../js/html5shiv.min.js"></script>
        <script src="../js/respond.min.js"></script>
        <![endif]-->
        <script>
	        $(function(){
				showStatusN(0);
			});
        </script>
    </head>
    <body>
        <%@ include  file="headNew.jsp"%>

        <img class="img-responsive g-w-all" src="../img/index-bg1.png" />

        <div class="container union-login">
            <div class="row pt pt1">
                <div class="col-xs-12 padding-top20 text-center title">
                    <h1>服务介绍</h1>
                    <p class="c-d f-fs4">联合登录是冰穹向开发者提供的冰穹账号快捷登录服务，用户可以方便快捷的使用冰穹账号登录您的网站或移动应用，为您增加海量用户的同时，带去丰富的用户资料，提供良好的用户体验</p>
                    <button type="button" class="btn btn-default" onclick="location.href='/merchant/addservice.html'">添加登录服务</button>
                </div>
            </div>

        </div>

        <div class="container-fluid union-login">
            <div class="row pt pt2">
                <div class="col-xs-12 padding-top20 text-center title">
                    <h1>服务特点</h1>
                </div>

                <div class="col-xs-12">
                    <div class="container g-w-cn2 text-center ser-char">
                        <div class="row">
                            <div class="info-card col-xs-4">
                                <i class="qb-icon unionlogin-1"></i>
                                <h3 class="c-d6">良好的用户体验</h3>
                                <p class="c-d text-left">降低网站及移动用户的注册和登录成本，用户优先选择更快捷的登录方式。</p>
                            </div>

                            <div class="info-card col-xs-4">
                                <i class="qb-icon unionlogin-2"></i>
                                <h3 class="c-d6">海量的用户数据</h3>
                                <p class="c-d text-left">联合登录时，在用户授权的情况下冰穹账户会提供用户的相关信息帮助开发者完善用户数据。</p>
                            </div>

                            <div class="info-card col-xs-4">
                                <i class="qb-icon unionlogin-3"></i>
                                <h3 class="c-d6">海量活跃用户</h3>
                                <p class="c-d text-left">联合登录为您带来海量冰穹活跃用户，提升网站及移动应用的用户数及访问量。</p>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

        <div class="container union-login">
            <div class="row pt pt3">
                <div class="col-xs-12 padding-top20 text-center title">
                    <h1>使用导航</h1>
                </div>

                <div class="container g-w-cn2">

                    <div class="col-xs-12">
                        <div class="panel panel-default f-fs2">
                            <div class="panel-heading">
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-xs-3 no-padding-left f-fs2 c-d2">应用名称</div>
                                        <div class="col-xs-4 no-padding-left f-fs2 c-d2">文档名</div>
                                        <div class="col-xs-4 no-padding-left f-fs2 c-d2">更新时间</div>
                                        <div class="col-xs-1 no-padding-left f-fs2 c-d2">操作</div>
                                    </div>
                                </div>
                            </div>
                            <c:forEach items="${resourceList}" var="resource" >
	                            <div class="panel-body">
	                                <div class="container-fluid">
	                                    <div class="row">
	                                        <div class="col-xs-3 no-padding-left f-fs2">
	                                        	<c:if test="${resource.resourceType=='10'}">
	                                        		文档支持
	                                        	</c:if>
	                                        	<c:if test="${resource.resourceType=='20'}">
	                                        		SDK下载
	                                        	</c:if>
	                                        	<small class="c-d3">${resource.fileSize}</small></div>
	                                        <div class="col-xs-4 no-padding-left f-fs2">${resource.resourceTitle}</div>
	                                        <div class="col-xs-4 no-padding-left f-fs2">${resource.showUpdateTime}</div>
	                                        <div class="col-xs-1 no-padding-left f-fs2"><a class="text-primary" href="/app/resourcedownload.html?resourceId=${resource.resourceId}">下载</a></div>
	                                    </div>
	                                </div>
	                            </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </div>

        </div>

 	<%@ include  file="foot.jsp"%>
    </body>
</html>