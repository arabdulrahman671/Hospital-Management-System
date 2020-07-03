package com.abchospital.pharmacy;

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

import doa.Patients;

@WebServlet("/GetMedicineDetails")
public class GetMedicineDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionToDatabase connection = new ConnectionToDatabase();
		HttpSession session = request.getSession();
		boolean isPatientFound =false;

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
				isPatientFound =true;
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
				String sql4;

				sql4 ="Select * From MEDICINES_MASTER WHERE AVAILABLE_QUANTITY  > 0 ";
				stmt=con.prepareStatement(sql4);
				rs=stmt.executeQuery();
				ArrayList<Medicines> medicinesNotIssued = new ArrayList<Medicines>();
				while(rs.next()) {
					String medicineName = rs.getString("NAME");
					int rate = rs.getInt("RATE");
					int medicineId = rs.getInt("MEDICINE_ID");
					int availableQuantity = rs.getInt("AVAILABLE_QUANTITY");
					Medicines mNot = new Medicines(medicineName,medicineId,rate,availableQuantity);
					System.out.println(medicineId+" "+medicineName+" "+availableQuantity+" "+rate);
					medicinesNotIssued.add(mNot);
				}
				session.setAttribute("medicinesNotIssued", medicinesNotIssued);
				RequestDispatcher rd= request.getRequestDispatcher("MedicineDetails.jsp");
				rd.forward(request, response);
			}
			if(isPatientFound==false) {
				session = request.getSession();
				System.out.println("Executing");
				session.setAttribute("message", "Sorry!, Incorrect Patient id");
				RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
				rd.forward(request,response);
			}
		}catch(Exception e) {
			System.out.println(e);
			session = request.getSession();
			session.setAttribute("message", "Sorry!, Something Went Wrong");
			RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
			rd.forward(request,response);
		}
	}

}
