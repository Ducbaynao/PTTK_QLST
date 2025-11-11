package servlet;

import dao.OrderDAO;
import dao.RevenueStatisticsDAO;
import model.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/RevenueStatisticsServlet")
public class RevenueStatisticsServlet extends HttpServlet {

    private RevenueStatisticsDAO revenueDAO;
    private OrderDAO orderDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
            return;
        }

        // === KHỞI TẠO DAO ===
        try {
            if (revenueDAO == null) revenueDAO = new RevenueStatisticsDAO();
            if (orderDAO == null) orderDAO = new OrderDAO();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi kết nối CSDL!");
            request.getRequestDispatcher("/staff/CustomerRevenueStatisticsView.jsp").forward(request, response);
            return;
        }

        String customerIdStr = request.getParameter("customerId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        // === XEM CHI TIẾT GIAO DỊCH ===
        if (customerIdStr != null && !customerIdStr.isEmpty()) {
            try {
                int customerId = Integer.parseInt(customerIdStr);

                List<Order> customerOrders = orderDAO.getOrdersByCustomerAndDate(customerId, fromDate, toDate);
                Customer customer = orderDAO.getCustomerById(customerId);
                String customerName = (customer != null) ? customer.getName() : "Khách vãng lai";

                request.setAttribute("customerName", customerName);
                request.setAttribute("customerOrders", customerOrders);
                request.setAttribute("fromDate", fromDate);
                request.setAttribute("toDate", toDate);
                request.setAttribute("loggedInUser", loggedInUser);

                request.getRequestDispatcher("/staff/TransactionStatisticView.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi xem chi tiết: " + e.getMessage());
                request.getRequestDispatcher("/staff/CustomerRevenueStatisticsView.jsp").forward(request, response);
                return;
            }
        }

        // === THỐNG KÊ TỔNG ===
        if (fromDate == null || fromDate.isEmpty() || toDate == null || toDate.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn đầy đủ ngày!");
            request.getRequestDispatcher("/staff/CustomerRevenueStatisticsView.jsp").forward(request, response);
            return;
        }

        try {
            List<RevenueStatistics> list = revenueDAO.getRevenueStatisticsByDate(fromDate, toDate);

            request.setAttribute("statistics", list);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);

            request.getRequestDispatcher("/staff/CustomerRevenueStatisticsView.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi thống kê: " + e.getMessage());
            request.getRequestDispatcher("/staff/CustomerRevenueStatisticsView.jsp").forward(request, response);
        }
    }
}