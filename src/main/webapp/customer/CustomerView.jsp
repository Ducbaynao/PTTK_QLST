<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ khách hàng</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/CustomerView.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

    <header>
        <div class="user-info">
            Xin chào, <strong><%= u.getName() %></strong>
        </div>
        <div class="page-title">TRANG CHỦ</div>
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="logout-link">Đăng xuất</a>
    </header>

    <main class="container">
        <form action="<%= request.getContextPath() %>/customer/CustomerInfoView.jsp" method="get">
            <input type="hidden" name="userId" value="<%= u.getId() %>" />
            <input type="submit" value="Đăng ký thành viên" />
        </form>
    </main>

</body>
</html>
