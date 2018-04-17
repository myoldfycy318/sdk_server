<%@ page import="java.util.List" %>
<%@ page import="com.dome.sdkserver.metadata.entity.bq.ogp.OgpPayEntity" %>
<%@ page import="java.math.BigDecimal" %><%--
  Created by IntelliJ IDEA.
  User: hunsy
  Date: 2017/8/9
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!decrypt html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>充值</title>
</head>
<style>
    body div input {
        margin: 0;
        padding: 0;
        border: 0;
    }

    body {
        background: #1b232e;
        margin: 0;
        padding-top: 50px;
        padding-bottom: 50px;
    }

    .recharge_logo {
        width: 100%;
        height: 130px;
        text-align: center;
        margin-bottom: 35px;
    }

    .recharge_logo > img {
        margin-top: 5px;
        height: 120px;
        width: 100px;
    }

    .recharge_content {
        margin: 0 auto;
        background: #202c3d;
        width: 747px;
        height: 480px;
        padding: 56px 50px 66px 50px;
    }

    .recharge_line_info {
        height: 55px;
        width: 100%;
    }

    .recharge_name {
        display: block;
        float: left;
        height: 55px;
        width: 85px;
        line-height: 55px;
        color: #fff;
        font-size: 16px;
        font-family: "微软雅黑";
    }

    .recharge_desc {
        display: block;
        float: right;
        height: 55px;
        width: 645px;
        line-height: 55px;
        color: #fff;
        font-size: 20px;
        font-family: "微软雅黑";
    }

    .recharge_line {
        height: 1px;
        width: 100%;
        background: #1b232e;
        margin: 25px 0 35px 0;
    }

    .recharge_num {
        display: block;
        float: right;
        min-height: 1px;
        width: 645px;
    }

    .recharge_num_line {
        width: 100%;
        height: 55px;
    }

    .recharge_num_line_sp {
        width: 100%;
        height: 55px;
        margin-top: 20px;
    }

    .recharge_num_info {
        display: block;
        float: left;
        height: 45px;
        width: 98px;
        background: #1a2332;
        margin-right: 9px;
        margin-top: 5px;
        color: #fff;
        font-family: "微软雅黑";
        font-size: 14px;
        text-align: center;
        line-height: 45px;
    }

    .recharge_num_info_sp {
        color: #fd3f59 !important;
        border: 1px solid #fd3f59;
    }

    .recharge_num_line > div:last-child {
        margin-right: 0;
    }

    .recharge_num_input {
        height: 45px;
        width: 160px;
        background: #1a2332;
        padding-left: 20px;
        padding-right: 30px;
        color: #8492a4;
        font-family: "微软雅黑";
        font-size: 14px;
        float: left;
    }

    .recharge_num_line_sp > span {
        color: #8492a4;
        font-family: "微软雅黑";
        font-size: 14px;
        display: block;
        height: 45px;
        line-height: 45px;
        float: left;
    }

    .spanColor {
        color: #fd3f59;
    }

    .recharge_yuan {
        width: 20px;
        float: left;
        position: relative;
        left: -30px;
        top: 11px;
        color: #fff;
        display: none;
    }

    .recharge_alert {
        color: #fd3f59;
        font-family: "微软雅黑";
        font-size: 14px;
        height: 20px;
        line-height: 20px;
        display: none;
    }

    .recharge_alert > span {
        display: block;
        line-height: 20px;
        height: 20px;
        float: left;
    }

    .recharge_alert > img {
        height: 20px;
        width: 20px;
        float: left;
    }

    .recharge_confirm {
        margin-top: 35px;
    }

    .recharge_confirm > button {
        height: 45px;
        width: 140px;
        background: #fd3f59;
        border: 0;
        line-height: 45px;
        color: #fff;
        font-size: 18px;
        font-family: "微软雅黑";
    }

    :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
        color: #8492a4;
        font-family: "微软雅黑";
        font-size: 14px;
    }

    ::-moz-placeholder { /* Mozilla Firefox 19+ */
        color: #8492a4;
        font-family: "微软雅黑";
        font-size: 14px;
    }

    input:-ms-input-placeholder {
        color: #8492a4;
        font-family: "微软雅黑";
        font-size: 14px;
    }

    input::-webkit-input-placeholder {
        color: #8492a4;
        font-family: "微软雅黑";
        font-size: 14px;
    }

    .wrap {
        width: 300px;
        background: #eee;
        border: 2px solid #ddd;
    }

    label {
        display: inline-block;
        width: 60px;
        height: 60px;
        background: url(./img/alert.png);
        background-size: 100% 100%;
        background-position: 0 0px;
        -webkit-transition: background 0.5s linear;
    }

    .inputNum {
        display: none;
    }
