<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Thống kê doanh thu khách hàng</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CustomerRevenueStatisticsView.css">
</head>
<body>
<div class="container">
    <h1>Thống kê doanh thu theo khách hàng</h1>

    <p class="staff-info">
        <strong>Nhân viên quản lí:</strong> <strong><%= u.getName() %></strong>
    </p>

    <form action="${pageContext.request.contextPath}/RevenueStatisticsServlet" method="post">
        Từ ngày: <input type="date" name="fromDate" value="${fromDate}">
        Đến ngày: <input type="date" name="toDate" value="${toDate}">
        <input type="submit" value="Xem báo cáo">
    </form>

    <hr>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty statistics}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên khách hàng</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Địa chỉ</th>
                        <th>Tổng doanh thu (VNĐ)</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="grandTotal" value="0" />
                    <c:forEach var="stat" items="${statistics}">
                        <c:set var="grandTotal" value="${grandTotal + stat.total}" />
                        <tr class="clickable-row"
						    onclick="window.location='${pageContext.request.contextPath}/RevenueStatisticsServlet?customerId=${stat.customer.id}&fromDate=${fromDate}&toDate=${toDate}'">
						    <td>${stat.customer.id}</td>
						    <td>${stat.customer.name}</td>
						    <td>${stat.customer.email}</td>
						    <td>${stat.customer.phoneNumber}</td>
						    <td>${stat.customer.address}</td>
						    <td align="right"><fmt:formatNumber value="${stat.total}" pattern="#,##0"/> </td>
						    <td>
						        <form action="${pageContext.request.contextPath}/RevenueStatisticsServlet" method="get" style="margin:0;">
						            <input type="hidden" name="customerId" value="${stat.customer.id}">
						            <input type="hidden" name="fromDate" value="${fromDate}">
						            <input type="hidden" name="toDate" value="${toDate}">
						            <button type="submit">Xem chi tiết</button>
						        </form>
						    </td>
						</tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="5" style="text-align:right; font-weight:bold;">TỔNG DOANH THU:</td>
                        <td align="right" colspan="2">
                            <fmt:formatNumber value="${grandTotal}" pattern="#,##0"/> 
                        </td>
                    </tr>
                </tfoot>
            </table>
        </c:when>
        <c:otherwise>
            <p>Không có dữ liệu thống kê trong khoảng thời gian này.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>