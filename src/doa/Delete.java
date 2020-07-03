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

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ConnectionToDatabase connection = new ConnectionToDatabase();

			Connection con = connection.connect();

			String sql = "DELETE FROM PATIENTS WHERE PATIENT_ID = ?"; 

			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(request.getParameter("patient-id")));
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				HttpSession session = request.getSession();
				session.setAttribute("message", "Patient Deleted Successfully!");
				RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
				rd.forward(request,response);
				System.out.println("Deleted");
			}

			RequestDispatcher rd = request.getRequestDispatcher("DeletePatient.jsp");
			rd.forward(request,response);
		}
		catch(Exception e) {
			HttpSession session = request.getSession();
			session.setAttribute("message", "Sorry!, Something went Wrong");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
			System.out.println(e);
		}

	}

}