</style>
<body>
<div class="recharge_logo"><img src="/page/img/logo.png"></div>
<div class="recharge_content">
    <div class="recharge_line_info">
        <div class="recharge_name">充值游戏：</div>
        <div class="recharge_desc">${gameName}</div>
    </div>
    <div class="recharge_line_info">
        <div class="recharge_name">充值区服：</div>
        <div class="recharge_desc">${zoneName}</div>
    </div>
    <%
        List<Object> ls = (List<Object>) request.getAttribute("ls");
    %>
    <div class="recharge_line"></div>

    <div>
        <div class="recharge_name">金额选择：</div>
        <div class="recharge_num">
            <%
                int size = ls.size();
                int lines;
                if (size % 5 == 0) {
                    lines = size / 5;
                } else {
                    lines = size / 5 + 1;
                }
                int statrt = 0;
                for (int j = 0; j < lines; j++) {
            %>
            <div class="recharge_num_line">
                <%
                    int end;
                    if (statrt + 5 > size) {
                        end = size;
                    } else {
                        end = statrt + 5;
                    }
                    for (int i = statrt; i < end; i++) {
                        int yuan = Integer.valueOf(ls.get(i).toString());

                %>
                <input type="checkbox" id="checkbox<%out.print(i);%>" name="selectNum" class="inputNum"
                       value="<%out.print(yuan);%>">
                <label for="checkbox<%out.print(i);%>" class="recharge_num_info" val="<%out.print(yuan);%>"><%
                    out.print(yuan);%>元</label>
                <%
                    }
                    statrt = 5 * (j + 1);
                %>
            </div>
            <%}%>
            <div class="recharge_num_line_sp">
                <input type="number"
                       onkeyup="value=(parseInt((value=value.replace(/\D/g,''))==''||parseInt((value=value.replace(/\D/g,''))==0)?'':value,10))"
                       onafterpaste="value=(parseInt((value=value.replace(/\D/g,''))==''||parseInt((value=value.replace(/\D/g,''))==0)?'':value,10))"
                       class="recharge_num_input" maxlength="5" placeholder="其他金额（元）">
                <div class="recharge_yuan">元</div>
                <span id="spanAlertErr">&nbsp;&nbsp;&nbsp;&nbsp;请输入1-10000之间的整数</span>
            </div>
            <div class="recharge_alert"><img src="/page/img/alert.png"><span>&nbsp;&nbsp;请选择或输入金额后确认</span></div>
            <div class="recharge_alert"><img src="/page/img/alert.png"><span>&nbsp;&nbsp;请输入1-10000之间的金额</span></div>
            <div class="recharge_confirm">
                <button onclick="confirmRecharge()">确认</button>
            </div>
            <form id="payForm" action="/web/pay" method="post" enctype="application/x-www-form-urlencoded">
<%--                <input type="hidden" name="appCode" value="${prepareEntity.appCode}">
                <input type="hidden" name="zoneId" value="${prepareEntity.zoneId}">
                <input type="hidden" name="userId" value="${prepareEntity.userId}">
                <input type="hidden" name="zoneName" value="${prepareEntity.zoneName}">
                <input type="hidden" name="cpTradeNo" value="${prepareEntity.cpTradeNo}">
                <input type="hidden" name="cpTradeTime" value="${prepareEntity.cpTradeTime}">
                <input type="hidden" name="content" value="${prepareEntity.content}">
                <input type="hidden" name="detail" value="${prepareEntity.detail}">
                <input type="hidden" name="attach" value="${prepareEntity.attach}">
                <input type="hidden" name="channelCode" value="${prepareEntity.channelCode}">
                <input type="hidden" name="roleId" value="${prepareEntity.roleId}">
                <input type="hidden" name="ts" value="${prepareEntity.ts}">
                <input type="hidden" name="sign" value="${prepareEntity.sign}">--%>
                <input type="hidden" name="webpayKey" value="${webpayKey}">
                <input type="hidden" name="totalFee" value="">
            </form>
        </div>
    </div>
