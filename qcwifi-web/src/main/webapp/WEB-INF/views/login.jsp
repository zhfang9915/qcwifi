<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Login Page</h1>
	<form action="../login/doLogin" method="post">
		username:<input name="username" type="text"/>
		<br/>
		<br/>
		password:<input name="password" type="password"/>
		<br/>
		<br/>
		<label><input type="checkbox" checked="checked"  name="rememberMe" />记住我</label>
		<br/>
		<br/>
		<input type="submit" value="login"/>
	</form>
</body>
</html>