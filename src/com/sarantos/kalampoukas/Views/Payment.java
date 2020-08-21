package com.sarantos.kalampoukas.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.BookingController;
import com.sarantos.kalampoukas.Controllers.PaymentController;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.Booking;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class Payment extends JFrame {
	Dimension dim;
	
	public Payment(Dimension dim, long bookingId) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);		
		
		setTitle("Payment - Hotel App");
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setResizable(false);
		getContentPane().setLayout(null);
		
		PaymentController pc = new PaymentController();
		
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        try {
					pc.cancelBooking(bookingId);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		
		// Labels
		JLabel frmCardNumberLabel = new JLabel("Card Number");
		frmCardNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmCardNumberLabel.setBounds(246, 177, 108, 16);
		getContentPane().add(frmCardNumberLabel);
		
		JLabel frmCardHolderNameLabel = new JLabel("Holder Name");
		frmCardHolderNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmCardHolderNameLabel.setBounds(246, 222, 108, 16);
		getContentPane().add(frmCardHolderNameLabel);
		
		JLabel frmCVVLabel = new JLabel("CVV");
		frmCVVLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmCVVLabel.setBounds(246, 267, 93, 16);
		getContentPane().add(frmCVVLabel);
		
		
		// Text Fields
		JTextField frmCardNumberField = new JTextField();
		frmCardNumberField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmCardNumberField.setBounds(366, 169, 227, 32);
		getContentPane().add(frmCardNumberField);
		frmCardNumberField.setColumns(10);
		
		JTextField frmCardHolderNameField = new JTextField();
		frmCardHolderNameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmCardHolderNameField.setBounds(366, 214, 227, 32);
		getContentPane().add(frmCardHolderNameField);
		frmCardHolderNameField.setColumns(10);
		
		JTextField frmCVVField = new JTextField();
		frmCVVField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmCVVField.setBounds(366, 259, 227, 32);
		getContentPane().add(frmCVVField);
		frmCVVField.setColumns(10);
		
		// Pay Button
		JButton payBtn = new JButton("Pay " + UserSession.getInstance().getTotalPrice() + " \u20ac");
		payBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) throws NumberFormatException {
				try {
					String cardNumber = frmCardNumberField.getText();
					String holderName = frmCardHolderNameField.getText();
					String cvv = frmCVVField.getText();
					
					if (cardNumber == null || cardNumber.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Card Number field is required.");
						return;
					}
					
					if (holderName == null || holderName.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Holder Name field is required.");
						return;
					}
					
					if (cvv == null || cvv.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "CVV field is required.");
						return;
					}
					
					String cardNumberRegex = "^(\\d){16}$";
					 
					Pattern pattern = Pattern.compile(cardNumberRegex);
					Matcher matcher = pattern.matcher(cardNumber.trim().replaceAll("\\s+", ""));
					
					if (!matcher.matches()) {
						JOptionPane.showMessageDialog(null, "Card Number field is not valid.");
						return;
					}
					
					String cvvRegex = "^(\\d){3}$";
					 
					pattern = Pattern.compile(cvvRegex);
					matcher = pattern.matcher(cvv.trim().replaceAll("\\s+", ""));
					
					if (!matcher.matches()) {
						JOptionPane.showMessageDialog(null, "CVV field is not valid.");
						return;
					}
					
					pc.payBooking(bookingId);
					JOptionPane.showMessageDialog(null, "Successful payment!");
					HotelApp.window.dispose();
					if (UserSession.getInstance().getRoleId() == 1) {
						
						try {
							BookingController bc = new BookingController();
							List<Booking> bookings;
							bookings = bc.getBookings();
							HotelApp.window = new Bookings(dim, bookings); 
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Payment was successful, but something went wrong afterwards. Please relogin");
							UserController.logOff();
							HotelApp.window = new Login(dim); 
						}						
					}
					else {
						HotelApp.window = new SearchRoom(dim);
					}						
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");
					e1.printStackTrace();
				}
			}
		});
		payBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payBtn.setBounds(366, 347, 227, 24);
		getContentPane().add(payBtn);
		
		// Cancel Button
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					pc.cancelBooking(bookingId);
				} catch (ClassNotFoundException | SQLException e1) {
					System.out.println(e1.toString());
					e1.printStackTrace();
				} finally {
					JOptionPane.showMessageDialog(null, "You have cancelled your booking.");
					HotelApp.window.dispose();
					if (UserSession.getInstance().getRoleId() == 1) HotelApp.window = new NewBooking(dim); 
					else HotelApp.window = new SearchRoom(dim);
				}
			}
		});
		cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancelBtn.setBounds(366, 384, 227, 24);
		getContentPane().add(cancelBtn);
		
		JComboBox<Integer> monthDropDown = new JComboBox<Integer>();
		for (int month = 1; month <= 12; month++) monthDropDown.addItem(month);
		monthDropDown.setBounds(366, 304, 60, 22);
		getContentPane().add(monthDropDown);
		
		JComboBox<Integer> yearDropDown = new JComboBox<Integer>();
		for (int year = Calendar.getInstance().get(Calendar.YEAR); year <= Calendar.getInstance().get(Calendar.YEAR) + 5; year++)
			yearDropDown.addItem(year);
		yearDropDown.setBounds(463, 304, 100, 22);
		getContentPane().add(yearDropDown);
		
		setVisible(true);
	}
}
