package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;
import java.util.List;

import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Models.DbContext;
import com.sarantos.kalampoukas.Models.Role;
import com.sarantos.kalampoukas.Models.User;

public class UserController {
	
	public UserController() {}
	
	public User login(String email, String password, String role) throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			User user = context.findUserByEmailAndRole(email, role);
			
			if (user == null) return user;
			
			return user.getPasswordHash().equals(password) ? user : null;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			throw new Exception();
		}		
	}
	
	public void register(User newUser, String role) throws Exception, IllegalArgumentException {
		DbContext context;
		try {
			context = new DbContext();
			System.out.println(newUser.getEmail());
			User user = context.findUserByEmailAndRole(newUser.getEmail(), role);
			if (user != null) {
				throw new IllegalArgumentException();
			}
			context.registerCustomer(newUser);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			throw new Exception();
		}		
	}
	
	public User getCustomerByEmail(String email) throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			User user = context.findUserByEmailAndRole(email, "Customer");			
			return user;
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			throw new Exception();
		}	
	}
	
	public static List<Role> getRoles() throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			return context.getRoles();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			throw new Exception();
		}	
	}
	
	public static void logOff() {
		UserSession.getInstance().cleanUserSession();
	}	
}
