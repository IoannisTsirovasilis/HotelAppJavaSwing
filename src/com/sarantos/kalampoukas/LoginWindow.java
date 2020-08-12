package com.sarantos.kalampoukas;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {
	private JTextField frmLoginEmailField;
	private JLabel frmLoginPasswordLabel;
	private JPasswordField frmLoginPasswordField;
	
	public LoginWindow(Dimension dim) {
		initialize(dim);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Dimension dim) {
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setTitle("Login - Hotel App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		frmLoginEmailField = new JTextField();
		frmLoginEmailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmLoginEmailField.setBounds(350, 224, 227, 32);
		getContentPane().add(frmLoginEmailField);
		frmLoginEmailField.setColumns(10);
		
		JLabel frmLoginEmailLabel = new JLabel("Email");
		frmLoginEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmLoginEmailLabel.setBounds(255, 232, 56, 16);
		getContentPane().add(frmLoginEmailLabel);
		
		frmLoginPasswordLabel = new JLabel("Password");
		frmLoginPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmLoginPasswordLabel.setBounds(255, 277, 83, 16);
		getContentPane().add(frmLoginPasswordLabel);
		
		Button loginBtn = new Button("Login");
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Disable login button to prevent multiple form submissions
				loginBtn.setEnabled(false);
				String email = frmLoginEmailField.getText();
				String password = frmLoginPasswordField.getText();
				if (false) { 
					JOptionPane.showMessageDialog(null, "Wrong credentials");
					loginBtn.setEnabled(true);
				}
				else {
					HotelApp.window.dispose();
					HotelApp.window = new SearchRoomWindow(dim);
				}
//				try {
//					boolean succeeded = UserController.Login(email, password);
//					if (succeeded) {
//						
//					}
//				} catch (ClassNotFoundException | SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
		loginBtn.setBounds(350, 324, 91, 24);
		getContentPane().add(loginBtn);
		
		Button exitBtn = new Button("Exit");
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		exitBtn.setBounds(486, 324, 91, 24);
		getContentPane().add(exitBtn);
		
		frmLoginPasswordField = new JPasswordField();
		frmLoginPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmLoginPasswordField.setBounds(350, 269, 227, 32);
		getContentPane().add(frmLoginPasswordField);
	}
}
