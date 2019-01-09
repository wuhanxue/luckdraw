<%--
  Created by IntelliJ IDEA.
  User: Matt
  Date: 2018/12/6
  Time: 16:03
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
    <script src="../../js/webJs/common.js"></script>
    <title>佳利达抽奖系统</title>
</head>
<style>
    body {
        font-size: 15px;
    }
    .setting > li > a {
        width: 100px;
        height: 40px;
        font-size: 20px;
        margin: 10px;
    }
    ul li{
        list-style: none;
    }
    .new {
        width: 100px;
        height: 40px;
        font-size: 20px;
        margin: 10px;
    }
</style>
<body>
<div class="container-fluid row">
    <div class="col-md-3 col-sm-3">
        <div class="sidebar">
            <div>
                <ol class="breadcrumb">
                    <li><a href="#">系统配置</a></li>
                    <li><a href="#">中奖名单</a></li>
                </ol>
            </div>
            <ul class="setting">
                <li><a class="btn btn-danger" href="/luckDrawSetting">开始抽奖</a></li>
                <li><a class="btn btn-primary" href="/drawSetting">奖项设置</a></li>
                <li><a class="btn btn-primary" href="/seat">座位设置</a></li>
                <li><a class="btn btn-warning" href="/winner">中奖名单</a></li>
                <li><a class="btn btn-primary" href="#" onclick="resetSeat();">中奖重置</a></li>
            </ul>
        </div>
    </div>
    <div class="col-md-9 col-sm-9">
        <div style="margin-left: 6%">
            <a class="btn btn-primary new" onclick="cleanAll();">清空</a>
            <a class="btn btn-primary new" onclick="exportExcel();">导出</a>
        </div>
        <div style="overflow-y: scroll;margin-left: 6%">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="text-center">序号</th>
                    <th class="text-center">奖项等级</th>
                    <th class="text-center">奖项名称</th>
                    <th class="text-center">奖品图片</th>
                    <th class="text-center">桌号</th>
                    <th class="text-center">位置号</th>
                    <th class="text-center">部门</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody>
                <%--循环显示信息--%>
                <c:forEach items="${winnerList}" var="winner" varStatus="st">
                    <tr>
                        <td class="text-center">${st.count}</td>
                        <td class="text-center">${winner.prize.level}</td>
                        <td class="text-center">${winner.prize.name}</td>
                        <c:choose>
                            <c:when test="${winner.prize.imgUrl.length()>0}">
                                <td style="width: 151px;height: 151px"><img src="/image/${winner.prize.imgUrl}" style="width: 150px;height: 150px;"></td>
                            </c:when>
                            <c:otherwise >
                                <td style="width: 151px;height: 151px">暂无图片</td>
                            </c:otherwise>
                        </c:choose>
                        <td class="text-center">${winner.seat.tableId}</td>
                        <td class="text-center">${winner.seat.locationId}</td>
                        <td class="text-center">${winner.seat.department}</td>
                        <td class="text-center">${winner.seat.name}</td>
                        <%--隐藏主键--%>
                        <td class="text-center hidden">${winner.id}</td>
                        <td class="text-center">
                            <a onclick="deleteItem(this);" href="#" title="作废"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

<script>
    /**
     * 删除当前条目
     * @param item 条目
     */
    function deleteItem(item) {
        if(confirm("确认删除?")) {
            var id = $(item).parent().prev().html();
            $.ajax({
                type: "DELETE",                       // 方法类型
                url: "winner", // url
                data: {
                    "id": id
                },
                async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
                dataType: "json",
                success: function (result) {
                    if (result != undefined && result.status == "success") {
                        alert(result.message);
                        window.location.reload();
                    }
                },
                error: function (result) {
                    alert("服务器异常!");
                }
            })
        }
    }

    /**
     * 清空数据
     */
    function cleanAll() {
        if(confirm("确认清空?")) {
            $.ajax({
                type: "DELETE",                       // 方法类型
                url: "allWinner", // url
                async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
                dataType: "json",
                success: function (result) {
                    if (result != undefined && result.status == "success") {
                        alert(result.message);
                        window.location.reload();
                    }
                },
                error: function (result) {
                    alert("服务器异常!");
                }
            })
        }

    }

    /**
     * 导出excel
     * @param e
     */
    function exportExcel() {
        var name = 'winnerList';
        // 获取勾选项
        var sqlWords = "select c.id,a.employee_name,a.seat_table_id,a.seat_location_id,b.prize_level,b.prize_name from main_seat a join config_prize b join main_winning c on b.id = c.prize_id and a.id=c.seat_id;";
        console.log("sql:" + sqlWords);
        window.open('exportExcel?name=' + name + '&sqlWords=' + sqlWords);
    }
</script>
