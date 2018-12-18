<%--
  Created by IntelliJ IDEA.
  User: wuhanxue5@sina.com
  Date: 2018/12/7
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>抽奖设置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../js/webJs/luckDrawSetting.js"></script>
</head>
<body onload="loadPrize()" background="../../image/timg.jpg" style="background-size: cover">
<div style="width: 100%;">
    <a class="btn btn-warning" style="background-color: transparent;color: transparent;border: 0;height: 20px" href="/drawSetting">设置</a>
</div>
<table style="width: 90%;height: 350px;border: 0;margin-top: 10%" align="center">
    <tr>
        <td class="text-center" colspan="2" style="color: #ff5048;font-size: 80px;vertical-align: center">抽奖栏</td>
    </tr>
    <tr>
        <td class="text-center" style="width: 768px">
            <!--几等奖，动态-->
            <p>
                <select id='prizes' onchange="setPrize(this)" style="height: 40px;width: 350px"></select>
            </p>
            <img id="img" style="height: 350px;width: 350px" src="../../image/logo.jpg">
        </td>
        <td class="text-left" style="width: 768px">
            <!--剩余名额，动态-->
            <p style="font-size: 40px"><span id="level"></span>剩余名额：<span id="number"></span></p>
            <p style="font-size: 40px" id="max" hidden>本次抽奖人数：
                <!--select选项，动态-->
                <select id="numberMAX" title="" style="height: 40px;width: 80px;" onchange="setNumber(this)"></select>
            </p>
            <%--<p id="table" hidden>每次抽取桌数：--%>
                <%--<!--select选项，动态-->--%>
                <%--<select id="tableNumber" title="" style="height: 40px;width: 80px;" onchange="setTableNumber(this)">--%>
                    <%--<option value="1">1</option>--%>
                    <%--<option value="2">2</option>--%>
                    <%--<option value="3">3</option>--%>
                    <%--<option value="4">4</option>--%>
                    <%--<option value="5">5</option>--%>
                <%--</select>--%>
            <%--</p>--%>
            <p style="font-size: 40px" id="everyTable" hidden>每桌抽取人数：
                <!--select选项，动态-->
                <select id="everyTableNumber" title="" style="height: 40px;width: 80px;" onchange="setEveryTableNumber(this)">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </p>
            <a class="btn btn-success" style="width: 100px;height: 55px;font-size: 30px;margin-top: 5%" href="luckDraw">抽奖</a>
            <%--<a class="btn btn-warning" style="width: 100px;height: 55px;font-size: 30px" href="drawSetting">设置</a>--%>
        </td>
    </tr>
</table>
</body>
<script>


</script>
</html>
