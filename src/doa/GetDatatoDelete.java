package doa;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abchospital.ConnectionToDatabase;

import java.sql.*;


@WebServlet("/GetDatatoDelete")
public class GetDatatoDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isPatientFound = false;
		ConnectionToDatabase connection = new ConnectionToDatabase();
		try{
			Connection con =  connection.connect();
			int patientId = Integer.parseInt(request.getParameter("patient-id"));
			String sql ="SELECT PATIENT_NAME,AGE,GENDER,ADDRESS,DATE_OF_ADMISSION,TYPE_OF_BED,STATUS,CITY,STATE FROM PATIENTS WHERE PATIENT_ID = ?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, patientId);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				String status = rs.getString("STATUS");
				String name = rs.getString("PATIENT_NAME");
				int age = rs.getInt("AGE");
				String gender = rs.getString("GENDER");
				String address = rs.getString("ADDRESS");
				String doj = rs.getString("DATE_OF_ADMISSION");
				String temp[] = doj.split(" ");
				doj=temp[0];
				String typeOfBed = rs.getString("TYPE_OF_BED");
				String city = rs.getString("CITY");
				String state = rs.getString("STATE");
				PrintWriter out = response.getWriter();
				isPatientFound = true;
				out.println(status+" "+name+" "+age+" "+address+" "+doj+" "+" "+typeOfBed);
				Patients p = new Patients(patientId, name, age, gender, address, doj, typeOfBed, status, city, state);
				HttpSession session = request.getSession();

				session.setAttribute("patient", p);

				RequestDispatcher rd = request.getRequestDispatcher("DeletePatient.jsp");
				rd.forward(request,response);

			}
			if(isPatientFound==false) {
				HttpSession session = request.getSession();
				System.out.println("Executing");
				session.setAttribute("message", "Sorry!, Incorrect Patient id");
				RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
				rd.forward(request,response);
			}

		}catch (Exception e) {
			HttpSession session = request.getSession();
			session.setAttribute("message", "Sorry!, Incorrect Patient id");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
			System.out.println(e);
		}



	}


}
