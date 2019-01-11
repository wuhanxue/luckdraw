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
<body onload="loadWinnerList();" background="../../image/timg.jpg" style="background-size: cover">
<table style="width: 100%;height: 100%;border: 0;margin-top: 10%">
    <thead>
    <!--几等奖，动态-->
    <tr>
        <td class="text-center" colspan="2" style="height: 85px;color: #ff3f4b;font-size: 45px">
            <span id="prize">一等奖</span>————中奖名单
        </td>
    </tr>
    </thead>
</table>
<div id="scroll" style="overflow-y:scroll; height: 500px">
    <table style="width: 80%;height: 450px;border: 0" cellpadding="0" cellspacing="0" align="center">
        <tbody id="tBody">

        </tbody>
    </table>
</div>
<div class="text-center" style="margin-top: 20px">
    <a class="btn btn-success" id="begin" style="width: 110px;height: 45px;font-size: 20px" onclick="oneMoreLuckDraw()">再抽一次</a>
    <a class="btn btn-danger" id="next" onclick="next()" style="width: 110px;height: 45px;font-size: 20px">下一奖项</a>
</div>
</body>
<script>
    /**
     * 显示中奖者名单
     */
    function loadWinnerList() {
        //if(localStorage.prizeMode !== "2"){
        console.log("winTableList:");
        console.log(JSON.parse(localStorage.getItem('winTableList')));
        if(parseInt(localStorage.tableLength) < 1){
            $("#begin").hide();
        }
        $("#prize").text(localStorage.prizeLevel + "：" + localStorage.prizeName);  // 设置奖品
        var i = 0;
        <c:forEach items="${seatList}" var="a">
        // if (i % 2 === 0) {
        //     var tr = "<tr>\n" +
        //         "</tr>";
        //     $("#tBody").append(tr);
        // }
        var td = "<tr class='text-center'> <td style=\"height: 50px;color: white;font-size:40px'\">\n" +
        "<p><span style='color:#22e6ff;font-size:40px'>桌号&nbsp;</span><span style='color:red;display:inline-block;width:120px;font-size:40px' class=\"tableId\">${a.tableId}</span>\n" +
        "&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>座位号&nbsp;</span><span style='color:red;display:inline-block;width:120px;font-size:40px' class=\"locationId\">${a.locationId}</span>\n" +
        "&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>姓名&nbsp;</span><span style='color:#8909ff;display:inline-block;width:120px;font-size:40px' class=\"name\">${a.name}</span>\n" +
        "&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>部门&nbsp;</span><span style='color:red;display:inline-block;width:250px;font-size:40px' class=\"department\">${a.department}</span>\n" +
        "</p>\n" +
        "</td></tr>";
        $("#tBody").append(td);   // 将td 插入到最新的tr中
        i++;
        </c:forEach>
        <%--}else {--%>
        <%--$("#prize").text(localStorage.prizeLevel);  // 设置奖品--%>
        <%--var i = 0;--%>
        <%--<c:forEach items="${seatList}" var="a">--%>
        <%--// if (i % 2 === 0) {--%>
        <%--//     var tr = "<tr>\n" +--%>
        <%--//         "</tr>";--%>
        <%--//     $("#tBody").append(tr);--%>
        <%--// }--%>
        <%--${a.winners.prize.name}.--%>
        <%--replace(/[^0-9]/ig, "");   // 截取数字--%>
        <%--var td = "<tr class='text-center'> <td style=\"height: 50px;color: white;font-size:40px'\">\n" +--%>
            <%--"<p><span style='color:#22e6ff;font-size:40px'>桌号&nbsp;</span><span style='color:red;display:inline-block;width:120px;font-size:40px' class=\"tableId\">${a.tableId}</span>\n" +--%>
            <%--"&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>座位号&nbsp;</span><span style='color:red;display:inline-block;width:120px;font-size:40px' class=\"locationId\">${a.locationId}</span>\n" +--%>
            <%--"&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>姓名&nbsp;</span><span style='color:#8909ff;display:inline-block;width:120px;font-size:40px' class=\"name\">${a.name}</span>\n" +--%>
            <%--"&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>奖品&nbsp;</span><span style='color:#8909ff;display:inline-block;width:120px;font-size:40px' class=\"prizeName\">" + prizeName + "</span>\n" +--%>
            <%--"&nbsp;&nbsp;<span style='color:#22e6ff;font-size:40px'>部门&nbsp;</span><span style='color:red;display:inline-block;width:250px;font-size:40px' class=\"department\">${a.department}</span>\n" +--%>
            <%--"</p>\n" +--%>
            <%--"</td></tr>";--%>
        <%--$("#tBody").append(td);   // 将td 插入到最新的tr中--%>
        <%--i++;--%>
        <%--</c:forEach>--%>
        <%--// }--%>
        setScrollAuto();   // 滚动条自动滚动

    }

    /**
     * 滚动条自动滚动事件
     * */
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

    /**
     * 空格键按下事件
     */
    $(document).keydown(function (event) {
        if (event.keyCode === 32) {  // 空格键、回车抽奖
           oneMoreLuckDraw();
        }
        if(event.keyCode === 13) {
            next();
        }
    });

    /**
     * 再抽一次
     */
    function oneMoreLuckDraw() {
        if(localStorage.prizeMode === "0"){  // 按桌抽奖
            if(parseInt(localStorage.tableLength) < 1){  // 如果所有桌数均抽完则返回下一奖项页面
                next();
            } else {
                localStorage.winnerDrawNumber = 1;
                var tList = JSON.parse(localStorage.getItem('winTableList'));
                localStorage.setItem('winTableList', JSON.stringify(tList));
                window.location.href = 'luckDraw';
            }
        }else{
            localStorage.winnerDrawNumber = 1;
            var tList = JSON.parse(localStorage.getItem('winTableList'));
            localStorage.setItem('winTableList', JSON.stringify(tList));
            window.location.href = 'luckDraw';
        }

    }

    /**
     *
     * 下一次抽奖
     */
    function next() {
        localStorage.winnerDrawNumber = 0;
        window.location.href = 'luckDrawSetting';
    }

</script>
</html>