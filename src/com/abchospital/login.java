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

@WebServlet("/login")
public class login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserVerification verify = new UserVerification();
		HttpSession session = request.getSession();
		session.setAttribute("isLogin", false);
		if(verify.user(request.getParameter("username"),request.getParameter("password"))) {
			session.setAttribute("isLogin", true);
			RequestDispatcher rd = request.getRequestDispatcher("Register.jsp");
			rd.forward(request, response);
		}
		else {
			PrintWriter out = response.getWriter();
			out.println("Incorrect Password");
			session.setAttribute("isLogin", false);
		}

	}


}

