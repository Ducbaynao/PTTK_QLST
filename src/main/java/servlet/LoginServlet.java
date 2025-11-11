package servlet;

import dao.UserDAO;
import model.User;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/user/Login")
public class LoginServlet extends HttpServlet {
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        // Nếu người dùng gõ URL trực tiếp, chuyển họ đến trang login
	        request.getRequestDispatcher("/user/Login.jsp").forward(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        UserDAO dao = new UserDAO();
	        User u = dao.checkLogin(username, password);

	        if (u != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("user", u);

	            switch (u.getRole()) {
	                case "staff":
	                    response.sendRedirect(request.getContextPath() + "/staff/ManagementStaffView.jsp");
	                    break;
	                case "customer":
	                    response.sendRedirect(request.getContextPath() + "/customer/CustomerView.jsp");
	                    break;
	                default:
	                    response.sendRedirect(request.getContextPath() + "/home.jsp");
	                    break;
	            }
	        } else {
	            request.setAttribute("error", "Sai username hoặc password");
	            request.getRequestDispatcher("/user/Login.jsp").forward(request, response);
	        }
	    }

}
