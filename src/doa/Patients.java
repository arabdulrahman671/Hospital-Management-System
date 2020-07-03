package doa;

public class Patients{
		private int patientId;
		private String name;
		private int age;
		private String gender;
		private String address;
		private String doj;
		private String typeOfBed;
		private String status;
		private String city,state;
		public Patients(int patientId, String name, int age, String gender, String address, String doj,
				String typeOfBed, String status, String city, String state) {
			this.patientId = patientId;
			this.name = name;
			this.age = age;
			this.gender = gender;
			this.address = address;
			this.doj = doj;
			this.typeOfBed = typeOfBed;
			this.status = status;
			this.city = city;
			this.state = state;
		}
		public Patients(int patientId, String name, int age,String gender, String address, String doj, String typeOfBed) {
			this.patientId = patientId;
			this.name = name;
			this.age = age;
			this.gender=gender;
			this.address = address;
			this.doj = doj;
			this.typeOfBed = typeOfBed;
		}
		public int getPatientId() {
			return patientId;
		}
		public void setPatientId(int patientId) {
			this.patientId = patientId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getAddress() {
			return address;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getDoj() {
			return doj;
		}
		public void setDoj(String doj) {
			this.doj = doj;
		}
		public String getTypeOfBed() {
			return typeOfBed;
		}
		public Patients(int patientId, String name, int age, String address, String doj, String typeOfBed) {
			this.patientId = patientId;
			this.name = name;
			this.age = age;
			this.address = address;
			this.doj = doj;
			this.typeOfBed = typeOfBed;
		}
		public void setTypeOfBed(String typeOfBed) {
			this.typeOfBed = typeOfBed;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
	
}
