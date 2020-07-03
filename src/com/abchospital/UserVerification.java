package com.abchospital;
import java.sql.*;
public class UserVerification {
	ConnectionToDatabase connect = new ConnectionToDatabase();
	public boolean user(String username, String password) {
		// TODO Auto-generated method stub
		try {

			Connection con = connect.connect();
			if(con!=null) {
				String sql = "SELECT * FROM USERSTORE WHERE USERNAME=? AND PASSWORD=?";
				PreparedStatement stmt=con.prepareStatement(sql);  
				stmt.setString(1,username);
				stmt.setString(2,password);				
				ResultSet rs=stmt.executeQuery();  
				if(rs.next()) {
					return true;
				}

			}
			return false;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}



}
