package com.abchospital.diagnostics;

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

@WebServlet("/UpdateDiagnostics")
public class UpdateDiagnostics extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			ConnectionToDatabase connection = new ConnectionToDatabase();
			Connection con = connection.connect();
			int patientId = (int)session.getAttribute("patientId");
			String checkedBoxes[] = request.getParameterValues("checkbox");
			int count =0;
			for(String s: checkedBoxes) {
				System.out.println(s);
				String sql ="INSERT INTO DIAGNOSTIC_TESTS(patient_id,test_id) VALUES(?,?)";
				PreparedStatement stmt=con.prepareStatement(sql);  
				stmt.setInt(1,patientId);
				stmt.setInt(2,Integer.parseInt(s));
				ResultSet rs=stmt.executeQuery();
				if(rs.next()) {
					count++;
				}

			}
			System.out.println(count+" rows Added");
			session.setAttribute("message", count+" Diagnostics Test Added");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request, response);

		}catch(Exception e) {
			System.out.println(e);
			session = request.getSession();
			session.setAttribute("message", "Sorry!, Something Went Wrong");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
		}



	}

}
