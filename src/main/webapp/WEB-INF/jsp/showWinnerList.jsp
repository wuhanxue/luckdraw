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
    <link rel="stylesheet" href="../../css/style.css">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
</head>
<body onload="loadWinnerList()">
<div class='luck-back'><!--背景图-->
    <div class="luck-content ce-pack-end" style="width: 80%;margin-left: -5%;padding: 0"><!--透明框-->
        <table style="width: 100%;height: 100%;border: 0">
            <thead>
            <!--几等奖，动态-->
            <tr>
                <td class="text-center" colspan="2" style="height: 85px">
                    <span id="prize" style="color: #ff3f4b;font-size: 45px">一等奖</span>: 中奖名单
                </td>
            </tr>
            </thead>
        </table>
        <div id="scroll" style="overflow-y:scroll;height: 313px; ">
            <table style="width: 80%;height: 450px;border: 0" cellpadding="0" cellspacing="0" align="center">
                <tbody id="tBody">

                </tbody>
            </table>
        </div>
        <div class="text-center" style="margin-top: -60px">
            <a class="btn btn-success" id="begin" style="width: 150px;height: 55px;font-size: 30px"
               href="luckDraw">再抽一次</a>
            <a class="btn btn-danger" id="next" href="luckDrawSetting"
               style="width: 150px;height: 55px;font-size: 30px">下一奖项</a>
        </div>
    </div>
</div>
</body>
<script>
    /**
     * 显示中奖者名单
     */
    function loadWinnerList() {
        $("#prize").text(localStorage.prizeLevel + "：" + localStorage.prizeName);  // 设置奖品
        var i = 0;
        <c:forEach items="${seatList}" var="a">
        // if (i % 2 === 0) {
        //     var tr = "<tr>\n" +
        //         "</tr>";
        //     $("#tBody").append(tr);
        // }
        var td = "<tr class='text-center'> <td style=\"height: 50px;color: white'\">\n" +
            "            <p>               <span style='color:white'>桌号</span>【<span style='color:red' class=\"tableId\">${a.tableId}</span>】\n" +
            "                    &nbsp;&nbsp;<span style='color:white'>座位号</span>【<span style='color:red' class=\"locationId\">${a.locationId}</span>】\n" +
            "                    &nbsp;&nbsp;<span style='color:white'>姓名</span>【<span style='color:red' class=\"name\">${a.name}</span>】\n" +
            "                    &nbsp;&nbsp;<span style='color:white'>部门</span>【<span style='color:red' class=\"department\">${a.department}</span>】\n" +
            "            </p>\n" +
            "            </td></tr>";
        $("#tBody").append(td);   // 将td 插入到最新的tr中
        i++;
        </c:forEach>
        setScrollAuto();   // 滚动条自动滚动
    }


    function setScrollAuto() {
        setTimeout(function () {   // 延时滚动中奖者名单
            //鼠标点击结束
            $('#scroll').click(function () {
                if (timer) {
                    clearInterval(timer);
                }
            });
            var h = -1;
            var timer = setInterval(function () {
                //获取当前滚动条高度
                var current = $('#scroll')[0].scrollTop;
                if (current == h) {
                    //滚动到底端，并返回顶端
                    clearInterval(timer);
                    // window.location.reload();
                    $('#scroll')[0].scrollTop = 0;  //返回顶部
                    setScrollAuto();          // 重新开始
                }
                else {
                    //以80ms/3.5px的速度滚动
                    h = current;
                    $('#scroll')[0].scrollTop = h + 3.5;
                }
            }, 80);
        }, 2000)
    }

</script>
</html>