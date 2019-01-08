<%--
  Created by IntelliJ IDEA.
  User: 950618
  Date: 2018/12/6
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>佳利达抽奖系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script src="../../js/webJs/prize.js"></script>
    <script src="../../js/webJs/common.js"></script>
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
<body onload="loadPrizeLIst()">
<div class="panel panel-default" id="newPanel">
    <h3 class="panel-title center-block">
    </h3>
    <div class="panel-heading" >

    </div>
    <div class="panel-body">
        <div id="add">
            <table class="table table-bordered">
                <thead>
                <th class="text-center">奖项等级</th>
                <th class="text-center">奖项名称</th>
                <th class="text-center">奖品数量</th>
                <th class="text-center">奖品图片</th>
                <th class="text-center">抽奖方式</th>
                </thead>
                <tbody>
                <tr id="clone" class="myclass" >
                    <td><input type="text" class="form-control"></td>
                    <td><input type="text" class="form-control"></td>
                    <td><input type="number" class="form-control"></td>
                    <td>
                        <form action="saveImg" method="post" enctype="multipart/form-data">
                            选择图片:<input type="file" name="file" accept="image/*" />
                            <input type="text" value=0 name="prizeId" class="hidden" >
                            <input type="submit" value="上传" class="hidden" >
                        </form>
                    </td>
                    <td>
                        <label>
                            <input type="radio" name="mode"> 按桌抽取</label>
                        <label>
                            <input type="radio" name="mode"> 随机抽取</label>
                        <label>
                            <input type="radio" name="mode"> 桌位抽取</label>
                    </td>
                </tr>
                <tr id="plusBtn">
                    <td>
                        <a class="btn btn-default btn-xs" onclick="addNewLine(this);"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="center-block">
        <button type="button" class="btn btn-primary " data-toggle="button" aria-pressed="false" autocomplete="off" onclick="save()">
            保存
        </button>
        <button type="button" class="btn btn-danger " onclick="closed()">
            关闭
        </button>
    </div>
</div><%--新增修改面板--%>

<div class="container-fluid row">
    <div class="col-md-3 col-sm-3">
        <div class="sidebar">
            <div>
                <ol class="breadcrumb">
                    <li><a href="#">系统配置</a></li>
                    <li><a href="#">奖项设置</a></li>
                </ol>
            </div>
            <ul class="setting">
                <li><a class="btn btn-danger" href="/luckDrawSetting">开始抽奖</a></li>
                <li><a class="btn btn-warning" href="/drawSetting">奖项设置</a></li>
                <li><a class="btn btn-primary" href="/seat">座位设置</a></li>
                <li><a class="btn btn-primary" href="/winner">中奖名单</a></li>
                <li><a class="btn btn-primary" href="#" onclick="resetSeat();">中奖重置</a></li>
            </ul>
        </div>
    </div>
    <div class="panel panel-default" id="show"><%--显示的面板--%>
        <div class="panel-body">
    <div class="col-md-9 col-sm-9">
        <div style="margin-left: 6%">
            <a class="btn btn-primary new" onclick="addData()">新增</a>
        </div>
        <div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="text-center">奖项等级</th>
                    <th class="text-center">奖项名称</th>
                    <th class="text-center">奖品数量</th>
                    <th class="text-center">奖品图片</th>
                    <th class="text-center">抽奖方式</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <c:forEach items="${prizeList}" var="p" varStatus="st">
                    <tr> <td>${p.level}</td>
                        <td>${p.name}</td>
                        <td>${p.allNumber}</td>
                   <c:choose>
                       <c:when test="${p.imgUrl.length()>0}">
                           <td style="width: 151px;height: 151px"><img src="/image/${p.imgUrl}" style="width: 150px;height: 150px;"></td>
                       </c:when>
                       <c:otherwise >
                           <td style="width: 151px;height: 151px">暂无图片</td>
                       </c:otherwise>
                   </c:choose>

                        <td>${p.modeName} </td>
                        <td class="hidden">${p.id} </td>
                        <td> <a  href="editPrize?id=${p.id}" title="编辑"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            <a onclick="cutOff(this)" href="#" title="删除" ><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
                    </tr>

                </c:forEach>

            </table>
        </div>
    </div>
        </div>
    </div>
</div>

<div id="embed"></div>
</body>
</html>
