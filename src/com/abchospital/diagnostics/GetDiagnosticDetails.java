package com.abchospital.diagnostics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abchospital.ConnectionToDatabase;

import doa.Patients;

@WebServlet("/GetDiagnosticDetails")
public class GetDiagnosticDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionToDatabase connection = new ConnectionToDatabase();
		HttpSession session = request.getSession();
		boolean isPatientFound = false;
		try {
			Connection con = connection.connect();
			String sql ="SELECT PATIENT_NAME,AGE,ADDRESS,DATE_OF_ADMISSION,TYPE_OF_BED From PATIENTS WHERE PATIENT_ID=?";
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(request.getParameter("patient-id")));
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				int patientId = Integer.parseInt(request.getParameter("patient-id"));
				String name = rs.getString("PATIENT_NAME");
				int age = rs.getInt("AGE");
				//String gender = rs.getString("GENDER");
				String address = rs.getString("ADDRESS");
				String doj = rs.getString("DATE_OF_ADMISSION");
				String temp[] = doj.split(" ");
				doj=temp[0];
				String typeOfBed = rs.getString("TYPE_OF_BED");
				isPatientFound = true;
				Patients p = new Patients(patientId,name,age,address,doj,typeOfBed);
				session.setAttribute("patient", p);
			}
			String sql1 ="Select * From diagnostic_tests WHERE PATIENT_ID="+Integer.parseInt(request.getParameter("patient-id"));
			stmt=con.prepareStatement(sql1);
			System.out.println("sql1 :" +sql1);
			rs=stmt.executeQuery();
			ArrayList<Integer> testIds = new ArrayList<Integer>();
			boolean isTestIds = false;
			while(rs.next()) {
				System.out.println("in loop");
				testIds.add(rs.getInt("TEST_ID"));
				isTestIds = true;
				session.setAttribute("doneDiagnostics", true);
			}
			String ids1="()";
			if(isTestIds) {
				String sql2 = "Select * From DIAGNOSTIC_TESTS_MASTER WHERE TEST_ID IN ";
				String ids="(";
				for(Integer i:testIds) {
					ids=ids+i+",";
				}
				System.out.println("Test ids : " +ids);
				StringBuffer sb= new StringBuffer(ids);
				sb.deleteCharAt(sb.length()-1);
				ids1 = new String(sb);
				ids1= ids1+")";
				sql2 = sql2+ids1;
				System.out.println(sql2);
				stmt=con.prepareStatement(sql2);
				rs=stmt.executeQuery();
				ArrayList<Diagnostics> diagnostics = new ArrayList<Diagnostics>();
				while(rs.next()) {
					String name = rs.getString("TEST_NAME");
					int charge = rs.getInt("CHARGE");
					int testId = rs.getInt("TEST_ID");
					Diagnostics d = new Diagnostics(testId,name,charge);
					System.out.println(name+" "+charge+" "+testId);
					diagnostics.add(d);
				}
				session.setAttribute("diagnostics", diagnostics);
			}
			String sql4;
			if(isTestIds) {
				sql4 ="Select * From DIAGNOSTIC_TESTS_MASTER WHERE TEST_ID  NOT IN "+ids1;
			}
			else {
				sql4 ="Select * From DIAGNOSTIC_TESTS_MASTER";
			}

			stmt=con.prepareStatement(sql4);
			rs=stmt.executeQuery();
			ArrayList<Diagnostics> diagnosticsNotDone = new ArrayList<Diagnostics>();
			while(rs.next()) {
				String name = rs.getString("TEST_NAME");
				int charge = rs.getInt("CHARGE");
				int testId = rs.getInt("TEST_ID");
				Diagnostics d = new Diagnostics(testId,name,charge);
				System.out.println(name+" "+charge+" "+testId);
				diagnosticsNotDone.add(d);
			}
			session.setAttribute("diagnosticsNotDone", diagnosticsNotDone);
			RequestDispatcher rd= request.getRequestDispatcher("DiagnosticDetails.jsp");
			rd.forward(request, response);

			if(isPatientFound==false) {
				session = request.getSession();
				System.out.println("Executing");
				session.setAttribute("message", "Sorry!, Incorrect Patient id");
				rd = request.getRequestDispatcher("Message.jsp");
				rd.forward(request,response);
			}

		}catch(Exception e) {
			session = request.getSession();
			session.setAttribute("message", "Sorry!, Something Went Wrong");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
			System.out.println(e);

		}

	}

}
