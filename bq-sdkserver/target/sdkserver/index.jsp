<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<html>
<body>
<h2>Hello World!</h2>

<br/>

<form target="_blank" action="/h5game/payinfo" method="post">
    userId :<input type="text" name="userId"/> <br/>
    appCode:<input type="text" name="appCode"/> <br/>
    zoneId :<input type="text" name="zoneId"/> <br/>
    price :<input type="text" name="price"/> <br/>
    道具 :<input type="text" name="item"/> <br/>
    区服 :<input type="text" name="zoneName"/> <br/>
    p1 :<input type="text" name="p1"/> <br/>
    p2 :<input type="text" name="p2"/> <br/>
    签名 :<input type="text" name="sign"> <br/>
    <input type="submit" value="支付">
</form>
======================
<form target="_blank" action="/h5game/entry" method="post">
    userId :<input type="text" name="userId"/> <br/>
    appCode:<input type="text" name="appCode"/> <br/>
    <input type="submit" value="登录">
</form>
================================
支付宝Wap支付<br/>

<div id="main">
    <form name=alipayment action=aliWapPay.jsp method=post target="_blank">
        <button class="new-btn-login" type="submit" style="text-align:center;">支付宝Wap支付</button>
    </form>
</div>

================================
调H5支付页面
<div id="main1">
    <form name=alipayment action=H5PayPage.jsp method=post target="_blank">
        <button class="new-btn-login" type="submit" style="text-align:center;">调H5支付页面</button>
    </form>
</div>
<br/>
</body>
</html>
================================
外部调ali即时支付页面
<div id="main1">
    <form name=alipayment action=aliPayDownPay.jsp method=post target="_blank">
        <button class="new-btn-login" type="submit" style="text-align:center;">调H5支付页面</button>
    </form>
</div>
<br/>
</body>
</html>
