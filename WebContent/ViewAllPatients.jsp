<%@page import="java.beans.FeatureDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.abchospital.ConnectionToDatabase,doa.FetchActivePatients,java.util.ArrayList,doa.Patients"%>
<!DOCTYPE html>
<html>
<head>
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
		boolean isLogin = (boolean) session.getAttribute("isLogin");
	if (isLogin == false) {
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
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



	<div class="container col-md-6 text-center">
		<div class="justify-content-center">
			<div class="">
				<div>
					<h3>View All Active Patients</h3>
					<br> <br> <br> <br>
				</div>
			</div>
		</div>
	</div>
	<%!FetchActivePatients fetch = new FetchActivePatients();
	ArrayList<Patients> patients = fetch.fetch();%>
	<h> <%=patients.size()%></h1>

	<div class="container">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th>Patient Id</th>
					<th>Name</th>
					<th>Age</th>
					<th>Address</th>
					<th>Date of Joining</th>
					<th>Type of room</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int p = 0; p < patients.size(); p++) {
				%>
				<tr>
					<td scope="row"><%=patients.get(p).getPatientId()%></td>
					<td><%=patients.get(p).getName()%></td>
					<td><%=patients.get(p).getAge()%></td>
					<td><%=patients.get(p).getAddress()%></td>
					<td><%=patients.get(p).getDoj()%></td>
					<td><%=patients.get(p).getTypeOfBed()%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
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
	</footer> <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script> <script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script> <script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>