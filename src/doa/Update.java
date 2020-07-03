package doa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abchospital.ConnectionToDatabase;


@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ConnectionToDatabase connection = new ConnectionToDatabase();
		
		Connection con = connection.connect();

		String sql = "UPDATE PATIENTS  SET PATIENT_NAME=?, AGE=?, DATE_OF_ADMISSION=TO_DATE(?, 'YYYY/MM/DD'), TYPE_OF_BED=?, ADDRESS=?, CITY=?, STATE=?, STATUS=?, GENDER=? WHERE PATIENT_ID=?"; 

		PreparedStatement stmt=con.prepareStatement(sql);  
		stmt.setString(1,request.getParameter("patient-name"));
		stmt.setInt(2,Integer.parseInt(request.getParameter("patient-age")));
		stmt.setString(3,request.getParameter("date-of-admission"));
		stmt.setString(4,request.getParameter("type-of-bed"));
		stmt.setString(5,request.getParameter("address"));
		stmt.setString(6,request.getParameter("city"));
		stmt.setString(7,request.getParameter("state"));
		stmt.setString(8,request.getParameter("status"));
		stmt.setString(9,request.getParameter("gender"));
		stmt.setInt(10,Integer.parseInt(request.getParameter("patient-id")));


		ResultSet rs=stmt.executeQuery();
		HttpSession session = request.getSession();
		
		if(rs.next()) {
			System.out.println("updated");
			session.setAttribute("message", "Patient Updated Successfully");
		}else {
			session.setAttribute("message", "Sorry, Something Went Wrong");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
		rd.forward(request,response);
		}
		catch(Exception e) {
			System.out.println(e);
			HttpSession session = request.getSession();
			session.setAttribute("message", "Sorry, Something Went Wrong");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
