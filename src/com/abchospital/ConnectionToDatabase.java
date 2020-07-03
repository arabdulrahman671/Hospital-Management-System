package com.abchospital;
import java.sql.*;
public class ConnectionToDatabase {
	public Connection connect(){

		try{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");  

			//step2 create  the connection object  
			Connection con=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  


			return con;


		}catch(Exception e){
			System.out.println(e);}  
		return null;
	}  

}

