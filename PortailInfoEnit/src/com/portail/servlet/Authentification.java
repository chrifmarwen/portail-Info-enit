package com.portail.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twmacinta.util.Db;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/")
public class Authentification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authentification() {
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
		if (cookie != null) {
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
			Db db = new Db();
			if (db.authenticated(mail, password)) {
				this.getServletContext()
						.getRequestDispatcher("/WEB-INF/accueil.jsp")
						.forward(request, response);
			} 
		} else {
			this.getServletContext()
			.getRequestDispatcher("/WEB-INF/login.jsp")
			.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
