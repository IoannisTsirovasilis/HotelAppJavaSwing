package com.sarantos.kalampoukas.Views;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Base extends JFrame {
	protected JButton btnHelp;
	protected JButton btnAboutUs;
	
	public Base() {
		btnHelp = new JButton("Help");
		btnAboutUs = new JButton("About Us");
		
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHelp.setBounds(328, 506, 105, 24);
		getContentPane().add(btnHelp);
		
		btnAboutUs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAboutUs.setBounds(445, 507, 105, 24);
		getContentPane().add(btnAboutUs);
		
		btnHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Send us an email at : myhotel@myhotel.gr\n" + 
						"or\n" + 
						"Call us at : +30 210 123 1234");
			}
		});
		btnAboutUs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "MyHotel offers 10 luxuriously appointem rooms and suites with special benefits.\n" + 
						"The hotel is located only 10 minutes from Metro, Monastiraki Station.\n" + 
						"Phone : +30 210 123 1234\n" + 
						"Email : myhotel@myhotel.gr \n" + 
						"Location : Monastiraki 00 Athens, Greece\n" + 
						"Payment Method : We accept Visa and MasterCard");
			}
		});
	}
}
