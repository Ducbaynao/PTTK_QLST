package servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        // Hủy session (xóa toàn bộ dữ liệu người dùng đang đăng nhập)
        HttpSession session = request.getSession(false); // false: không tạo session mới nếu chưa có
        if (session != null) {
            session.invalidate();
        }

        // Chuyển hướng người dùng về trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
