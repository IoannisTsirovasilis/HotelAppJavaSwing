package com.sarantos.kalampoukas.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.User;
import javax.swing.JPasswordField;

public class Register extends JFrame implements KeyListener {
	Dimension dim;
	private JTextField emailField;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField mobileField;
	private JButton registerBtn;
	
	private static final String VALIDATION_MESSAGE = "%s field is required.";
	private JPasswordField passwordField;
	private JPasswordField repeatPasswordField;
	
	public Register(Dimension dim) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);		
		
		setTitle("Register - Hotel App");
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel emailLbl = new JLabel("Email");
		emailLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailLbl.setBounds(255, 180, 56, 16);
		getContentPane().add(emailLbl);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailField.setColumns(10);
		emailField.setBounds(394, 172, 227, 32);
		getContentPane().add(emailField);
		
		JLabel nameLbl = new JLabel("Name");
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLbl.setBounds(255, 225, 56, 16);
		getContentPane().add(nameLbl);
		
		JLabel surnameLbl = new JLabel("Surname");
		surnameLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		surnameLbl.setBounds(255, 270, 76, 16);
		getContentPane().add(surnameLbl);
		
		JLabel mobileLbl = new JLabel("Mobile");
		mobileLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		mobileLbl.setBounds(255, 315, 56, 16);
		getContentPane().add(mobileLbl);
		
		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordLbl.setBounds(255, 360, 76, 16);
		getContentPane().add(passwordLbl);
		
		JLabel repeatPasswordLbl = new JLabel("Repeat Password");
		repeatPasswordLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		repeatPasswordLbl.setBounds(255, 405, 128, 16);
		getContentPane().add(repeatPasswordLbl);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameField.setColumns(10);
		nameField.setBounds(394, 217, 227, 32);
		getContentPane().add(nameField);
		
		surnameField = new JTextField();
		surnameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		surnameField.setColumns(10);
		surnameField.setBounds(394, 262, 227, 32);
		getContentPane().add(surnameField);
		
		mobileField = new JTextField();
		mobileField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		mobileField.setColumns(10);
		mobileField.setBounds(394, 307, 227, 32);
		getContentPane().add(mobileField);
		
		registerBtn = new JButton("Register");
		registerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				register();
			}
		});
		registerBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		registerBtn.setBounds(394, 442, 227, 24);
		getContentPane().add(registerBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				HotelApp.window.dispose();
				HotelApp.window = new Login(dim);
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backBtn.setBounds(767, 506, 105, 24);
		getContentPane().add(backBtn);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(394, 352, 227, 32);
		getContentPane().add(passwordField);
		
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		repeatPasswordField.setBounds(394, 397, 227, 32);
		getContentPane().add(repeatPasswordField);
		
		emailField.addKeyListener(this);
		nameField.addKeyListener(this);
		surnameField.addKeyListener(this);
		mobileField.addKeyListener(this);
		passwordField.addKeyListener(this);
		repeatPasswordField.addKeyListener(this);
		
		setVisible(true);
	}
	
	private void register() {
		String email = "";
		try {
			registerBtn.setEnabled(false);
			email = emailField.getText();
			String name = nameField.getText();
			String surname = surnameField.getText();
			String mobile = mobileField.getText();
			String password = passwordField.getText();
			String repeatPassword = repeatPasswordField.getText();
			
			
			if (email == null || email.trim().equals("")) {
				JOptionPane.showMessageDialog(null, String.format(VALIDATION_MESSAGE, "Email"));
				return;
			}
			
			String regex = "^(.+)@(.+)[\\.]{1}(.+)$";
			 
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(email);
			
			if (!matcher.matches()) {
				JOptionPane.showMessageDialog(null, "Invalid email format");
				return;
			}
			
			if (name == null || name.trim().equals("")) {
				JOptionPane.showMessageDialog(null, String.format(VALIDATION_MESSAGE, "Name"));
				return;
			}
			if (surname == null || surname.trim().equals("")) {
				JOptionPane.showMessageDialog(null, String.format(VALIDATION_MESSAGE, "Surname"));
				return;
			}
			if (mobile == null || mobile.trim().equals("")) {
				JOptionPane.showMessageDialog(null, String.format(VALIDATION_MESSAGE, "Mobile"));
				return;
			}
			if (password == null || password.trim().equals("")) {
				JOptionPane.showMessageDialog(null, String.format(VALIDATION_MESSAGE, "Password"));
				return;
			}
			
			if (password.length() < 4) {
				JOptionPane.showMessageDialog(null, "Password must have at least 4 characters");
				return;
			}
			
			if (!password.equals(repeatPassword)) {
				JOptionPane.showMessageDialog(null, "Passwords do not match");
				return;
			}
			
			UserController uc = new UserController();
			User user = new User(name.trim(), surname.trim(), email.trim(), mobile.trim(), 2, password.trim());
			uc.register(user, "Customer");
			JOptionPane.showMessageDialog(null, "Registration completed!");		
			HotelApp.window.dispose();
			if (UserSession.getInstance() == null) {
				HotelApp.window = new Login(dim);
			} else {
				HotelApp.window = new NewBooking(dim);
			}				
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "User with email: " + email + " already exists.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Something went wrong please try again later");
		} finally {
			registerBtn.setEnabled(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			register();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

