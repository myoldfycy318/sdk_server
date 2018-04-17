    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.http.NameValuePair"%>
<%@ page import="org.apache.http.message.BasicNameValuePair"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dome.sdkserver.util.ApiConnector" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<html>
<head>
    <script src="/js/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>支付宝pc网站支付接口</title>
</head>
<%
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    pairs.add(new BasicNameValuePair("appCode","D0001346"));
    pairs.add(new BasicNameValuePair("chargePointCode","c00001"));
    pairs.add(new BasicNameValuePair("chargePointAmount","1"));
    pairs.add(new BasicNameValuePair("chargePointName","c00001name"));
    pairs.add(new BasicNameValuePair("payOrigin","RC"));
    pairs.add(new BasicNameValuePair("payType","1"));
    pairs.add(new BasicNameValuePair("channelCode","CHA000003"));
    pairs.add(new BasicNameValuePair("zoneId","z001"));
    pairs.add(new BasicNameValuePair("serverId","s001"));
    pairs.add(new BasicNameValuePair("extraCommonParam","123321_xxxx"));
    pairs.add(new BasicNameValuePair("passport","yuchunhai89@foxmail.com"));
    pairs.add(new BasicNameValuePair("buyerId","bq_000050098"));
    pairs.add(new BasicNameValuePair("sign","d33ab804b2d1e53e2ddaab2a16f9fdcc"));
   String result = ApiConnector.post("http://localhost/bqpay/recharge",pairs);
    System.out.println(result);
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
