<%--
  Created by IntelliJ IDEA.
  User: wuhanxue5@sina.com
  Date: 2018/12/4
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="upload" method="post" enctype="multipart/form-data">
    选择图片:<input type="file" name="file" accept="image/*" />
    <br>
    <input type="submit" value="上传">
</form>

</body>
</html>
