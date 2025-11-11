package servlet;

import dao.OrderDAO;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff/OrderServlet")
public class OrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
            return;
        }

        String customerIdStr = request.getParameter("customerId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        if (customerIdStr == null || customerIdStr.isEmpty()) {
            request.setAttribute("error", "Không tìm thấy khách hàng.");
            request.getRequestDispatcher("/staff/CustomerRevenueStatisticsView.jsp").forward(request, response);
            return;
        }

        int customerId = Integer.parseInt(customerIdStr);

        // Lấy danh sách đơn hàng kèm chi tiết
        List<Order> customerOrders = orderDAO.getOrdersByCustomerAndDate(customerId, fromDate, toDate);

        // Lấy tên khách hàng
        Customer customer = orderDAO.getCustomerById(customerId);
        String customerName = (customer != null) ? customer.getName() : "Khách vãng lai";

        // Đẩy dữ liệu sang JSP
        request.setAttribute("customerId", customerId);
        request.setAttribute("customerName", customerName);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("customerOrders", customerOrders);
        request.setAttribute("loggedInUser", loggedInUser);

        // Forward đến JSP
        request.getRequestDispatcher("/staff/TransactionStatisticView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}