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
    pairs.add(new BasicNameValuePair("resFormat","1"));
    pairs.add(new BasicNameValuePair("extraCommonParam","orderId12_userId123"));
    pairs.add(new BasicNameValuePair("reqIp","127.0.0.1"));
    pairs.add(new BasicNameValuePair("outOrderNo","200"));
    pairs.add(new BasicNameValuePair("buyerId","3970728"));
    pairs.add(new BasicNameValuePair("totalFee","1"));
    pairs.add(new BasicNameValuePair("notifyUrl","http://hksdk.domestore.cn/notify/gameInter"));
    pairs.add(new BasicNameValuePair("returnUrl","http://hksdk.domestore.cn/return.html"));
    pairs.add(new BasicNameValuePair("payOrigin","TBE"));
    pairs.add(new BasicNameValuePair("subject", org.apache.commons.codec.binary.Base64.encodeBase64String("商品名称111".getBytes("utf-8"))));
    pairs.add(new BasicNameValuePair("body", org.apache.commons.codec.binary.Base64.encodeBase64String("交易标题12".getBytes("utf-8"))));
    pairs.add(new BasicNameValuePair("signCode","pe9ty6Vs9jueoWIDcUpB0aVYDL/7xU7uyiw21ghiJ7qkkra1f0QEHZBmRj1BJ7KQZDeC++ylEkwFe0yI9Xy1tl8HYar4IERnH9AjBtpfC1EnbsUK+mfrt8wBPbgi3Nn3QPViZa1AqzgkSUb9KiR3os5qLAyGeqtG3A0jgRxtvYs="));
   String result = ApiConnector.post("http://hksdk.domestore.cn/pay/aliPayDown",pairs);
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
