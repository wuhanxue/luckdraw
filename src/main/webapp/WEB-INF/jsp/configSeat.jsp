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
    <title>佳利达抽奖系统</title>
</head>
<style>
    body {
        font-size: 40px;
    }
    .setting > li > a {
        width: 250px;
        height: 80px;
        font-size: 50px;
        margin: 15px;
    }
    ul li{
        list-style: none;
    }
    .new {
        width: 200px;
        height: 80px;
        font-size: 50px;
        margin: 15px;
    }
</style>
<body>
<div class="container-fluid">

    <div class="sidebar">
        <div>
            <ol class="breadcrumb">
                <li><a href="#">系统配置</a></li>
                <li><a href="#">座位设置</a></li>
            </ol>
        </div>
        <ul class="setting">
            <li><a class="btn btn-primary" href="#">奖项设置</a></li>
            <li><a class="btn btn-primary" href="#">座位设置</a></li>
            <li><a class="btn btn-primary" href="#">中奖重置</a></li>
        </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-offset-2 col-md-9 col-md-offset-2 main">
        <div style="margin-left: 25%">
            <a class="btn btn-primary new">新增</a>
            <a class="btn btn-primary new">导入</a>
        </div>
        <div style="overflow-y: scroll;margin-left: 25%">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="text-center">桌号</th>
                    <th class="text-center">位置号</th>
                    <th class="text-center">部门</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody>
                <%--循环显示信息--%>
                <c:forEach items="${seatList}" var="seat" varStatus="st">
                    <tr>
                        <td class="text-center">${seat.tableId}</td>
                        <td class="text-center">${seat.locationId}</td>
                        <td class="text-center">${seat.department}</td>
                        <td class="text-center">${seat.name}</td>
                        <td class="text-center">
                            <a onclick="" href="#" title="编辑"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            <a onclick="" href="#" title="作废"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
