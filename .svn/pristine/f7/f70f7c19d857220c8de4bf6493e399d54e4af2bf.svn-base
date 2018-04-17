<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
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
		<script src="../js/jquery.pager.js"></script>
		
		<script src="../js/jquery.query.js"></script>
		<script src="../js/zclip/jquery.zclip.js"></script>
		<script src="../js/jquery.placeholder.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/base.css">
        <link rel="stylesheet" type="text/css" href="../css/qianbao.css">
        <!--[if lt IE 9]>
		<script src="../js/html5shiv.min.js"></script>
		<script src="../js/respond.min.js"></script>
        <![endif]-->
		<script>
            $(function(){
				/*分页*/
				//$('[data-toggle="popover"]').popover();
				queryApp(1);

				$(".menu li:not(:first)").bind("mouseover",function(){
					$(".menu li:not(:first)").removeClass("hover");
					$(this).addClass("hover");
				}).bind("mouseout",function(){
					$(this).removeClass("hover");
				}).bind("click",function(){
					window.location = $(this).find("a").attr("href");
				});

				$("#rsaPrivateKeyPanel").on('shown.bs.modal', function () {
					$('.copyText').zclip({
						path:'../js/zclip/ZeroClipboard.swf',
						copy:function(){return $('.keyContainer').text();},
						afterCopy:function(){alert("复制成功");}
					});
					fixModalClose($("#rsaPrivateKeyPanel"));
				});
				
            });

			/*新增应用*/
			function addApp(){
				$("#modal-add-app").modal("show");
			}

			/*接入申请*/
			function accessReq(){
				$("#access-req").modal("show");
			}

			/*申请上线*/
			function online(){
				$("#online-req").modal("show");
			}
			//查询APP
			function queryApp(pageNum){
				$("#list-container").html("");
				$.get("/usercenter/list.html",{"pageNum":pageNum},function(response){
					if(response == undefined || response.list == undefined){
						alert("页面会话失效，请刷新页面重试");
						return;
					}
					for(var i = 0;i<response.list.length;i++){
						
						var item = response.list[i];
						var tr = $($("#list-item-template table tbody").html());	
						var tds = tr.children();
						tr.addClass(i%2 > 0?"even":"odd").attr("appId",item.appId);
						//TODO 需要根据实际接口取值
						tds.eq(0).html(item.appName);
						tds.eq(1).html(item.appId);
						//tds.eq(3).html(item.secretKey);
						var asyncPublicKey = item.secretKey;
						if(!asyncPublicKey){
							tds.eq(3).html("");
						}else{
							var a = $("<a href='javascript:;'>"+ asyncPublicKey.substring(0,30) +"...</a>");
							tds.eq(3).html(a);
							$(a).data("item",item);
							a.bind("click",function(){
								var item = $(this).data("item");
								$(".keyContainer").html(item.secretKey);
								$("#rsaPrivateKeyPanel").modal().show();
								$(".modal-title").html("支付回调公钥");
							});
						}
						var privatekey = item.privateKey;
						if(!privatekey){
							tds.eq(2).html("");
						}else{
							var a = $("<a href='javascript:;'>"+ privatekey.substring(0,30) +"...</a>");
							tds.eq(2).html(a);
							$(a).data("item",item);
							a.bind("click",function(){
								var item = $(this).data("item");
								$(".keyContainer").html(item.privateKey);
								$("#rsaPrivateKeyPanel").modal().show();
								$(".modal-title").html("支付私钥");
							});
						}
						$("#list-container").append(tr);
						
					}
					/*分页*/
					$("#pager").pager({ pagenumber:pageNum, totalCount: response.count,limit:10,tips:$("#pagerTips"), buttonClickCallback: function(pageIndex){
						queryApp(pageIndex);
					}});
				},"JSON");
				
			}
        </script>
    </head>
    <body class="bg-gray">
        <%@ include  file="headNew.jsp"%>

        <div class="container no-padding-left">
            <div class="row">
                <ol class="breadcrumb">
                    <span class="text-muted">您的位置：</span>
                    <li class="text-muted">个人账号</li>
                    <li class="text-muted">用户中心</li>
                </ol>
            </div>
        </div>

        <div class="container app app-center bg-white text-center">
            
			<div class="content-list center-block text-left">
				<div class="row no-padding-left">
					<div class="col-xs-12" >
						<div class="row">
							<div class="col-xs-12">
								<div class="info">
									<h4 class="c-d2 f-fs2"><strong>商户ID：${merchantId}</strong></h4>
									<span class="text-muted f-fs1">注：商户ID为您在钱宝开放平台的唯一凭证，请妥善保管</span>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top:30px">
							<div class="col-xs-12">
								<table class="table ">
									<thead>
										<tr>
											<th style="width: 25%;">应用名称</th>
										    <th style="width: 15%;">AppID</th>
										  	<th style="width: 25%;">支付私钥</th>
										  	<th style="width: 35%;">支付回调公钥</th>
										</tr>
									</thead>
									<tbody id="list-container">
										
									</tbody>
								</table>
							</div>
							<div class="col-xs-6 text-left padding-top25">
								<p id="pagerTips" class="c-d"></p>
							</div>
							<div  class="col-xs-6 text-right">
								<div class="pagination pagination-sm">
								<ul id="pager" class="pager"></ul>
							</div>
						</div>
						
						
						</div>
						<div class="row" style="margin-top:36px">
						<div class="col-xs-12">
						</div>
					</div>
					</div>
				</div>
			</div>
        </div>

		<div class="modal fade app-info modal-access" id="rsaPrivateKeyPanel">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header -d2">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><img src="../img/close.png"/></button>
						<h4 class="modal-title">rsaPrivateKey</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-xs-12">
								<form class="form-horizontal">
									<div class="form-group" style="word-wrap:break-word">
										<div class="keyContainer">+iZQWhgFosev6O4KZNGakZ6luE0WoeZvByb3riwF2ByWnSzmDDqfYn6hjfltANQ2w8nLIjeg5MSDOmr5VnePTxe5cQlHklgyDZtx0OXqIDaTeCvcAtpNvezeEEsXbF8FKObyNgdMDWWVnAgMBAAECgYArv49Fl7t6OKcobroBopgzEP+hqESFehgJ8DjK6fnpu/jWjHH5K3fiIjms5KYj3Qk1V8ZidnqPZ6bETKkNhorQ180TH6EKcpdlUOVJnrL3Z8BFqPJnKXeL/0UXDM2EqcxSd4y9QYQcG1tjFlBzKdwUWW7QeldK7nq0Bp1nwxsCkQJBAM3inHSiweCmBEWjp6O6u7woq3q4eEOoNmW2hZEKzxJciTcigMMT4wuyQz6o5rV6B5afkM0FLNnIT2G9+mEo0bMCQQCxIp1vTKqoXDSbWt4OUbWa6I3QsayjshcFXRY0ZgDGyP4X7rG/DmS+lhUlJaVE0D0PhkNC1mYQxNY+5ybarnt9AkAPz6xmRK2Q/A7t7jCMoWWTgKgSp6IMyBlNXGQLjIH0jgi3pD2xPZC9MDZ71GrLWxDEH4L2S8RzATAfeICG/6SDAkAxMQO3nPrntnOFkT6Ji3d7hYmcozrmWta5zpgUBR/CFJ871jXWcRDZPKSypDm5WnsajJaxQQknLwKwhVDs+fehAkEAyHN31yyqAoW1bkVuugtk1Qltr6B7mEGcJEYrVCpLGXhvvgxWKsw7Y7OUD2o3Cu2/jeKNfrb6V0y0zZChWM2cOQ==</div>
									</div>
								</form>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 text-center modal-btn">
								<input  type="button" class="btn btn-sm btn-primary copyText" value="复制"></input >
							</div>
						</div>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		<!--隐藏模板-->
		<div id="list-item-template" class="hide">
			<table>
				<tr class="">
					<td scope="row">应用名称</th>
					<td>54148932</td>
					<td>fsdf65565</td>
					<td>ljasodfopasdfbasdfasdf</td>
				</tr>
			</table>
		</div>
        <%@ include  file="foot.jsp"%>

    </body>
</html>