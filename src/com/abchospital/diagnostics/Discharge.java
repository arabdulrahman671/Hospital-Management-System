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


@WebServlet("/Discharge")
public class Discharge extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		HttpSession session = request.getSession();
		try {
			
			int patientId = (int)session.getAttribute("patientId");
			ConnectionToDatabase connection = new ConnectionToDatabase();
			Connection con = connection.connect();
			
			String sql ="UPDATE PATIENTS SET STATUS = 'discharged' WHERE PATIENT_ID=?";
			
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, patientId);
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("Discharged");
				message="Patient Discharged!";
			}else {
				message="Sorry!, Something went Wrong";
			}
		}catch(Exception e) {
			System.out.println(e);
			message="Sorry!, Something went Wrong";
		}
		session.setAttribute("message",message);
		RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
		rd.forward(request, response);
		
		
	}

}
