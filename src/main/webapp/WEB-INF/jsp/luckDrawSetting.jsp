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
<body onload="loadPrize()">
<table style="width: 80%;height: 350px;border: 0" align="center">
    <tr>
        <td class="text-center" colspan="2" style="color: #ff5048;font-size: 30px">抽奖栏</td>
    </tr>
    <tr>
        <td class="text-center" style="width: 313px">
            <!--几等奖，动态-->
            <p>
                <select id='prizes' onchange="setPrize(this)" style="height: 30px"></select>
            </p>
            <img id="img" style="height: 200px;width: 200px" src="../../image/logo.jpg">
        </td>
        <td class="text-center" style="width: 313px">
            <!--剩余名额，动态-->
            <p><span id="level"></span>剩余名额：<span id="number"></span></p>
            <p>本次抽奖人数：
                <!--select选项，动态-->
                <select id="numberMAX" title="" style="height: 40px;width: 80px;" onchange="setNumber(this)"></select>
            </p>
            <a class="btn btn-success" style="width: 100px;height: 55px;font-size: 30px" href="luckDraw">抽奖</a>
            <a class="btn btn-warning" style="width: 100px;height: 55px;font-size: 30px" href="/drawSetting">设置</a>
        </td>
    </tr>
</table>
</body>
<script>


</script>
</html>
