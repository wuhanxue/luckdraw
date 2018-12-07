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
</head>
<body>
<table style="width: 60%;height: 600px;border: 0" align="center">
    <tr>
        <td class="text-center" colspan="2" style="color: #ff5048">抽奖栏</td>
    </tr>
    <tr>
        <td class="text-center" style="width: 30%">
            <!--几等奖，动态-->
            <p><span></span>等奖--TCL55寸彩电</p>
            <img style="height: 200px;width: 200px" src="../../image/TV.jpg">
        </td>
        <td class="text-center" style="width: 30%">
            <!--剩余名额，动态-->
            <p>五等奖剩余名额：<span></span></p>
            <p>本次抽奖人数：
                <!--select选项，动态-->
                <select title="" style="height: 40px;width: 80px;">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                </select>
            </p>
            <a class="btn btn-success" style="width: 100px;height: 55px;font-size: 30px" href="luckDraw" target="_blank">抽奖</a>
        </td>
    </tr>
</table>
</body>
</html>
