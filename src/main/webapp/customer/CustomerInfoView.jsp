<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"customer".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký thành viên</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/CustomerInfoView.css">
    
</head>
<body>
    <%-- Đã thay đổi: h1 (tiêu đề chính) lên trên --%>
    <h1>Đăng ký thành viên</h1>
    
    <%-- Đã thay đổi: h2 (lời chào) xuống dưới --%>
    <h2>Xin chào, <%= user.getName() %>!</h2>

    <form action="<%= request.getContextPath() %>/MembershipRegistrationServlet" method="post">
        <label for="membershipDurationYears">Thời hạn đăng ký:</label>
        <select id="membershipDurationYears" name="membershipDurationYears" required>
            <option value="1">1 năm</option>
            <option value="2">2 năm</option>
            <option value="3">3 năm</option>
            <option value="5">5 năm</option>
        </select>

        <label for="membershipLevel">Cấp bậc thành viên:</label>
        <select id="membershipLevel" name="membershipLevel" required>
            <option value="Bac">Bạc</option>
            <option value="Vang">Vàng</option>
            <option value="Kim cuong">Kim cương</option>
        </select>

        <button type="submit">Đăng ký</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/customer/CustomerView.jsp">Quay lại trang khách hàng</a></p>

    <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
	%>
	    <script>
	        alert("<%= message %>");
	        window.location.href = "<%= request.getContextPath() %>/customer/CustomerView.jsp";
	    </script>
	<%
    }
%>

</body>
</html>