<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <script src="/gamepay/js/jquery-1-28e7718a8b.11.3.min.js" type="text/javascript"></script>
</head>
<body onload="javascript:pay();">
<script type="text/javascript">
    function pay() {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
    }
    function onBridgeReady() {
        var gameUrl = '<%=request.getParameter("sdkdomain")%>/h5game/entry?userId=<%=request.getParameter("userId")%>&appCode=<%=request.getParameter("appCode")%>&platformCode=<%=request.getParameter("platformCode")%>';
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": '<%=request.getParameter("appId")%>',     //公众号名称，由商户传入
                "timeStamp": '<%=request.getParameter("timeStamp")%>',//时间戳，自1970年以来的秒数
                "nonceStr": '<%=request.getParameter("nonceStr")%>',//随机串
                "package": '<%=request.getParameter("package")%>',
                "signType": '<%=request.getParameter("signType")%>',//微信签名方式:
                "paySign": '<%=request.getParameter("paySign")%>',//微信签名
            }, function (res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    //alert("微信支付成功!");
                    window.location.href = gameUrl;
                } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
//                    alert("用户取消支付!");
                    window.location.href = gameUrl;
                } else {
                    //alert(JSON.stringify(res));
//                    alert("支付失败!");
                    window.location.href = gameUrl;
                }
            });
    }
</script>


</body>
</html>

