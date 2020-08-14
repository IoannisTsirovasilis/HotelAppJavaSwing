package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;

import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Models.DbContext;
import com.sarantos.kalampoukas.Models.User;

public class UserController {
	
	public UserController() {}
	
	public User login(String email, String password) throws ClassNotFoundException, SQLException {
		DbContext context = new DbContext();
		
		User user = context.findUserByEmail(email);
		
		if (user == null) return user;
		
		return user.getPasswordHash().equals(password) ? user : null;
	}
	
	public static void logOff() {
		UserSession.getInstance().cleanUserSession();
	}	
}
