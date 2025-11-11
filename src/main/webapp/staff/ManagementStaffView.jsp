<%@page import="model.User"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User u = (User) session.getAttribute("user"); 
    if (u == null) { 
        response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
        return;
    } 
%>
<html>
<head>
    <title>Trang chủ</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ManagementStaffView.css">
</head>
<body>


<div class="container">

	<h1 class="page-title">Trang chủ</h1>
    <h2>Xin chào, <%= u.getName() %>!</h2>



    <form action="StatisticsView.jsp" method="get">
        <button type="submit">Xem thống kê</button>
    </form>
</div>

</body>
</html>