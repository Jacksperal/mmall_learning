<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    hello world
</head>
<body>
<h2>Hello World!</h2>

springMVC上传文件
<form name="form1" method="post" enctype="multipart/form-data" action="/mmall/manage/product/upload.do">
    <input type="file" name="file"/>
    <button>上传</button>
</form>

富文本图片上传文件
<form name="form1" method="post" enctype="multipart/form-data" action="/mmall/manage/product/richtext_img_upload.do">
    <input type="file" name="file"/>
    <button>富文本图片上传</button>
</form>
</body>
</html>
