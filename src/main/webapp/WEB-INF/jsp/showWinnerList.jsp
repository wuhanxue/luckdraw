<%--
  Created by IntelliJ IDEA.
  User: wuhanxue
  Date: 2018/12/6
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>中奖者名单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
</head>
<body onload="loadWinnerList()">
<table style="width: 60%;height: 600px;border: 0" cellpadding="0" cellspacing="0" align="center">
    <thead>
    <!--几等奖，动态-->
    <tr>
        <td class="text-center" colspan="2" style="height: 85px"><p style="font-size: 80px;color: #ff3f4b"><span id="class"
                                                        style="color: #ff3f4b">一</span>等奖
            中奖名单</p></td>
    </tr>
    </thead>
    <tbody id="tBody">

    </tbody>
</table>
<div class="text-center">
    <a class="btn btn-success" id="begin" style="width: 150px;height: 55px;font-size: 30px"
       href="luckDraw">再抽一次</a>
    <a class="btn btn-danger" id="list" href="luckDrawSetting" style="width: 150px;height: 55px;font-size: 30px">下一奖项</a>
</div>
</body>
<script>
    /**
     * 显示中奖者名单
     */
    function loadWinnerList() {
        var i = 0;
        <c:forEach items="${seatList}" var="a">
        i++;
        if (i % 2 === 0) {
            var tr = "<tr>\n" +
                "</tr>";
            $("#tBody").append(tr);
        }
        var td = " <td style=\"height: 100px\">\n" +
            "            <p>               桌号【<span class=\"tableId\">${a.tableId}</span>\n" +
            "                    】&nbsp;&nbsp;座位号【<span class=\"locationId\">${a.locationId}</span>\n" +
            "                    】&nbsp;&nbsp;姓名【<span class=\"name\">${a.name}</span>】\n" +
            "            </p>\n" +
            "            </td>";
        $("#tBody").find("tr:last").append(td);   // 将td 插入到最新的tr中
        </c:forEach>
    }

</script>
</html>
