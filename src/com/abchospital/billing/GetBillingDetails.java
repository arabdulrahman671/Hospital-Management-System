package com.abchospital.billing;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abchospital.ConnectionToDatabase;
import com.abchospital.diagnostics.Diagnostics;
import com.abchospital.pharmacy.Medicines;

import doa.Patients;

@WebServlet("/GetBillingDetails")
public class GetBillingDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionToDatabase connection = new ConnectionToDatabase();
		HttpSession session = request.getSession();
		String dateOfAdmission="";
		String tob ="";
		boolean isPatientFound=false;
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
				dateOfAdmission=doj;
				String typeOfBed = rs.getString("TYPE_OF_BED");
				tob = typeOfBed;
				isPatientFound=true;
				Patients p = new Patients(patientId,name,age,address,doj,typeOfBed);
				session.setAttribute("patient", p);
				String sql1 ="Select * From MEDICINES_ISSUED WHERE PATIENT_ID="+Integer.parseInt(request.getParameter("patient-id"));
				stmt=con.prepareStatement(sql1);
				System.out.println("sql1 :" +sql1);
				rs=stmt.executeQuery();
				HashMap<Integer,Integer> medicineIdsWithQuantity = new HashMap<Integer,Integer>();

				boolean isMedicinesIssued = false;
				while(rs.next()) {
					System.out.println("in loop");
					medicineIdsWithQuantity.put(rs.getInt("MEDICINE_ID"), rs.getInt("QUANTITY"));
					isMedicinesIssued = true;
					session.setAttribute("isMedicinesIssued", true);
				}
				String ids1="()";
				if(isMedicinesIssued) {
					String sql2 = "Select * From MEDICINES_MASTER WHERE MEDICINE_ID IN ";
					String ids="(";
					for (Map.Entry<Integer, Integer> set : medicineIdsWithQuantity.entrySet()) {
						System.out.println(set.getKey() + " = " + set.getValue());
						ids=ids+set.getKey()+",";
					}
					System.out.println("Medicine ids : " +ids);
					StringBuffer sb= new StringBuffer(ids);
					sb.deleteCharAt(sb.length()-1);
					ids1 = new String(sb);
					ids1= ids1+")";
					sql2 = sql2+ids1;
					System.out.println(sql2);
					stmt=con.prepareStatement(sql2);
					rs=stmt.executeQuery();
					ArrayList<Medicines> medicines = new ArrayList<Medicines>();
					while(rs.next()) {
						String medicineName = rs.getString("NAME");
						int rate = rs.getInt("RATE");
						int medicineId = rs.getInt("MEDICINE_ID");
						int quantityIssued = medicineIdsWithQuantity.get(medicineId);
						Medicines m = new Medicines(medicineId, medicineName, quantityIssued, rate);
						System.out.println(medicineId+" "+medicineName+" "+quantityIssued+" "+rate);
						medicines.add(m);
					}
					session.setAttribute("medicines", medicines);
				}
				String sql2 ="Select * From diagnostic_tests WHERE PATIENT_ID="+Integer.parseInt(request.getParameter("patient-id"));
				stmt=con.prepareStatement(sql2);
				System.out.println("sql1 :" +sql2);
				rs=stmt.executeQuery();
				ArrayList<Integer> testIds = new ArrayList<Integer>();
				boolean isTestIds = false;
				while(rs.next()) {
					System.out.println("in loop");
					testIds.add(rs.getInt("TEST_ID"));
					isTestIds = true;
					session.setAttribute("doneDiagnostics", true);
				}
				String ids2="()";
				if(isTestIds) {
					String sql3 = "Select * From DIAGNOSTIC_TESTS_MASTER WHERE TEST_ID IN ";
					String ids="(";
					for(Integer i:testIds) {
						ids=ids+i+",";
					}
					System.out.println("Test ids : " +ids);
					StringBuffer sb= new StringBuffer(ids);
					sb.deleteCharAt(sb.length()-1);
					ids2 = new String(sb);
					ids2= ids2+")";
					sql3 = sql3+ids2;
					System.out.println(sql3);
					stmt=con.prepareStatement(sql3);
					rs=stmt.executeQuery();
					ArrayList<Diagnostics> diagnostics = new ArrayList<Diagnostics>();
					while(rs.next()) {
						String testName = rs.getString("TEST_NAME");
						int charge = rs.getInt("CHARGE");
						int testId = rs.getInt("TEST_ID");
						Diagnostics d = new Diagnostics(testId,testName,charge);
						System.out.println(testName+" "+charge+" "+testId);
						diagnostics.add(d);
					}
					session.setAttribute("diagnostics", diagnostics);
				}				
			}
			DateProcessor dp = new DateProcessor();
			System.out.println("Current Date"+dp.getDate());
			session.setAttribute("currentDate", dp.getDate());
			long dateDiff = dp.getDateDifference(dateOfAdmission, dp.getDate());
			System.out.println("Date Difference"+dateDiff);
			session.setAttribute("dateDifference", dateDiff);
			int roomCharges=0;
			switch(tob) {
			case "general-ward":
				roomCharges= 2000;
				break;
			case "semi-sharing":
				roomCharges= 4000;
				break;
			case "single-room":
				roomCharges= 8000;
				break;
			default:
				roomCharges= 0;
			}
			long totalBedCharges =roomCharges*dateDiff;
			session.setAttribute("totalBedCharges", totalBedCharges);
			RequestDispatcher rd = request.getRequestDispatcher("BillingDetails.jsp");
			rd.forward(request,response);
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