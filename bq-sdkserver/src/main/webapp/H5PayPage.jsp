<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.http.NameValuePair"%>
<%@ page import="org.apache.http.message.BasicNameValuePair"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dome.sdkserver.util.ApiConnector" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
    <script src="/js/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>支付宝手机网站支付接口</title>
</head>
<%
    // appCode=HD0000024
    // &item=道具
    // &p1=p1_1_测试
    // &p2=p2_2_测试
    // &price=10
    // &userId=bq_000000039&
    // zoneId=1&
    // sign=caa6739741fb89c69bc47861c00c181c
    request.setAttribute("appCode","HD0000029");
    request.setAttribute("channelCode","CHA000002");
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    pairs.add(new BasicNameValuePair("appCode","HD0000029"));
    pairs.add(new BasicNameValuePair("item","道具"));
    pairs.add(new BasicNameValuePair("p1","p1_1"));
    pairs.add(new BasicNameValuePair("p2", "p2_2"));
    pairs.add(new BasicNameValuePair("price","0.01"));
    pairs.add(new BasicNameValuePair("userId","bq_000000039"));
    pairs.add(new BasicNameValuePair("zoneId","1"));
    pairs.add(new BasicNameValuePair("sign","80258b43242562af2f5303ad77bf708c"));
   String result = ApiConnector.get("http://sdkserver.domestore.cn/h5game/payinfo", pairs,"utf-8");
    out.println(result);
    System.out.println(result);
%>
<div id = "div1">
</div>
<body>
</body>
</html>
