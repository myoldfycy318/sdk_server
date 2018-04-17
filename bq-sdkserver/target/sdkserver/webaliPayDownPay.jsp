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
    pairs.add(new BasicNameValuePair("appCode",request.getParameter("appCode")));
    pairs.add(new BasicNameValuePair("zoneId",request.getParameter("zoneId")));
    pairs.add(new BasicNameValuePair("roleId",request.getParameter("roleId")));
    pairs.add(new BasicNameValuePair("userId",request.getParameter("userId")));
    pairs.add(new BasicNameValuePair("cpTradeNo",request.getParameter("cpTradeNo")));
    pairs.add(new BasicNameValuePair("cpTradeTime",request.getParameter("cpTradeTime")));
    pairs.add(new BasicNameValuePair("channelCode",request.getParameter("channelCode")));
    pairs.add(new BasicNameValuePair("ts",request.getParameter("ts")));
    pairs.add(new BasicNameValuePair("totalFee",request.getParameter("totalFee")));
    pairs.add(new BasicNameValuePair("sign",request.getParameter("sign")));
    pairs.add(new BasicNameValuePair("content",request.getParameter("content")));
    pairs.add(new BasicNameValuePair("roleName",request.getParameter("roleName")));
    pairs.add(new BasicNameValuePair("zoneName",request.getParameter("zoneName")));
    pairs.add(new BasicNameValuePair("attach",request.getParameter("attach")));
    pairs.add(new BasicNameValuePair("tradeFee","1"));
    pairs.add(new BasicNameValuePair("payType","1"));

    String result = ApiConnector.post("http://myoldfycy318.xicp.io//web/pay/create",pairs);
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
