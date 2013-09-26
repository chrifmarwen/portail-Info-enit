package com.twmacinta.util;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.portail.servlet.Connexion;

public class Db {
	private String dbURL = "";
	private String user = "";
	private String password = "";
	private java.sql.Connection dbConnect = null;
	private java.sql.Statement dbStatement = null;
	private Properties prop = new Properties();

	public Boolean connect() throws IOException {
		prop.load(this.getClass().getResourceAsStream("/dbconfig.properties"));
		this.dbURL = prop.getProperty("jdbc.url");
		this.user = prop.getProperty("jdbc.user");
		this.password = prop.getProperty("jdbc.password");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.dbConnect = DriverManager.getConnection("jdbc:mysql:"
					+ this.dbURL, this.user, this.password);
			this.dbStatement = this.dbConnect.createStatement();
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	public void close() {
		try {
			this.dbStatement.close();
			this.dbConnect.close();
		} catch (SQLException ex) {
			Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public boolean authenticated(String mail, String pswd) {
		try {
			connect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			ResultSet rs = this.dbStatement
					.executeQuery("SELECT * from portail.user,portail.auth where (auth_pswd='"
							+ pswd
							+ "'and user.user_ID = auth.user_ID and user.user_mail='"
							+ mail + "')");
			if (rs.next()) {
				close();
				return true;
			} else {
				close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			close();
			return false;
		}
		
	}
}
