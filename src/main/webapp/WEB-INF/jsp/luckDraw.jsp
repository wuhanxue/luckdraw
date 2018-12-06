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
</head>
<body onload="loadNumber()">

<%--<h1 style="color:#40AA53">抽奖结果</h1>--%>

<%--<div id="Result" style="color:#40AA53">--%>
<%--<span id="ResultNum">0</span>--%>
<%--<span id="num">0</span>--%>
<%--</div>--%>

<%--&lt;%&ndash;<div id="Button">&ndash;%&gt;--%>
<%--&lt;%&ndash;<input type='button' id="btn" value='开始' onclick='beginRndNum(this)'/>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>

<table id="table" style="width: 60%;height: 750px;border: 0" align="center">
    <tr id="class1">
        <!--几等奖，动态-->
        <td class="text-center" colspan="4"><span id="class">一</span>等奖 幸运号码</td>
    </tr>

</table>
<div class="text-center">
    <a class="btn btn-success" id="begin" style="width: 100px;height: 55px;font-size: 30px"
       onclick="beginRndNum(this)">开始</a>
    <a class="btn btn-danger" id="list" style="width: 100px;height: 55px;font-size: 30px">名单</a>
</div>

<%--<table align='center' border='1' cellspacing='0'>--%>
<%--<tr>--%>
<%--<td>id</td>--%>
<%--<td>桌号</td>--%>
<%--<td>座位号</td>--%>
<%--<td>部门</td>--%>
<%--<td>姓名</td>--%>
<%--<td>编号</td>--%>
<%--<td>下次是否参赛</td>--%>
<%--</tr>--%>
<%--<c:forEach items="${employeeList}" var="c" varStatus="st">--%>
<%--<tr>--%>
<%--<td>${c.id}</td>--%>
<%--<td>${c.tableId}</td>--%>
<%--<td>${c.locationId}</td>--%>
<%--<td>${c.department}</td>--%>
<%--<td>${c.name}</td>--%>
<%--<td>${c.number}</td>--%>
<%--<td>${c.join}</td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</table>--%>
</body>
<script>
    var g_Interval = 50;     // 数字跳转延迟速度(毫秒)
    var g_Timer;           // 延迟执行代码块
    var running = false; // 是否抽奖
    var list = [[1, 2], [2, 1], [1, 3], [1, 4], [1, 1], [2, 2]];  // 参与抽奖的桌号和座位号
    var winnerNumber = 6;  // 设置中奖人数
    var listWinner = [];  // 中奖的人的桌号和座位号数据

    function beginRndNum(trigger) {
        if (running) {
            running = false;
            clearTimeout(g_Timer); //取消延时
            updateRndNum();  // 设置最终的中奖号码(保证不存在重复)
            $(trigger).text("开始");
            $("span[id^='tableId']").css('color', 'red');
            $("span[id^='locationId']").css('color', 'red');
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

    function beginTimer() {
        g_Timer = setTimeout(beat, g_Interval);
    }

    function beat() {
        g_Timer = setTimeout(beat, g_Interval);
        updateRndNum();
    }

    /**
     * 根据抽奖人数加载抽奖框
     */
    function loadNumber() {
        // 获取所有参与抽奖的员工数据
        var url = "luckDraw";
        // $.ajax({
        //     type:"GET",
        //     url: url,
        //     dataType:"json",
        //     success: function(result){
        //        console.log("data");
        //        console.log(result);
        //     }
        // });
        $("#class1").nextAll().remove();  // 删除旧数据
        for (var i = 0; i < winnerNumber; i++) {
            if (i % 2 === 0) {
                var tr = "<tr>\n" +
                    "</tr>";
                $("#table").append(tr);
            }
            var td = "<td class=\"text-center\">\n" +
                "            <span>桌号：</span>&nbsp<span title=\"\" id='tableId" + i + "' style='width: 50px'>--</span>\n" +
                "            <span>座号：</span>&nbsp<span title=\"\" id='locationId" + i + "' style='width: 50px'>--</span>\n" +
                " </td>";
            $("#table").find("tr:last").append(td);   // 将td 插入到最新的tr中
        }

    }
</script>
</html>
