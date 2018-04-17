    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
        <!--轮播图插件-->
        <script src="../js/flexSlider/jquery.flexslider-min.js"></script>
        <link rel="stylesheet" type="text/css" href="../js/flexSlider/flexslider.css">
        <!--[if lt IE 9]>
        <script src="../js/html5shiv.min.js"></script>
        <script src="../js/respond.min.js"></script>
        <![endif]-->
        <script>
	        $(function(){
				showStatusN(0);
			});
        
            $(function(){
                //初始化轮播图
                $(".flexslider").flexslider();
                $(".info-card.havebtn").each(function(){
                    $(this).hover(function(){
                        $(this).find(".info-card-bottom").animate({paddingTop:'0px'});
                        $(this).find("button").animate({marginTop:'16px'});
                        $(this).find("h2").animate({marginBottom:'15px'});
                    },function(){
                        $(this).find(".info-card-bottom").animate({paddingTop:'0px'});
                        $(this).find("button").animate({marginTop:'65px'});
                        $(this).find("h2").animate({marginBottom:'25px'});
                    })
                });

                $(".info-card.havecover").each(function(){
                    $(this).hover(function(){
                        $(this).find(".cover-img").animate({
                            top:'-2px'
                        })
                    },function(){
                        $(this).find(".cover-img").animate({
                            top:'343px'
                        })
                    })
                });
                
                <%--// 数据统计入口
                $("#dataStatistic").click(function(){
                	window.open("/statistic/toStatisticOverview.html");
                });
--%>

            });
        </script>

    </head>
    <body class="padding-top0">
		<%@ include  file="headNew.jsp"%>
        <div class="container-fluid">
            <div class="row">
                <div class="flexslider">
                    <ul class="slides">
                        <li><img src="../img/index-bg1.png" /></li>
                        <li><img src="../img/index-bg2.png" /></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="container index">
            <div class="row pt pt1">
                <div class="col-xs-12 text-center title">
                    <h1 style="margin-top: 0;">移动开发服务</h1>
                    <p class="c-d3">Mobile Development Services</p>
                </div>
                <div class="col-xs-3 text-center">
                    <div class="info-card havebtn">
                        <div class="info-card-top">
                            <i class="qb-icon main-dev-1"></i>
                        </div>
                        <div class="info-card-bottom">
                            <h2 class="c-d2">联合登录</h2>
                            <p class="c-d7">用冰穹账号访问APP和网站 ，提升用户数和访问量</p>
                            <button type="button" class="btn btn-default btn-block" onclick="location.href='/app/oauthindex.html'">了解详情</button>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3 text-center">
                    <div class="info-card havebtn">
                        <div class="info-card-top ">
                            <i class="qb-icon main-dev-2"></i>
                        </div>
                        <div class="info-card-bottom">
                            <h2 class="c-d2">统一支付</h2>
                            <p class="c-d">方便快捷的支付 ，提升支付转化率</p>
                            <button type="button" class="btn btn-default btn-block" onclick="location.href='/app/payindex.html'">了解详情</button>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3 text-center">
                    <div class="info-card havecover">
                        <div class="info-card-top">
                            <i class="qb-icon main-dev-3"></i>
                        </div>
                        <div class="info-card-bottom">
                            <h2 class="c-d2">消息推送</h2>
                            <p class="c-d">通过冰穹消息推送通道，给用户发送通知</p>
                            <button type="button" class="btn btn-default btn-block" onclick="location.href='union-pay.html'">了解详情</button>
                        </div>
                        <div class="cover-img">
                            <img src="../img/index-cover.png"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3 text-center" id="dataStatistic">
                    <div class="info-card havebtn">
                        <div class="info-card-top">
                            <i class="qb-icon main-dev-4"></i>
                        </div>
                        <div class="info-card-bottom">
                            <h2 class="c-d2">数据统计</h2>
                            <p class="c-d">在Android和iOS上使用，应用数据统计服务</p>
                            <button type="button" class="btn btn-default btn-block" onclick="location.href='/merchant/payStatistic.html'">了解详情</button>
                        </div>
                        <div class="cover-img" style="display: none">
                            <img src="../img/index-cover.png"/>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="container-fluid index">
            <div class="row pt pt2">
                <div class="col-xs-12 padding-top20 text-center title">
                    <h1>分发推广服务</h1>
                    <p class="c-d3">Dirstbution Promotion Service</p>
                </div>

                <div class="col-xs-3 col-xs-offset-3 text-center">
                    <div class="info-card  havecover f-fr text-center" style="padding-top: 20px;height: 320px">
                        <div class="info-card-top" style="margin-bottom: 20px;">
                            <i class="qb-icon main-cast-1"></i>
                        </div>
                        <div class="info-card-bottom">
                            <h2 class="c-d2">应用分发</h2>
                            <p class="c-d">应用分发至宝玩及冰趣应用商店，为您带来更多流量</p>
                            <button type="button" class="btn btn-default btn-block" href="audit-2.html">了解详情</button>
                        </div>
                        <div class="cover-img">
                            <img src="../img/index-cover.png"/>
                        </div>
                    </div>
                </div>

                <div class="col-xs-3 text-center">
                    <div class="info-card  havecover" style="padding-top: 30px;;height: 320px">
                        <div class="info-card-top" style="margin-bottom: 12px">
                            <i class="qb-icon main-cast-2"></i>
                        </div>
                        <div class="info-card-bottom">
                            <h2 class="c-d2">渠道推广</h2>
                            <p class="c-d">提供冰穹平台全网渠道标识，提供统一的渠道认证及支付结算</p>
                            <button type="button" class="btn btn-default btn-block" href="audit-2.html">了解详情</button>
                        </div>
                        <div class="cover-img">
                            <img src="../img/index-cover.png"/>
                        </div>
                    </div>
                </div>
            </div>

        </div>


       <%@ include  file="foot.jsp"%>
    </body>
</html>