package com.sarantos.kalampoukas.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 

public class EmailSender {
	
	private static final String HOST = "smtp.mandrillapp.com";
	private static final int PORT = 587;
	private static final String USERNAME = "MyHotel";
	private static final String PASSWORD = "tGK__ly_tLW4qSLn8uDy_Q";
	private static final String SENDER_EMAIL = "g.kalaboukas@pivo-uzi.club";
	private static final String SUBJECT = "Reservation";
	private static final String EMAIL_BODY = "Reservation confirmed!";
	
	public void sendEmail(String email) throws MessagingException {
		email = "g.kalaboukas@pivo-uzi.club";
		Properties props = new Properties();  
		props.put("mail.smtp.host", HOST);  
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", PORT); 
	     
		Session session = Session.getInstance(props,  
			new javax.mail.Authenticator() {  
				protected PasswordAuthentication getPasswordAuthentication() {  
						return new PasswordAuthentication(USERNAME, PASSWORD);  
				}  
		});   

		MimeMessage message = new MimeMessage(session);  
		message.setFrom(new InternetAddress(SENDER_EMAIL));  
		System.out.println(email);
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));  
		message.setSubject(SUBJECT);  
		message.setText(EMAIL_BODY);  
		  
		// Send message  
		Transport tr = session.getTransport("smtp");
		tr.connect(HOST, USERNAME, PASSWORD);
		message.saveChanges();
		tr.sendMessage(message, message.getAllRecipients());
		tr.close(); 
		System.out.println("message sent successfully....");  
	}
}
