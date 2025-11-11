package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DAO {

    public OrderDAO() {
        super();
    }

    // Lấy danh sách đơn hàng của khách hàng + khoảng thời gian
    public List<Order> getOrdersByCustomerAndDate(int customerId, String fromDate, String toDate) {
        List<Order> orders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT 
                o.ID, o.createdAt, o.createdBy, o.total, o.paymentMethod,
                oo.shippingAddress, oo.notes, oo.status,
                si.tblSalesStaffID
            FROM tblorder o
            LEFT JOIN tblonlineorder oo ON o.ID = oo.tblOrderID
            LEFT JOIN tblsalesinvoice si ON o.ID = si.tblOrderID
            WHERE o.tblCustomerID = ?
            """);

        // Thêm điều kiện ngày nếu có
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND o.createdAt >= ? ");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND o.createdAt <= ? ");
        }
        sql.append(" ORDER BY o.createdAt DESC");

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            ps.setInt(1, customerId);
            int index = 2;
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(index++, fromDate + " 00:00:00");
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(index++, toDate + " 23:59:59");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = createOrderFromResultSet(rs, customerId);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order createOrderFromResultSet(ResultSet rs, int customerId) throws SQLException {
        int orderId = rs.getInt("ID");
        Timestamp ts = rs.getTimestamp("createdAt");

        Order order;
        String shippingAddress = rs.getString("shippingAddress");
        Integer staffId = rs.getObject("tblSalesStaffID") != null ? rs.getInt("tblSalesStaffID") : null;

        if (shippingAddress != null) {
            // OnlineOrder
            OnlineOrder oo = new OnlineOrder();
            oo.setShippingAddress(shippingAddress);
            oo.setNotes(rs.getString("notes"));
            oo.setStatus(rs.getString("status"));
            order = oo;
        } else if (staffId != null) {
            // SalesInvoice
            SalesInvoice si = new SalesInvoice();
            si.setStaff(getSalesStaffById(staffId));
            order = si;
        } else {
            order = new Order();
        }

        // Thông tin chung
        order.setId(orderId);
        order.setCreatedAt(ts != null ? new Date(ts.getTime()) : null);
        order.setCreatedBy(rs.getObject("createdBy") != null ? rs.getInt("createdBy") : null);
        order.setTotal(rs.getFloat("total"));
        order.setPaymentMethod(rs.getString("paymentMethod"));
        order.setCustomer(getCustomerById(customerId));

        // Nạp chi tiết (rất quan trọng cho JSP)
        if (order instanceof OnlineOrder) {
            ((OnlineOrder) order).setDetails(getOnlineOrderDetails(orderId));
        } else if (order instanceof SalesInvoice) {
            ((SalesInvoice) order).setDetails(getSalesInvoiceDetails(orderId));
        }

        return order;
    }

    // Chi tiết đơn online
    private List<OnlineOrderDetail> getOnlineOrderDetails(int orderId) { // orderId ở đây là tblorder.ID
        List<OnlineOrderDetail> details = new ArrayList<>();
        
        // === SỬA SQL TẠI ĐÂY ===
        // Chúng ta phải JOIN qua tblonlineorder để liên kết
        // tblorder.ID (biến orderId) với tblonlineorderdetail.tblOrderID (liên kết với tblonlineorder.ID)
        String sql = """
            SELECT od.quantity, od.price, p.name AS productName
            FROM tblonlineorderdetail od
            JOIN tblproduct p ON od.tblProductID = p.ID
            JOIN tblonlineorder oo ON od.tblOrderID = oo.ID
            WHERE oo.tblOrderID = ?
            """;
        // === KẾT THÚC SỬA SQL ===

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // '?' bây giờ tham chiếu đến oo.tblOrderID (tức là tblorder.ID)
            ps.setInt(1, orderId); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setName(rs.getString("productName"));

                OnlineOrderDetail d = new OnlineOrderDetail();
                d.setQuantity(rs.getInt("quantity"));
                d.setPrice(rs.getDouble("price"));
                d.setProduct(p);
                details.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    // Chi tiết hóa đơn tại quầy
    private List<SalesInvoiceDetail> getSalesInvoiceDetails(int orderId) {
        List<SalesInvoiceDetail> details = new ArrayList<>();
        String sql = """
            SELECT sid.quantity, sid.price, p.name AS productName
            FROM tblsalesinvoicedetail sid
            JOIN tblproduct p ON sid.tblProductID = p.ID
            JOIN tblsalesinvoice si ON sid.tblSalesInvoiceID = si.ID
            WHERE si.tblOrderID = ?
            """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setName(rs.getString("productName"));

                SalesInvoiceDetail d = new SalesInvoiceDetail();
                d.setQuantity(rs.getInt("quantity"));
                d.setPrice(rs.getDouble("price"));
                d.setProduct(p);
                details.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    // Lấy nhân viên bán hàng
    private SalesStaff getSalesStaffById(int staffId) {
        String sql = """
            SELECT u.ID, u.name, s.position 
            FROM tbluser u 
            JOIN tblstaff s ON u.ID = s.tblUserID 
            WHERE u.ID = ?
            """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SalesStaff staff = new SalesStaff();
                staff.setId(rs.getInt("ID"));
                staff.setName(rs.getString("name"));
                staff.setPosition(rs.getString("position"));
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy khách hàng
    public Customer getCustomerById(int id) {
        String sql = "SELECT u.* FROM tbluser u JOIN tblcustomer c ON u.ID = c.tblUserID WHERE u.ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setAddress(rs.getString("address"));
                return new Customer(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}