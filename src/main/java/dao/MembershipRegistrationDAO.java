package dao;

import java.sql.*;
import model.*;

public class MembershipRegistrationDAO extends DAO{
//    private Connection con;

    public MembershipRegistrationDAO() {
//        this.con = con;
    	super();
    }

    public boolean saveMembershipRegistration(MembershipRegistration reg) {
        String sql = "INSERT INTO tblmembershipregistration (tblCustomerID, membershipDurationYears, membershipLevel) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reg.getCustomer().getId());
            ps.setInt(2, reg.getMembershipDurationYears());
            ps.setString(3, reg.getMembershipLevel());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
