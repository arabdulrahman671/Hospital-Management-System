<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1"><!--Bootstrap-->
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>

<link rel="stylesheet" href="styles/main.css">
<title>ABC Hospital</title>
</head>
<body>
<%
	 session = request.getSession();
	session.setAttribute("isLogin", false);

%>
<div class="container-fluid" id="title">
  <h1 class="text-center">ABC Hospital</h1>
</div> 
<br>
<br>
<br>



<div class="container col col-md-4 align-self-center ">
  <div class="justify-content-center  ">
  <form action="login" method="POST">
    <div class="form-group">
      <label for="username">User name</label>
      <input type="text" class="form-control"  required name="username" id="username"  placeholder="Enter Username">
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" class="form-control" required name="password" id="password" placeholder="Password">
    </div>
    <button type="submit" class="btn btn-primary justify-content-center">Submit</button>
  </form>
  </div>
</div>



<br>
	<br>
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
					href="https://github.com/arabdulrahman671"><i class="fa fa-github" aria-hidden="true"></i></a> <br>
				Sk. Shaila Bhanu &nbsp; <a href="https://github.com/shaila1199"><i
					class="fa fa-github" aria-hidden="true"></i></a> <br> A. Supraja <br>
				J. Sree Sai Koundinya <br> V. vennela
			</p>

		</div>
	</footer>
</body>
</html>