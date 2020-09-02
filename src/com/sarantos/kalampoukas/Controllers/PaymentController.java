package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;

import javax.mail.MessagingException;

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
				System.out.println("OK");
				EmailSender emailSender = new EmailSender();
				emailSender.sendEmail(UserSession.getInstance().getEmail());
				return true;
			}			
		} catch (MessagingException mex) {
			System.out.println(mex.toString());
			mex.printStackTrace();
			return true;
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
