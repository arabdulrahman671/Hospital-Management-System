package com.abchospital;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/Register")
public class Register extends HttpServlet {


	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionToDatabase connect = new ConnectionToDatabase();
		try {
			Connection con = connect.connect();

			String sql = "INSERT INTO PATIENTS (PATIENT_SSN,PATIENT_NAME,AGE,DATE_OF_ADMISSION,TYPE_OF_BED,ADDRESS,CITY,STATE,STATUS,GENDER) VALUES(?,?,?,TO_DATE(?, 'YYYY/MM/DD'),?,?,?,?,?,?)";

			PreparedStatement stmt=con.prepareStatement(sql);  
			stmt.setInt(1,Integer.parseInt(request.getParameter("ssnid")));
			stmt.setString(2,request.getParameter("patient-name"));
			stmt.setInt(3,Integer.parseInt(request.getParameter("patient-age")));
			stmt.setString(4,request.getParameter("date-of-admission"));
			stmt.setString(5,request.getParameter("type-of-bed"));
			stmt.setString(6,request.getParameter("address"));
			stmt.setString(7,request.getParameter("city"));
			stmt.setString(8,request.getParameter("state"));
			stmt.setString(9,request.getParameter("status"));
			stmt.setString(10,request.getParameter("gender"));

			ResultSet rs=stmt.executeQuery();  
			if(rs.next()) {
				HttpSession session = request.getSession();
				session.setAttribute("message", "Patient Registered!");
				RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
				rd.forward(request,response);
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("message", "Sorry!, Something went Wrong.");
				RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
				rd.forward(request,response);
			}
		}
		catch(Exception e) {
			System.out.println(e);
			HttpSession session = request.getSession();
			session.setAttribute("message", "Sorry!, Something went Wrong.");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
		}

		System.out.println("s"+request.getParameter("patient-name")
		+request.getParameter("patient-age")+request.getParameter("gender")
		+request.getParameter("date-of-admission")+request.getParameter("type-of-bed")
		+request.getParameter("address")+request.getParameter("state")
		+request.getParameter("city")+request.getParameter("status"));

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