</div>
</body>
<script>
    var gameName = document.getElementsByClassName('recharge_desc');
    var numInputSel = document.getElementsByClassName("inputNum")
    var numSelect = document.getElementsByClassName('recharge_num_info');
    var numInput = document.getElementsByClassName('recharge_num_input');
    var rechargeAlert = document.getElementsByClassName('recharge_alert');
    var rechargeYuan = document.getElementsByClassName('recharge_yuan');
    var spanAlertErr = document.getElementById("spanAlertErr");
    var totalFeeInp = document.getElementsByName("totalFee")[0];

    for (var i = 0; i < numSelect.length; i++) {
        numSelect[i].onclick = function () {
            totalFeeInp.value = '';
            rechargeAlert[0].style.display = "none";
            rechargeAlert[1].style.display = "none";
            numInput[0].value = '';
            removeClass(numInput[0], 'recharge_num_info_sp');
            for (var j = 0; j < numSelect.length; j++) {
                removeClass(numSelect[j], 'recharge_num_info_sp');
            }
            addClass(this, 'recharge_num_info_sp');
            totalFeeInp.value = this.getAttribute("val") * 100;
        }
    }
    numInput[0].onclick = function () {
        rechargeAlert[0].style.display = "none";
        rechargeAlert[1].style.display = "none";
        rechargeYuan[0].style.display = "block"
        for (var j = 0; j < numSelect.length; j++) {
            removeClass(numSelect[j], 'recharge_num_info_sp');
        }
        addClass(this, 'recharge_num_info_sp');
    }
    numInput[0].onblur = function () {
        if (numInput[0].value > 10000 || numInput[0].value <= 0 || isNaN(numInput[0].value)) {
            spanAlertErr.style.color = "#fd3f59";
        } else {
            spanAlertErr.style.color = "#8492a4";
        }
        totalFeeInp.value = this.value * 100;
    }
    //确定
    function confirmRecharge() {

        var tf = totalFeeInp.value;
        if (tf == '' || tf == undefined || tf == 0) {
            rechargeAlert[0].style.display = "block";
            return;
        }
        if (tf > 10000 * 100 || tf * 100 < 1*100) {
            rechargeAlert[1].style.display = "block";
            totalFeeInp.value = "";
            numInput[0].value = "";
//            alert("请输入1-10000之间的金额")
            return;
        }

        document.getElementById("payForm").submit();

//        var arr = [];
//        for (var j = 0; j < numSelect.length; j++) {
//            arr.push(hasClass(numSelect[j], 'recharge_num_info_sp'));
//        }
//        arr.push(hasClass(numInput[0], 'recharge_num_info_sp'));
//        console.log(arr);
//        for (var i = 0; i < arr.length; i++) {
//            if (arr[i] == true) {
//                if (i < arr.length - 1) {
//                    var recharge = numSelect[i].innerHTML;
//                } else {
//                    var recharge = numInput[0].value;
//                    if (numInput[0].value > 10000 || numInput[0].value <= 0 || isNaN(numInput[0].value)) {
//                        spanAlertErr.style.color = "#fd3f59";
//                        return;
//                    }
//                }
//                alert("开启充值！" + recharge);
//                return;
//            }
//        }
//        rechargeAlert[0].style.display = "block";
    }
    function hasClass(elem, cls) {
        cls = cls || '';
        if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
        return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
    }
    function addClass(elem, cls) {
        if (!hasClass(elem, cls)) {
            elem.className = elem.className == '' ? cls : elem.className + ' ' + cls;
        }
    }
    function removeClass(elem, cls) {
        if (hasClass(elem, cls)) {
            var newClass = ' ' + elem.className.replace(/[\t\r\n]/g, '') + ' ';
            while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                newClass = newClass.replace(' ' + cls + ' ', ' ');
            }
            elem.className = newClass.replace(/^\s+|\s+$/g, '');
        }
    }
</script>
</html>


