package com.portail.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portail.beans.Db;
import com.portail.beans.User;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Main() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookie = request.getCookies();
		String password = null;
		String mail = null;
		Db db = new Db();
		boolean Ok = false;
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			Ok = db.authenticated(user.getMail(), user.getPassword());
		} else if (cookie != null) {
			for (Cookie ck : cookie) {
				if (ck.getName().equals("remember_portail")
						&& ck.getValue().equals("on")) {
					for (Cookie cook : cookie) {
						if (cook.getName().equals("mail_portail")) {
							mail = cook.getValue();
						}
						if (cook.getName().equals("password_portail")) {
							password = cook.getValue();
						}
					}
				}
			}

			if (db.authenticated(mail, password)) {
				Ok = true;
			}
		} else {
			Ok = false;
		}
		
		if (Ok) {
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/accueil.jsp")
					.forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
					.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Db mydb = new Db();
		String mail = (String) request.getParameter("email");
		String password = (String) request.getParameter("motdepasse");
		String remember = (String) request.getParameter("remember");
		User user = new User();
		user.setPassword(password);
		user.setMail(mail);
		if (mydb.authenticated(mail, user.getPassword())) {
			session.setAttribute("user", user);
			if (remember != null) {
				Cookie remebmer_cookie = new Cookie("remember_portail", "on");
				response.addCookie(remebmer_cookie);
				Cookie mail_cookie = new Cookie("mail_portail", mail);
				Cookie pswd_cookie = new Cookie("password_portail",
						user.getPassword());
				response.addCookie(mail_cookie);
				response.addCookie(pswd_cookie);
			}
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/accueil.jsp")
					.forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
					.forward(request, response);
		}
	}
}
