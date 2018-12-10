<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 950618
  Date: 2018/12/7
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>抽奖设置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <link href="../../css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script src="../../js/webJs/prize.js"></script>
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

<div id="add">
    <table class="table table-bordered">
        <thead>
        <th class="text-center">奖项名称</th>
        <th class="text-center">中奖人数</th>
        <th class="text-center">奖品图片</th>
        <th class="text-center">抽奖方式</th>
        </thead>-
        <tbody>
        <tr id="clone" class="myclass" >
            <td><input type="text" value="${data.name}"></td>
            <td><input type="number" value="${data.number}"></td>
            <td>
                <%--${data.imgUrl.length()}--%>
                <div style="display: inline" id="img">
                <c:if test="${data.imgUrl.length()>0}">
                    <img src="/image/${data.imgUrl}" style="width: 50px;height: 50px;"  >
                </c:if>
                </div>
                    <div style="display: inline" id="adjustButton">
                        <button type="button" class="btn btn-primary" data-toggle="button"  onclick="adjustPic()" style="display: inline" > 修改图片</button>
                    </div>

                 <div id="adjustForm" class="hidden">
                <form action="saveImg" method="post" enctype="multipart/form-data">
                    选择图片:<input type="file" name="file" accept="image/*" />
                    <input type="text" value=${data.id} name="prizeId" class="hidden" >
                    <br>
                    <input type="submit" value="上传"class="hidden" >

                </form>
                 </div>
                    <div  class="hidden" id="cancelButton" style="display:inline;">
                        <button type="button" class="btn btn-primary" data-toggle="button"  onclick="cancelPic()"  > 取消修改</button>
                    </div>
            </td>
            <td>
                <c:choose>
                    <c:when test="${data.mode}==true">    <!--如果 -->
                        <label>
                            <input type="checkbox" checked=checked > 随机抽取</label>
                        <label>
                            <input type="checkbox" >   按桌抽取
                        </label>
                    </c:when>
                    <c:otherwise>  <!--否则 -->
                        <label>
                            <input type="checkbox" > 随机抽取</label>
                        <label>
                            <input type="checkbox" checked="checked">   按桌抽取
                        </label>
                    </c:otherwise>
                </c:choose>

            </td>
            <%--隐藏--%>
            <td class="hidden">${data.id}</td>
        </tr>
        </tbody>
    </table>
    <div class="panel-footer">
        <button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off" onclick="adjustConfirm()">
            修改
        </button>
        <button type="button" class="btn btn-danger" onclick="window.location.href='drawSetting'">
            关闭
        </button>
    </div>
</div>

</body>