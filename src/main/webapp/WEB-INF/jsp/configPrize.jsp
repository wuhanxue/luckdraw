<%--
  Created by IntelliJ IDEA.
  User: 950618
  Date: 2018/12/6
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script src="../../js/webJs/prize.js"></script>
    <title>佳利达抽奖系统</title>
    <%--<script src="../js/prize.js" ></script>--%>

</head>
<style>
    body {
        font-size: 10px;
    }
    .middle {
        margin-top: 10%;
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
<body onload="loadPrizeLIst()">
<div >

    <div >
        <div>
            <ol class="breadcrumb">
                <li><a href="#">系统配置</a></li>
                <li><a href="#">奖项设置</a></li>
            </ol>
        </div>
        <ul class="setting">
            <li><a class="btn btn-primary" href="drawSetting.html">奖项设置</a></li>
            <li><a class="btn btn-primary" href="drawSetting2.html">座位设置</a></li>
            <li><a class="btn btn-primary" href="drawSetting3.html">中奖重置</a></li>
        </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-offset-2 col-md-9 col-md-offset-2">
        <div class="panel panel-default" id="addPanel">
            <div class="panel-heading" >
                <h3 class="panel-title">
                    奖品添加
                </h3>
            </div>
            <div class="panel-body">

            </div>
            <div class="panel-footer">
                <button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
                    保存
                </button>
                <button type="button" class="btn btn-danger"  aria-pressed="false" autocomplete="off">
                 关闭
                </button>
            </div>
        </div>
        <div style="margin-left: 25%">
            <a class="btn btn-primary new" onclick="addData()">新增</a>
        </div>
        <div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="text-center">奖项名称</th>
                    <th class="text-center">中奖人数</th>
                    <th class="text-center">奖品图片</th>
                    <th class="text-center">抽奖方式</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="text-center" id="awardName"></td>
                    <td class="text-center" id="awardNumber"></td>
                    <td class="text-center" id="awardPicture">
                        <img src="image/price.png" style="width: 60px;height: 60px">
                    </td>
                    <td class="text-center" id="awardWay"></td>
                    <td class="text-center">
                        <a onclick="" href="#" title="编辑"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                        <a onclick="" href="#" title="作废"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                    </td>
                </tr>
                <%--<tr class="CloneTr">--%>
                    <%--<td class="text-center"></td>--%>
                    <%--<td class="text-center"></td>--%>
                    <%--<td class="text-center"></td>--%>
                    <%--<td class="text-center"></td>--%>
                    <%--<td class="text-center"></td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>
    </div>
</div>




</body>

</html>
