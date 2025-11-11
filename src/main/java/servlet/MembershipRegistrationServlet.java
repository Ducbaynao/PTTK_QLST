	package servlet;
	
	import java.io.IOException;
	import javax.servlet.*;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.*;
	
	import dao.DAO;
	import dao.MembershipRegistrationDAO;
	import model.Customer;
	import model.MembershipRegistration;
	import model.User;
	
	@WebServlet("/MembershipRegistrationServlet")
	public class MembershipRegistrationServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	
	        try {
	            // 1️⃣ Lấy thông tin từ form
	//            int customerId = Integer.parseInt(request.getParameter("customerId"));
	            int membershipDurationYears = Integer.parseInt(request.getParameter("membershipDurationYears"));
	            String membershipLevel = request.getParameter("membershipLevel");
	            System.out.println("form: " + membershipDurationYears + " " + membershipLevel);
	            // 2️⃣ Lấy thông tin khách hàng từ session (đã đăng nhập)
	            HttpSession session = request.getSession();
	            User user = (User) session.getAttribute("user");
	            System.out.println("user: " + user.getUsername());
	            
	            if (user == null) {
	            	System.out.println("haha");
	                response.sendRedirect(request.getContextPath() + "/user/Login.jsp");
	                return;
	            }
	
	            // 3️⃣ Đóng gói dữ liệu đăng ký hội viên
	            Customer customer = new Customer(user);
	            MembershipRegistration registration = new MembershipRegistration(customer,membershipDurationYears,membershipLevel);
	            System.out.println("data " + registration.getMembershipDurationYears() + " " + registration.getCustomer().getUsername());
	
	            // 4️⃣ Lưu vào cơ sở dữ liệu
	            MembershipRegistrationDAO dao = new MembershipRegistrationDAO();
	            boolean isSaved = dao.saveMembershipRegistration(registration);
	
	            // 5️⃣ Trả kết quả lại cho view
	            if (isSaved) {
	                request.setAttribute("message", "Đăng ký thành viên thành công!");
	            } else {
	                request.setAttribute("message", "Đăng ký thất bại, vui lòng thử lại!");
	            }
	
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/CustomerInfoView.jsp");
	            dispatcher.forward(request, response);
	            
	
	
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
	            request.getRequestDispatcher("/customer/CustomerInfoView.jsp").forward(request, response);
	        }
	    }
	}
