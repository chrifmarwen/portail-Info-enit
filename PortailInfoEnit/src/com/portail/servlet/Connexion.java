package com.portail.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twmacinta.util.Db;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Connexion() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Db mydb = new Db();
		String mail = (String) request.getParameter("email");
		String password = (String) request.getParameter("motdepasse");
		String remember = (String) request.getParameter("remember");
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(Integer.toHexString((int) (b & 0xff)));
		}
		if (mydb.authenticated(mail, sb.toString())) {
			if (remember != null) {
				Cookie remebmer_cookie = new Cookie("remember_portail", "on");
				response.addCookie(remebmer_cookie);
				Cookie mail_cookie = new Cookie("mail_portail", mail);
				Cookie pswd_cookie = new Cookie("password_portail", sb.toString());
				response.addCookie(mail_cookie);
				response.addCookie(pswd_cookie);
			} 
			this.getServletContext()
			.getRequestDispatcher("/WEB-INF/accueil.jsp")
			.forward(request, response);
		} else {
			this.getServletContext()
			.getRequestDispatcher("/WEB-INF/login.jsp")
			.forward(request, response);
		}
	}
}