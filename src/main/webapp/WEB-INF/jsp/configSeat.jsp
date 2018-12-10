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
<body onload="loadSeatList();">
<div class="container-fluid row">
    <div class="col-md-3 col-sm-3">
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
    </div>
    <div class="col-md-9 col-sm-9">
        <div style="margin-left: 6%">
            <a class="btn btn-primary new" onclick="addData()">新增</a>
            <a class="btn btn-primary new">导入</a>
        </div>
        <%--新增修改面板--%>
        <div class="panel panel-default" id="newPanel">
            <h3 class="panel-title center-block">
            </h3>
            <div class="panel-heading" >

            </div>
            <div class="panel-body">
                <div id="add">

                    <table class="table table-bordered">

                        <thead>
                            <th class="text-center">桌号</th>
                            <th class="text-center">座号</th>
                            <th class="text-center">部门</th>
                            <th class="text-center">姓名</th>
                        </thead>
                        <tbody>
                        <tr id="clone" class="myclass" >
                            <td><input type="number" class="form-control"></td>
                            <td><input type="number" class="form-control"></td>
                            <td><input type="text" class="form-control"></td>
                            <td><input type="text" class="form-control"></td>
                        </tr>
                        <tr id="plusBtn">
                            <td>
                                <a class="btn btn-default btn-xs" onclick="addNewLine(this);"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                            </td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </div>
            <div class="panel-footer">
                <button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off" onclick="save()">
                    保存
                </button>
                <button type="button" class="btn btn-danger" onclick="closed()">
                    关闭
                </button>
            </div>
        </div>
        <div style="overflow-y: scroll;margin-left: 6%">
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

<script>
    /**
     * 缺省载入函数
     */
    function loadSeatList() {
        // 默认新增编辑面板隐藏
        $('#newPanel').hide();
    }

    /**
     * 增加数据
     */
    function addData() {
        // 删除克隆行
        $('#clone').siblings().not($('#plusBtn')).remove();
        // 添加标题
        $('#newPanel').find("H3").text('座位添加');
        // 新增编辑面板显示
        $('#newPanel').show(1000);
    }

    /**
     * 关闭新增面板
     */
    function closed() {
        // 新增编辑面板赢藏
        $('#newPanel').hide(1000)
    }

    /**
     * 新增
     * @param item
     */
    function addNewLine(item) {
        var tr=$(item).parent().parent().prev();
        var cloneTr=tr.clone();
        cloneTr.show();
        cloneTr.insertAfter(tr);
        cloneTr.removeAttr('id');
        cloneTr.children().find("input").val("");
        cloneTr.children("td:eq(0)").find("a").remove();
        var delBtn = "<a class='btn btn-default btn-xs' onclick='delLine(this);'><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></a>&nbsp;";
        cloneTr.children("td:eq(0)").prepend(delBtn);
    }

    /**
     * 删除行
     * @param e
     */
    function delLine(e) {
        var tr = e.parentElement.parentElement;
        tr.parentNode.removeChild(tr);
    }

    /**
     * 保存座位数据
     */
    function save() {
        $('.myclass').each(function () {
            var data = {
                tableId: $(this).children('td').eq(0).find('input').val(),
                locationId: $(this).children('td').eq(1).find('input').val(),
                department: $(this).children('td').eq(2).find('input').val(),
                name: $(this).children('td').eq(3).find('input').val()
            };
            console.log(data);
            $.ajax({
                type: "POST",                       // 方法类型
                url: "seat", // url
                data: JSON.stringify(data),
                async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                success: function (result) {
                    if (result != undefined && result.status == "success") {
                        alert(result.message);
                        window.location.reload();
                    }
                },
                error: function (result) {
                    alert("服务器异常!");
                }
            });
        });
    }
</script>
