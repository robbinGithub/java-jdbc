<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录的 表单</title>
</head>
<body>

	<h3><font color="red">${message }</font></h3>
	<form action="${pageContext.request.contextPath }/login" method="post">
		用户名: <input type="text" name="name"><br/>
		密码: <input type="password" name="password"><br/>
		<input type="submit" value="登录">
	</form>
</body>
</html>