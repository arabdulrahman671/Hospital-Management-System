package doa;
import java.sql.*;
import java.util.ArrayList;

import com.abchospital.ConnectionToDatabase;
public class FetchActivePatients {

	public ArrayList<Patients> fetch() {
		try {
			ConnectionToDatabase connection = new ConnectionToDatabase();
			Connection con = connection.connect();

			String sql = "SELECT PATIENT_ID,PATIENT_NAME,AGE,ADDRESS,DATE_OF_ADMISSION,TYPE_OF_BED FROM PATIENTS WHERE STATUS = 'active'";

			PreparedStatement stmt=con.prepareStatement(sql);  


			ResultSet rs=stmt.executeQuery();  
			ArrayList<Patients> patients = new ArrayList<Patients>();
			while(rs.next()) {
				int patientId = rs.getInt("PATIENT_ID");
				String name = rs.getString("PATIENT_NAME");
				int age = rs.getInt("AGE");
				//String gender = rs.getString("GENDER");
				String address = rs.getString("ADDRESS");
				String doj = rs.getString("DATE_OF_ADMISSION");
				String temp[] = doj.split(" ");
				doj=temp[0];
				String typeOfBed = rs.getString("TYPE_OF_BED");
				System.out.println(patientId+" "+name+" "+age+" "+address+" "+doj+" "+" "+typeOfBed);
				Patients p = new Patients( patientId,  name,  age,  address,  doj,  typeOfBed);
				patients.add(p);
				}
			return patients;
		}

		catch (Exception e) {
			System.out.println(e);

			return null;
		}
	}
}
