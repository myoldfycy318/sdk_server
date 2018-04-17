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
    <title>支付宝手机网站支付接口</title>
</head>
<%
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
   /*
   //冰趣渠道
    pairs.add(new BasicNameValuePair("appCode","HD0000024"));
    pairs.add(new BasicNameValuePair("channelCode","CHA000002"));
    pairs.add(new BasicNameValuePair("buyerId","OGIzT0NDRjdFOWs9"));
    */
   //宝玩渠道
    pairs.add(new BasicNameValuePair("appCode","H0000042"));
    pairs.add(new BasicNameValuePair("chargePointAmount","0.01"));
    pairs.add(new BasicNameValuePair("payType","1"));
    pairs.add(new BasicNameValuePair("channelCode","CHA000001"));
    pairs.add(new BasicNameValuePair("p1","163-1711291664382738667"));
    pairs.add(new BasicNameValuePair("chargePointName","充值"));
    pairs.add(new BasicNameValuePair("zoneId","aDRrRllvRzZwT289"));
    pairs.add(new BasicNameValuePair("payOrigin","pc"));
    pairs.add(new BasicNameValuePair("buyerId","NzdGZTBEVGorWGhmaVFPTXNqQ1ZqQT09"));
    pairs.add(new BasicNameValuePair("sign","3b0c4f7470c7813994561053e3866572"));
    pairs.add(new BasicNameValuePair("chargePointCode","C0000000"));
    pairs.add(new BasicNameValuePair("roleId","16600001014678"));
    pairs.add(new BasicNameValuePair("zoneName","哈克龙"));
   String result = ApiConnector.post("http://myoldfycy318.xicp.io/bqpay/createWapOrder",pairs);
   result =  JSONObject.parseObject(JSONObject.parseObject(result).getString("data")).getString("aliWapPage");
    //建立请求
//    String sHtmlText = response.
    out.println(result);
    System.out.println(result);

%>
<div id = "div1">
</div>
<body>
</body>
</html>
