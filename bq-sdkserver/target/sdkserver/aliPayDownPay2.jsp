<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.http.NameValuePair"%>
<%@ page import="org.apache.http.message.BasicNameValuePair"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dome.sdkserver.util.ApiConnector" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.dome.sdkserver.metadata.entity.bq.pay.WeChatEntity" %>
<%@ page import="com.dome.sdkserver.biz.utils.WechatPayUtil" %>
<html>
<head>
    <script src="/js/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>支付宝pc网站支付接口</title>
</head>
<%
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    pairs.add(new BasicNameValuePair("resFormat","1"));
    String orderNo = request.getParameter("orderNo");
    pairs.add(new BasicNameValuePair("orderNo",orderNo));
    pairs.add(new BasicNameValuePair("payType","1"));
    String ip = WechatPayUtil.getRemoteHost(request);
    pairs.add(new BasicNameValuePair("reqIp",ip));
    String result = ApiConnector.post("http://myoldfycy318.xicp.io/web/pay/defray",pairs);
    System.out.println("2222.......................\n"+result);
    result =  JSONObject.parseObject(JSONObject.parseObject(result).getString("data")).getString("form");
    //建立请求
//    String sHtmlText = response.
//    out.println(result);
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    response.getWriter().write(result);

%>
<div id = "div1">
</div>
<body>
</body>
</html>
