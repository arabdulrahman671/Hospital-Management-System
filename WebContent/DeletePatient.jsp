<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="doa.Patients"%>
<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="styles/custom-navbar.css" type="text/css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>ABC Hospital</title>
</head>
<body>

	<%
    boolean isLogin =(boolean)session.getAttribute("isLogin");
    if(isLogin==false){
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request,response); 
    }
    %>
	<nav
		class="navbar navbar-expand-sm sticky-top navbar-light bg-light navbar-styles">
		<a class="navbar-brand" href="index.jsp">ABC Hospital</a>
		<button class="navbar-toggler d-lg-none" type="button"
			data-toggle="collapse" data-target="#collapsibleNavId"
			aria-controls="collapsibleNavId" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse " id="collapsibleNavId">
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="dropdownId"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Patient</a>
					<div class="dropdown-menu" aria-labelledby="dropdownId">
						<a class="dropdown-item" href="Register.jsp">Register</a> <a
							class="dropdown-item" href="UpdatePatient.jsp">Update</a> <a
							class="dropdown-item" href="DeletePatient.jsp">Delete</a> <a
							class="dropdown-item" href="SearchPatient.jsp">Search</a> <a
							class="dropdown-item" href="ViewAllPatients.jsp">View All
							Patients</a> <a class="dropdown-item" href="getBillingDetails.jsp">Patient
							Billing</a>
					</div></li>
				<li class="nav-item"><a class="nav-link "
					href="getMedicineDetails.jsp">Pharmacy</a></li>
				<li class="nav-item"><a class="nav-link "
					href="GetDiagnosticDetails.jsp">Diagnostics</a></li>
			</ul>
			<form class="form-inline my-2 my-lg-0" action="LogOut" method="POST">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Log
					out</button>
			</form>
		</div>
	</nav>


	<%
     session = request.getSession();
	try{
		Patients p = (Patients)session.getAttribute("patient");
		 int patientId=p.getPatientId();
		 String name=p.getName();
		 int age=p.getAge();
		 String gender=p.getGender();
		 String address=p.getAddress();
		 String doj=p.getDoj();
		 String typeOfBed=p.getTypeOfBed();
		 String status=p.getStatus();
		 String city=p.getCity();
		 String state=p.getState();%>
	<div class="container col-md-6 text-center">
		<div class="justify-content-center">
			<div class="">
				<div>
					<h3>Delete Patient</h3>
					<br> <br> <br> <br>
				</div>
			</div>
		</div>
	</div>
	<form class="container col-md-6" action="Delete" method="get">
		<div class="justify-content-center">
			<div>
				<table class="table">
					<tbody>
						<tr>
							<td><label for="patient-id">Patient ID*</label></td>
							<td><input class="form-control" name="patient-id" required
								value="<%=patientId %>" type="text"></td>
							<td><input class="form-control" value="GET" type="submit"
								onclick="form.action='GetDatatoDelete';"></td>
						</tr>

						<tr>
							<td><label for="patient-name">Patient Name*</label></td>
							<td><input class="form-control" name="patient-name"
								value="<%=name %>" type="text"></td>
						</tr>
						<tr>
							<td><label for="ssnid">Patient Age*</label></td>
							<td><input class="form-control" name="patient-age"
								value="<%=age %>" type="number"></td>
						</tr>
						<tr>
							<%String gs="",gs1="",gs2="";
	                            switch(gender) {
	                            case "male":
	                                gs="selected";
	                                break;
	                              case "female":
	                            	  gs1="selected";
		                                break;
	                              case "others":
	                            	  gs2="selected";
		                                break;
	                              default:
	                            	  gs2="selected";
		                                break;
	                 
	                            }
 %>
							<td><label for="gender">Gender*</label></td>
							<td><select name="gender" class="form-control">
									<option value="male" <%=gs %>>Male</option>
									<option value="female" <%=gs1 %>>Female</option>
									<option value="other" <%=gs2 %>>Other</option>
							</select></td>
						</tr>
						<tr>
							<td><label for="date-of-admission">Date of
									Admission*</label></td>
							<td><input class="form-control" name="date-of-admission"
								value="<%=doj %>" type="date"></td>
						</tr>

						<tr>
							<td><label for="type-of-bed">Type of bed*</label></td>
							<%String ts="",ts1="",ts2="";
	                            switch(typeOfBed) {
	                            case "general-ward":
	                                ts="selected";
	                                break;
	                              case "semi-sharing":
	                            	  ts1="selected";
		                                break;
	                              case "single-room":
	                            	  ts2="selected";
		                                break;
	                              default:
	                            	  ts2="selected";
		                                break;
	                 
	                            }
 %>
							<td><select name="type-of-bed" class="form-control">
									<option value="general-ward" <%=ts %>>General ward</option>
									<option value="semi-sharing" <%=ts1 %>>Semi sharing</option>
									<option value="single-room" <%=ts2 %>>Single Room</option>
							</select></td>
						</tr>
						<tr>
							<td><label for="address">Address*</label></td>
							<td><textarea class="form-control" name="address" rows="4"><%=address %></textarea></td>
						<tr>
							<td><label for="state">State*</label></td>
							<td><input class="form-control" name="state"
								value="<%=state %>" type="text"></td>
						</tr>
						<tr>
							<td><label for="city">City*</label></td>
							<td><input class="form-control" name="city"
								value="<%=city %>" type="text"></td>
						</tr>
						<tr>
							<%String ss="",ss1="";
	                            switch(status) {
	                            case "active":
	                                ss="selected";
	                                break;
	                              case "discharged":
	                            	  ss1="selected";
		                                break;
	                       
	                              default:
	                            	  ss1="selected";
		                                break;
	                 
	                            }
 %>
							<td><label for="status">Status*</label></td>
							<td><select name="status" class="form-control">
									<option value="active" <%=ss %>>Active</option>
									<option value="discharged" <%=ss1 %>>Discharged</option>
							</select></td>
						</tr>
						<tr>
							<td><input name="submit" class="btn btn-primary"
								type="submit" value="Delete"></td>

						</tr>
					</tbody>
				</table>
			</div>

		</div>

	</form>

	<%}
	catch(Exception e){%>

	<div class="container col-md-6 text-center">
		<div class="justify-content-center">
			<div class="">
				<div>
					<h3>Delete Patient</h3>
					<br> <br> <br> <br>
				</div>
			</div>
		</div>
	</div>
	<form class="container col-md-6" action="Delete" method="get">
		<div class="justify-content-center">
			<div>
				<table class="table">
					<tbody>
						<tr>
							<td><label for="patient-id">Patient ID*</label></td>
							<td><input class="form-control" name="patient-id" required
								type="text"></td>
							<td><input class="form-control" value="GET" type="submit"
								onclick="form.action='GetDatatoDelete';"></td>
						</tr>

						<tr>
							<td><label for="patient-name">Patient Name*</label></td>
							<td><input class="form-control" name="patient-name"
								type="text"></td>
						</tr>
						<tr>
							<td><label for="ssnid">Patient Age*</label></td>
							<td><input class="form-control" name="patient-age"
								type="number"></td>
						</tr>
						<tr>
							<td><label for="gender">Gender*</label></td>
							<td><select name="gender" class="form-control">
									<option value="male">Male</option>
									<option value="female">Female</option>
									<option value="other">Other</option>
							</select></td>
						</tr>
						<tr>
							<td><label for="date-of-admission">Date of
									Admission*</label></td>
							<td><input class="form-control" name="date-of-admission"
								type="date"></td>
						</tr>

						<tr>
							<td><label for="type-of-bed">Type of bed*</label></td>
							<td><select name="type-of-bed" class="form-control">
									<option value="general-ward">General ward</option>
									<option value="semi-sharing">Semi sharing</option>
									<option value="single-room">Single Room</option>
							</select></td>
						</tr>
						<tr>
							<td><label for="address">Address*</label></td>
							<td><textarea class="form-control" name="address"
									type="text" rows="4"></textarea></td>
						<tr>
							<td><label for="state">State*</label></td>
							<td><input class="form-control" name="state" type="text"></td>
						</tr>
						<tr>
							<td><label for="city">City*</label></td>
							<td><input class="form-control" name="city" type="text"></td>
						</tr>
						<tr>
							<td><label for="status">Status*</label></td>
							<td><select name="status" class="form-control">
									<option value="active">Active</option>
									<option value="discharged">Discharged</option>
							</select></td>
						</tr>
						<tr>
							<td><input name="submit" class="btn btn-primary"
								type="submit" value="Delete"></td>

						</tr>
					</tbody>
				</table>
			</div>

		</div>

	</form>
	<%}%>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<footer class="page-footer  text-center">
		<div style="background-color: black; color: white; padding: 30px;">
			<p>
				Developed By: <span>Mohd Abdul Rahman</span> &nbsp; <a
					href="https://arabdulrahman671.github.io/MyPortfolio/index.html"><i
					class="fa fa-address-card" aria-hidden="true"></i></a> &nbsp; <a
					href="https://github.com/arabdulrahman671"><i
					class="fa fa-github" aria-hidden="true"></i></a> <br> Sk. Shaila
				Bhanu &nbsp; <a href="https://github.com/shaila1199"><i
					class="fa fa-github" aria-hidden="true"></i></a> <br> A. Supraja <br>
				J. Sree Sai Koundinya <br> V. vennela
			</p>

		</div>
	</footer>





	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>