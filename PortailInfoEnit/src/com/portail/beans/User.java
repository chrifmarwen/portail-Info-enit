package com.portail.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	private int id_user;
	private String name;
	private String surname;
	private String mail;
	private char gender;
	private String birth;
	private String password;
	
	public User() {
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
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
		this.password = sb.toString();
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public User(int id_user, String name, String surname, String mail,
			char gender, String birth) {
		super();
		this.id_user = id_user;
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.gender = gender;
		this.birth = birth;
	}
	

}
