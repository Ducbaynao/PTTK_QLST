<%@page import="model.User"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // Thêm phần kiểm tra session
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Trang xem thống kê</title> <%-- Sửa title --%>
    <%-- Thêm link CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/StatisticsView.css">
</head>
<body>

<%-- Thêm container --%>
<div class="container">

    <h2>Xem thống kê</h2> <%-- Sửa h2 --%>

    <%-- Thêm thông tin nhân viên --%>
	<p class="staff-info">
	    <strong>Nhân viên quản lí:</strong> <strong><%= u.getName() %></strong>
	</p>

    <%-- Nhóm các nút lại --%>
    <div class="button-group">
        <form action="CustomerRevenueStatisticsView.jsp" method="get">
            <button type="submit">Thống kê doanh thu</button>
        </form>
    
        <button disabled>Thống kê nhà cung cấp</button>
        <button disabled>Thống kê sản phẩm</button>
    </div>

</div>

</body>
</html>