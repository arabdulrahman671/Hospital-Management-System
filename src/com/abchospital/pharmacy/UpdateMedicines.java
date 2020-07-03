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

@WebServlet("/UpdateMedicines")
public class UpdateMedicines extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			ConnectionToDatabase connection = new ConnectionToDatabase();
			Connection con = connection.connect();
			int patientId = (int)session.getAttribute("patientId");
			HashMap<Integer,Integer> medicineIdsWithQuantity =new HashMap<Integer,Integer>();
			String sql = "SELECT MEDICINE_ID FROM MEDICINES_MASTER";

			/*for(String s: checkedBoxes) {
				System.out.println(s);
				String sql ="INSERT INTO DIAGNOSTIC_TESTS(patient_id,test_id) VALUES(?,?)";
				PreparedStatement stmt=con.prepareStatement(sql);  
				stmt.setInt(1,patientId);
				stmt.setInt(2,Integer.parseInt(s));
				ResultSet rs=stmt.executeQuery();
				if(rs.next()) {
					count++;
			}	}*/
			ArrayList<Integer> medicineIds= new ArrayList<Integer>();
			PreparedStatement stmt=con.prepareStatement(sql);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				medicineIds.add(rs.getInt("MEDICINE_ID"));
			}
			System.out.println(" All Medicine Ids in meddicine Master:" );
			for(Integer ids:medicineIds) {
				System.out.println(ids);

			}
			int count=0;
			boolean isMedicineIssued = false;
			ArrayList<Integer> idsGreterThanZero = new ArrayList<Integer>();
			for(Integer ids:medicineIds) {
				medicineIdsWithQuantity.put(ids,Integer.parseInt(request.getParameter(""+ids)));

			}



			for (Map.Entry<Integer, Integer> set : medicineIdsWithQuantity.entrySet()) {
				System.out.println(set.getKey() + "-id : Quantity- " + set.getValue());
				if(set.getValue()>0) {
					idsGreterThanZero.add(set.getKey());
					isMedicineIssued = true;
				}

			}
			if(isMedicineIssued) {
				String sql1="";
				for(Integer ids:idsGreterThanZero) {
					sql1="UPDATE MEDICINES_MASTER SET available_quantity = available_quantity -"+medicineIdsWithQuantity.get(ids)+" WHERE medicine_id="+ids;
					System.out.println(sql1);
					rs=stmt.executeQuery(sql1);
				}
				if(rs.next()) {
					System.out.println("Master Table Updated");
				}


				ArrayList<Medicines> m = (ArrayList<Medicines>)session.getAttribute("medicines");
				ArrayList<Integer> idsOfMedicinesIssued = new ArrayList<Integer>();
				System.out.println("m: "+m);
				if(m==null) {

				}else {
					for(Medicines i: m) {
						if(i==null) {

						}else {
							idsOfMedicinesIssued.add(i.getMedicineId());
						}

					}
				}

				for(Integer i:idsOfMedicinesIssued) {
					System.out.println("ids of medicines Issued : "+ i);
				}
				String sql2="";
				for(Integer ids:idsGreterThanZero) {
					if(idsOfMedicinesIssued.contains(ids)) {
						sql2="UPDATE MEDICINES_ISSUED SET QUANTITY = QUANTITY +"+medicineIdsWithQuantity.get(ids)+" WHERE medicine_id="+ids+"AND PATIENT_ID="+patientId;
						System.out.println(sql2);
						rs=stmt.executeQuery(sql2);
					}
					else {
						sql2="INSERT INTO MEDICINES_ISSUED (PATIENT_ID,MEDICINE_ID,QUANTITY) VALUES("+patientId+","+ids+","+medicineIdsWithQuantity.get(ids)+")";
						System.out.println(sql2);
						rs=stmt.executeQuery(sql2);
					}

				}
				if(rs.next()) {
					System.out.println("Medicine Issued Table Updated");
				}



				session.setAttribute("message", "Medicines Updated");

			}
			else {
				System.out.println("medicines not issued");
				session.setAttribute("message", "Medicines Updated");
			}

		}catch(Exception e) {
			System.out.println(e);
			session.setAttribute("message", "Sorry!, Something Went Wrong");

		}		
		RequestDispatcher rd = request.getRequestDispatcher("Message.jsp");
		rd.forward(request, response);

	}

}
