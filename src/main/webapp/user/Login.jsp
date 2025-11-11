<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Siêu thị điện máy</title>
<link rel="stylesheet" href="../css/Login.css">
</head>
<body>
	<form action="Login" method="post">
	<h2>Đăng nhập hệ thống</h2> 	
	  Tên đăng nhập <input type="text" name="username"><br>
	  Mật khẩu <input type="password" name="password"><br>
	  <input type="submit" value="Đăng nhập">
</form>

<% if(request.getAttribute("error") != null) { %>
  <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

</body>
</html>