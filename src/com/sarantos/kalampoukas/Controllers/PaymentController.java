package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;

import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Models.DbContext;
import com.sarantos.kalampoukas.util.EmailSender;

public class PaymentController {
	
	public PaymentController() {}
	
	public boolean payBooking(long id) throws ClassNotFoundException, SQLException {
		try {
			DbContext context = new DbContext();
			boolean succeeded = context.payBooking(id);
			
			if (succeeded) {
				EmailSender emailSender = new EmailSender();
				emailSender.SendEmail(UserSession.getInstance().getEmail());
				return true;
			}			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
		}
		
		return false;
	}
	
	public boolean cancelBooking(long id) throws ClassNotFoundException, SQLException {
		try {
			DbContext context = new DbContext();			
			return 	context.cancelBooking(id);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
		}
		
		return false;
	}
}
