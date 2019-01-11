<%--
  Created by IntelliJ IDEA.
  User: wuhanxue5@sina.com
  Date: 2018/12/5
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>佳利达抽奖系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../../css/style.css">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../js/webJs/luckDraw.js"></script>
    <style>
        body {
            font-size: 20px;
        }
    </style>
</head>
<body onload="loadNumber()" background="../../image/timg.jpg" style="background-size: cover">
<%--<div class="luck-content ce-pack-end" style="width: 80%;margin-left: -5%;padding: 0;margin-top: -50px"><!--透明框-->--%>
<%--<div style="overflow-y: scroll;height: 500px">--%>
<table id="table" style="width: 100%;height: 55%;border: 0;margin-top: 10%" border="0" cellspacing="0"
       cellpadding="0">
    <tr id="class1">
        <!--几等奖，动态-->
        <td class="text-center" colspan="2">
            <span id="prize" style="color: #ff3f4b;font-size: 45px">一等奖</span>
        </td>
    </tr>
</table>
<%--提示面板--%>
<div class="panel panel-default" id="newPanel" hidden>
    <div class="panel-body">
        <span id="message" class="text-center"></span>
    </div>
    <div class="panel-footer">
        <button type="button" class="btn btn-danger" onclick="closed()">关闭</button>
    </div>
</div>
<div class="text-center" style="margin-top: 5%">
    <a class="btn btn-success" id="begin" style="width: 100px;height: 55px;font-size: 30px"
       onclick="beginRndNum()">开始</a>
    <a class="btn btn-danger" id="list" onclick="save();" style="width: 100px;height: 55px;font-size: 30px">名单</a>
</div>
<%--</div>--%>
<%--</div>--%>
</body>
<script>
    /**
     * 根据抽奖人数加载抽奖框
     */
    function loadNumber() {
        $("#list").hide();  // 按钮隐藏
        list = [];  // 清零
        tableList = [];
        listWinner = [];
        tableNumber = parseInt(localStorage.tableNumber);   // 每次抽取桌数
        <c:forEach items="${seatList}" var="a">
        var tableId = ${a.tableId};
        var locationId = ${a.locationId};
        if(localStorage.prizeLevel !== "五等奖" ) {
            if(tableId !== 1 && tableId !== 2 && tableId !== "1" && tableId !== "2") {
                list.push([tableId, locationId]);
            }
        }else{
            list.push([tableId, locationId]);
        }
        var add1 = false;
        for (k1 in tableList) {  //检查桌号LIST中是否存在该桌号
            if (tableList[k1] === tableId) {
                add1 = true;
                break;
            }
        }
        if (!add1) {  // 如果不存在添加
            tableList.push(tableId);
        }
        </c:forEach>
        console.log("等级："+localStorage.prizeLevel);
        console.log("list:");
        console.log(list);
        if (localStorage.prizeMode === "1" || localStorage.prizeMode === "2") { // 随机抽奖,桌位抽奖
            console.log("抽奖人数：");
            console.log(localStorage.winnerNumber);
            winnerNumber = localStorage.winnerNumber;  // 获取抽奖人数
            // if(localStorage.prizeMode === "1"){
            $("#prize").text(localStorage.prizeLevel + "：" + localStorage.prizeName); // 设置奖品等级和名称
            // }else {
            //     randomSetModeTwoPrize();  // 获取随机奖品
            //     beginRndNum1();
            //     setTimeout(function () {     // 随机奖品
            //         beginRndNum1();
            //     }, 3000); //
            //
            // }
            add = false;  // 将新增状态设置为未新增
            // 获取所有参与抽奖的员工数据
            if (list.length === 0) {
                $("#message").text("请重置中奖人！");
                $('#newPanel').show(1000);
                setTimeout(function () {  // 三秒后自动跳转至抽奖设置页面
                    window.location.href = "luckDrawSetting";
                }, 3000);
            } else if (winnerNumber > list.length) { // 当抽奖人数大于参加人数时自动赋值为参加人数
                $("#message").text("中奖人数大于参加人数");
                $('#newPanel').show(1000);
                winnerNumber = list.length;
            }
            $("#class1").nextAll().remove();  // 删除旧数据
            for (var i = 0; i < winnerNumber; i++) { // 根据中奖人数插入抽奖框
                if (i % 2 === 0) {
                    var tr = "<tr >\n" +
                        "</tr>";
                    $("#table").append(tr);
                }
                var td = "<td class=\"text-center\" style='color: #030101'>\n" +
                    "<div class=\"slot\"><span style='font-size: 55px;margin-right: 15px;color: #030101'>桌号：</span><span title=\"\" id='tableId" + i + "' style='display: inline-block;margin: 0;width: 45px;font-size: 55px'>--</span>\n" +
                    "<span style='font-size: 55px;margin-left: 15px;color: #030101'>&nbsp;&nbsp;座号：</span><span title=\"\" id='locationId" + i + "' style='display: inline-block;margin: 0;width: 45px;font-size: 55px'>--</span>\n" +
                    "</div></td>";
                $("#table").find("tr:last").append(td);   // 将td 插入到最新的tr中
                $("#list").text("名单");
            }
            $.ajax({   // 获取桌号数组为随机中奖不超过1次的桌号
                type: "POST",
                url: "getLessTwoWinTableList",
                async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
                dataType: "json",
                success: function (result) {
                    if (result != null && result.data != null && result.status === "success") {
                        tableList = [];   // 清空
                        tableList = result.data;    // 赋值
                    }
                }
            });
        } else {  //按桌抽奖
            $("#prize").text(localStorage.prizeLevel + "：" + localStorage.prizeName);  // 设置奖品等级和名称
            if(parseInt(localStorage.tableNumber) > -1) { // 如果不是全部抽奖
                $.ajax({   // 获取按桌抽取未中奖的桌号数组
                    type: "POST",
                    url: "getNotWinTableList",
                    async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
                    dataType: "json",
                    success: function (result) {
                        if (result != null && result.data != null && result.status === "success") {
                            tableList = [];   // 清空
                            tableList = result.data;    // 赋值
                        }
                    }
                });
            }
            for (var j = 0; j < localStorage.everyTableNumber; j++) { // 每桌抽取人数
                if (j % 2 === 0) {
                    var tr = "<tr>\n" +
                        "</tr>";
                    $("#table").append(tr);
                }
                var td = "<td class=\"text-center\">\n" +
                    "<span style='font-size: 55px;margin-left: 15px;color: black'>座号：</span><span title=\"\" id='locationId" + j + "' style='display: inline-block;margin: 0;width: 45px;font-size: 55px'>--</span>\n" +
                    "</td>";
                $("#table").find("tr:last").append(td);   // 将td 插入到最新的tr中
            }
        }
        if(localStorage.prizeLevel !== "五等奖" ) {
            var index1 = tableList.indexOf(1);
            tableList.splice(index1,1);
            var index2 = tableList.indexOf(2);
            tableList.splice(index2,1);
        }
    }
</script>
</html>

