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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <%--<script type="text/javascript" src="../js/luckDraw.js"></script>--%>
    <title>佳利达抽奖系统</title>
    <style>
        body {
            font-size: 20px;
        }
    </style>
</head>
<body onload="loadNumber()" >

<%--<h1 style="color:#40AA53">抽奖结果</h1>--%>

<%--<div id="Result" style="color:#40AA53">--%>
<%--<span id="ResultNum">0</span>--%>
<%--<span id="num">0</span>--%>
<%--</div>--%>

<%--&lt;%&ndash;<div id="Button">&ndash;%&gt;--%>
<%--&lt;%&ndash;<input type='button' id="btn" value='开始' onclick='beginRndNum(this)'/>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>

<table id="table" style="width: 80%;height: 450px;border: 0;" align="center">
    <tr id="class1">
        <!--几等奖，动态-->
        <td class="text-center" colspan="4"><p style="font-size: 80px;color: #ff3f4b"><span id="class" style="color: #8909ff">一</span>等奖 幸运号码</p></td>
    </tr>
</table>
<div class="text-center">
    <a class="btn btn-success" id="begin" style="width: 100px;height: 55px;font-size: 30px"
       onclick="beginRndNum(this)">开始</a>
    <a class="btn btn-danger" id="list" onclick="saveWinner()" style="width: 100px;height: 55px;font-size: 30px">名单</a>
</div>
</body>
<script>
    var g_Interval = 50;     // 数字跳转延迟速度(毫秒)
    var g_Timer;           // 延迟执行代码块
    var running = false; // 是否抽奖
    var list = [[1, 2], [2, 1], [1, 3], [1, 4], [1, 1], [2, 2]];  // 参与抽奖的桌号和座位号
    var winnerNumber = 3;  // 设置中奖人数
    var listWinner = [];  // 中奖的人的桌号和座位号数据
    var add = false;   // 新增是否成功

    function beginRndNum(trigger) {
        if (running) {
            running = false;
            clearTimeout(g_Timer); //取消延时
            updateRndNum();  // 设置最终的中奖号码(保证不存在重复)
            $(trigger).text("开始");
            $("span[id^='tableId']").css('color', 'red');
            $("span[id^='locationId']").css('color', 'red');
            // setTimeout(saveWinner(),3000); // 抽奖结束不点击三秒后自动执行
        } else {
            running = true;
            $("span[id^='tableId']").css('color', 'black');
            $("span[id^='locationId']").css('color', 'black');
            $(trigger).text("停止");
            listWinner = [];   // 中奖人清空
            beginTimer();
        }
    }

    /**
     * 获取抽奖编号并显示
     */
    function updateRndNum() {
        for (var i = 0; i < winnerNumber; i++) {
            var $i = i;
            var num = Math.floor(Math.random() * list.length);  // 序号
            //var num1 = Math.floor(Math.random() * list.length + 1);  // 座位号
            $("#tableId" + $i).html(list[num][0]);
            $("#locationId" + $i).html(list[num][1]);
            if (!running) { // 停止随机后
                if (listWinner.indexOf(list[num]) > -1) { //包含该元素
                    var j = 0;
                    while (listWinner.indexOf(list[j]) > -1) { // 执行到不包含该元素为止
                        j++;  // 寻找不包含的元素
                    }
                    $("#tableId" + $i).html(list[j][0]);
                    $("#locationId" + $i).html(list[j][1]);
                    listWinner.push(list[j]);
                } else { // 不包含该元素
                    listWinner.push(list[num]);
                }
            }
        }
    }

    /**
     * 设置时间延迟
     * */
    function beginTimer() {
        g_Timer = setTimeout(beat, g_Interval);
    }

    /**
     * 时间延迟时间，并执行数字滚动
     * */
    function beat() {
        g_Timer = setTimeout(beat, g_Interval);
        updateRndNum();
    }

    /**
     * 根据抽奖人数加载抽奖框
     */
    function loadNumber() {
        add = false;  // 将新增状态设置为未新增
        // 获取所有参与抽奖的员工数据
        list = [];  // 清零
        <c:forEach items="${seatList}" var="a">
        var tableId = ${a.tableId};
        var locationId = ${a.locationId};
        list.push([tableId, locationId]);
        </c:forEach>
        console.log(list);
        if (winnerNumber > list.length) { // 当抽奖人数大于参加人数时自动赋值为参加人数
            console.log("中奖人数大于参加人数");
            winnerNumber = list.length;
        }
        $("#class1").nextAll().remove();  // 删除旧数据
        for (var i = 0; i < winnerNumber; i++) { // 根据中奖人数插入抽奖框
            if (i % 2 === 0) {
                var tr = "<tr>\n" +
                    "</tr>";
                $("#table").append(tr);
            }
            var td = "<td class=\"text-center\">\n" +
                "<span style='font-size: 25px;margin-right: 15px'>桌号：</span><span title=\"\" id='tableId" + i + "' style='display: inline-block;margin: 0;width: 45px;font-size: 25px'>--</span>\n" +
                "<span style='font-size: 25px;margin-left: 15px'>座号：</span><span title=\"\" id='locationId" + i + "' style='display: inline-block;margin: 0;width: 45px;font-size: 25px'>--</span>\n" +
                "</td>";
            $("#table").find("tr:last").append(td);   // 将td 插入到最新的tr中
        }
    }

    /**
     * 保存中奖数据并跳转到中奖名单
     */
    function saveWinner() {
        var seatList = [];     // 初始化
        for (var i = 0; i < winnerNumber; i++) {
            var $i = i;
            var seat = {};
            seat.tableId = parseInt($("#tableId" + $i).text());
            seat.locationId = parseInt($("#locationId" + $i).text());
            seatList.push(seat);
        }
        $.ajax({   // 将中奖者信息更新到数据库
                type: "PUT",
                url: "updateWinner",
                data: {
                    "seats":JSON.stringify(seatList)
                } ,
                dataType: "json",
            //    contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    add = true;  //将新增状态设置为成功
                }
            });
        if(add){ // 如果更新中奖名单数据成功则执行延时跳转
            // localStorage.setItem("seatList",JSON.stringify(seatList));  // 存储中奖名单
            // console.log("中奖名单");
            // console.log(JSON.parse(localStorage.getItem('seatList')));
          // $.get("showWinnerList");                                   // 跳转至中奖者名单页面
        //    window.location.href="showWinnerList.jsp";
        }
    }
</script>
</html>
