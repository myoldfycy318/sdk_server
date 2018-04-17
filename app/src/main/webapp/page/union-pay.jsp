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

        <img class="img-responsive g-w-all" src="../img/index-bg2.png" />

        <div class="container union-login">
            <div class="row pt pt1">
                <div class="col-xs-12 padding-top20 text-center title">
                    <h1>服务介绍</h1>
                    <p class="c-d f-fs4">冰穹统一支付服务使您可以在应用中直接向用户销售付费内容并获得收入。其中付费内容可包括多种多样的虚拟数字商品，比如游戏货币、游戏</p>
                    <button type="button" class="btn btn-default" onclick="location.href='/merchant/addservice.html'">添加支付服务</button>
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
                            <div class="info-card ">
                                <i class="qb-icon unionpay-1"></i>
                                <h3 class="c-d2">集成简单</h3>
                                <p class="c-d text-left">集成工作量小，开发迅速，可迅速上线获得现金流。</p>
                            </div>

                            <div class="info-card">
                                <i class="qb-icon unionpay-2"></i>
                                <h3 class="c-d2">支持多种计费方式</h3>
                                <p class="c-d text-left">计费方式灵活，可按需选择</p>
                            </div>

                            <div class="info-card">
                                <i class="qb-icon unionpay-3"></i>
                                <h3 class="c-d2">优惠的分成比例</h3>
                                <p class="c-d text-left">冰穹统一支付服务为开发者提供了极具吸引力的分成比例，充分保障开发者利益</p>
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