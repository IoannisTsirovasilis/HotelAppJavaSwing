package com.sarantos.kalampoukas.Models;

public class User implements IModel {
	private int id;
	private String name;
	private String surname;
	private String email;
	private String mobile;
	private int role_id;
	private String password_hash;
	
	public User(int id, String name, String surname, String email,
			String mobile, int role_id, String password_hash) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.mobile = mobile;
		this.role_id = role_id;
		this.password_hash = password_hash;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public int getRoleId() {
		return role_id;
	}
	
	public String getPasswordHash() {
		return password_hash;
	}
}
