package dao;

import java.sql.*;

public class DAO {
	
	public static Connection con = null;
	
	public DAO(){
		String url = "jdbc:mysql://localhost:3306/qlst_dm?useSSL=false";
		String dbClass = "com.mysql.cj.jdbc.Driver";

		try {

			Class.forName(dbClass);
			con = DriverManager.getConnection (url, "root", "123456");

			System.out.println("Ket noi thanh cong");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
