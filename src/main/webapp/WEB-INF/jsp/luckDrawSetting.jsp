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
<body onload="loadPrize()">
<table style="width: 60%;height: 600px;border: 0" align="center">
    <tr>
        <td class="text-center" colspan="2" style="color: #ff5048">抽奖栏</td>
    </tr>
    <tr>
        <td class="text-center" style="width: 30%">
            <!--几等奖，动态-->
            <p>
                <select id='prizes' onchange="setPrize(this)">
                    <c:forEach items="${prizeList}" var="a">
                        <option value='${a.id}'>${a.level}：${a.name}</option>
                    </c:forEach>
                </select>
            </p>
            <img id="img" style="height: 200px;width: 200px" src="../../image/jdlink.jpg">
        </td>
        <td class="text-center" style="width: 30%">
            <!--剩余名额，动态-->
            <p><span id="level"></span>剩余名额：<span id="number"></span></p>
            <p>本次抽奖人数：
                <!--select选项，动态-->
                <select id="numberMAX" title="" style="height: 40px;width: 80px;" onchange="setNumber(this)">

                </select>
            </p>
            <a class="btn btn-success" style="width: 100px;height: 55px;font-size: 30px" href="luckDraw">抽奖</a>
        </td>
    </tr>
</table>
</body>
<script>
    /**
     * 页面初始化设置
     * */
    function loadPrize() {
        $("#prizes").get(0).selectedIndex = -1;  //默认选中
    }

    /**
     * 选择奖品后设置奖品图片，
     */
    function setPrize(item) {
        var id = parseInt($(item).find("option:selected").val());  // 获取选中奖品的剩余数量
        var number = 0; //奖品数量
        var prizeLevel = "";
        var prizeName = "";
        <c:forEach items="${prizeList}" var="b">
        var id1 = ${b.id};
        if (id1 === id) {
            number = ${b.number};  //剩余奖品数
            <%--prizeLevel = ${b.level};  // 奖品等级--%>
            <%--prizeName = ${b.name};    // 奖品名字--%>
            $("#level").text("${b.name}");         // 设置奖品等级
            $("#img").attr("src", "../../image/${b.imgUrl}");  // 设置奖品图片
        }
        </c:forEach>
        // localStorage.prizeLevel = prizeLevel;  // 奖品等级
        // localStorage.prizeName = prizeName;    // 奖品名字
        // localStorage.prizeId = id;             // 奖品编号
        $("#numberMAX").children().remove(); //删除旧数据
        for (var i = 1; i < number + 1; i++) { // 根据奖品数插入抽奖人数下拉框
            var option = "<option value='" + i + "'>" + i + "</option>";
            $("#numberMAX").append(option);
        }
        $("#number").text(number);   // 设置奖品剩余名额
    }

    /**
     * 设置抽奖人数并将其传递到下一页面
     * @param item
     */
    function setNumber(item) {
       // localStorage.winnerNumber = parseInt($(item).find("option:selected").val());  // 将抽奖人数传递到下一页面
    }
</script>
</html>
