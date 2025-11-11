package dao;

import model.Customer;
import model.RevenueStatistics;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;        
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueStatisticsDAO extends DAO {

    public RevenueStatisticsDAO() {
        super();
    }

    public List<RevenueStatistics> getRevenueStatisticsByDate(String fromDate, String toDate) {
        List<RevenueStatistics> result = new ArrayList<>();

      
        String sql = """
            SELECT 
                c.tblUserID AS customerId,
                u.name AS customerName,
                u.email,
                u.phoneNumber,
                u.address,
                COALESCE(SUM(o.total), 0) AS totalRevenue 
            FROM tblcustomer c
            JOIN tbluser u ON c.tblUserID = u.ID
            LEFT JOIN tblorder o ON o.tblCustomerID = c.tblUserID
            """; 

        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql += " AND o.createdAt >= ? ";
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql += " AND o.createdAt <= ? ";
        }

  
        sql += """
            GROUP BY c.tblUserID, u.name, u.email, u.phoneNumber, u.address
            ORDER BY totalRevenue DESC
            """;
      


        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int idx = 1;
            
      
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                ps.setString(idx++, fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                ps.setString(idx++, toDate);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("customerId"));
                user.setName(rs.getString("customerName"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setAddress(rs.getString("address"));

                Customer customer = new Customer(user);
                double total = rs.getDouble("totalRevenue");

                RevenueStatistics stat = new RevenueStatistics(customer, total);
                result.add(stat);
            }
        } catch (SQLException e) {
            System.err.println("LỖI SQL trong RevenueStatisticsDAO:");
            e.printStackTrace();
        }
        return result;
    }
}
