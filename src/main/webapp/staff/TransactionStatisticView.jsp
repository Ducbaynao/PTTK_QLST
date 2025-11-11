<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fromDateObj = null;
    Date toDateObj = null;
    try {
        if (request.getAttribute("fromDate") != null && !((String)request.getAttribute("fromDate")).isEmpty()) {
            fromDateObj = sdf.parse((String)request.getAttribute("fromDate"));
        }
        if (request.getAttribute("toDate") != null && !((String)request.getAttribute("toDate")).isEmpty()) {
            toDateObj = sdf.parse((String)request.getAttribute("toDate"));
        }
    } catch (Exception e) {
        
    }
    pageContext.setAttribute("fromDateObj", fromDateObj);
    pageContext.setAttribute("toDateObj", toDateObj);
%>

<html>
<head>
    <title>Thống kê các lần giao dịch</title>
    <meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/TransactionStatisticView.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h2>Thống kê các lần giao dịch</h2>
        <p><strong>${loggedInUser.name}</strong> </p>
    </div>

    <h3>Khách hàng: <strong>${customerName}</strong></h3>
    <p style="font-size: 1.1em;">
        <strong>Thời gian thống kê:</strong>
        <c:choose>
            <c:when test="${fromDateObj != null and toDateObj != null}">
                Từ <fmt:formatDate value="${fromDateObj}" pattern="dd/MM/yyyy"/> 
                đến <fmt:formatDate value="${toDateObj}" pattern="dd/MM/yyyy"/>
            </c:when>
            <c:when test="${fromDateObj != null}">
                Từ ngày <fmt:formatDate value="${fromDateObj}" pattern="dd/MM/yyyy"/>
            </c:when>
            <c:when test="${toDateObj != null}">
                Đến ngày <fmt:formatDate value="${toDateObj}" pattern="dd/MM/yyyy"/>
            </c:when>
            <c:otherwise>Toàn bộ thời gian</c:otherwise>
        </c:choose>
    </p>

    <c:set var="grandTotal" value="0" />

    <c:choose>
        <c:when test="${empty customerOrders}">
            <p style="text-align:center; color:#e74c3c; font-size:1.1em;">
                Khách hàng chưa có giao dịch nào trong khoảng thời gian này.
            </p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã đơn</th>
                        <th>Ngày tạo</th>
                        <th>Loại đơn</th>
                        <th>Tên sản phẩm</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền (VND)</th>
                    </tr>
                </thead>
                <tbody>
				    <c:forEach var="order" items="${customerOrders}" varStatus="orderStatus">
				        <c:set var="orderTotal" value="0" />
				        <c:set var="detailList" value="${order.details}" />
				
				        <c:forEach var="detail" items="${detailList}" varStatus="detailStatus">
				            <c:set var="lineTotal" value="${detail.price * detail.quantity}" />
				            <c:set var="orderTotal" value="${orderTotal + lineTotal}" />
				            <c:set var="grandTotal" value="${grandTotal + lineTotal}" />
				
				            <tr>
				                <c:if test="${detailStatus.first}">
				                    <td rowspan="${fn:length(detailList)}">${orderStatus.index + 1}</td>
				                    <td rowspan="${fn:length(detailList)}">#${order.id}</td>
				                    <td rowspan="${fn:length(detailList)}">
				                        <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
				                    </td>
				                    <td rowspan="${fn:length(detailList)}">
				                        <c:choose>
				                            <c:when test="${order['class'].simpleName == 'OnlineOrder'}">Online</c:when>
				                            <c:when test="${order['class'].simpleName == 'SalesInvoice'}">Tại quầy</c:when>
				                            <c:otherwise>Khác</c:otherwise>
				                        </c:choose>
				                    </td>
				                </c:if>
				
				                <td class="product-name">${detail.product.name}</td>
				                <td class="price"><fmt:formatNumber value="${detail.price}" pattern="#,##0"/> </td>
				                <td>${detail.quantity}</td>
				                <td class="price"><fmt:formatNumber value="${lineTotal}" pattern="#,##0"/> </td>
				            </tr>
				        </c:forEach>
				
				        <tr class="total-row">
				            <td colspan="7" style="text-align: right;">Tổng đơn #${order.id}:</td>
				            <td class="price"><fmt:formatNumber value="${orderTotal}" pattern="#,##0"/> </td>
				        </tr>
				    </c:forEach>
				
				    <tr class="grand-total">
				        <td colspan="7" style="text-align: right;">TỔNG DOANH THU CỦA KHÁCH HÀNG:</td>
				        <td class="price"><fmt:formatNumber value="${grandTotal}" pattern="#,##0"/> </td>
				    </tr>
				</tbody>
            </table>
        </c:otherwise>
    </c:choose>

	<a href="javascript:history.back()" class="back-link">
	    ← Quay lại danh sách khách hàng
	</a>
</div>
</body>
</html>