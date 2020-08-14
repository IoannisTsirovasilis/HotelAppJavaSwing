package com.sarantos.kalampoukas.windows;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.PaymentController;

import javax.swing.JButton;

public class PaymentWindow extends JFrame {
	Dimension dim;
	
	public PaymentWindow(Dimension dim, long bookingId) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);		
		
		setTitle("Payment - Hotel App");
		setFont(new Font("Tahoma", Font.PLAIN, 16));
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
					pc.payBooking(bookingId);
					JOptionPane.showMessageDialog(null, "Successful payment!");
					HotelApp.window.dispose();
					HotelApp.window = new SearchRoomWindow(dim);
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");
					e1.printStackTrace();
				}
			}
		});
		payBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payBtn.setBounds(366, 309, 227, 24);
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
					HotelApp.window = new SearchRoomWindow(dim);
				}
			}
		});
		cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancelBtn.setBounds(366, 346, 227, 24);
		getContentPane().add(cancelBtn);
		
		setVisible(true);
	}
}
