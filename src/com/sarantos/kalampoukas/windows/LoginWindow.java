package com.sarantos.kalampoukas.windows;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.User;

public class LoginWindow extends JFrame implements KeyListener {
	private JTextField frmLoginEmailField;
	private JLabel frmLoginPasswordLabel;
	private JPasswordField frmLoginPasswordField;
	private JButton loginBtn;
	private Dimension dim;
	
	public LoginWindow(Dimension dim) {
		initialize(dim);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Dimension dim) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		loginBtn = new JButton("Login");
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login(dim);
			}
		});
		loginBtn.setBounds(350, 324, 91, 24);
		getContentPane().add(loginBtn);
		
		JButton exitBtn = new JButton("Exit");
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
		frmLoginEmailField.addKeyListener(this);
		frmLoginPasswordField.addKeyListener(this);
		setVisible(true);
	}
	
	private void login(Dimension dim) {
		try {
			// Disable login button to prevent multiple form submissions
			loginBtn.setEnabled(false);
			String email = frmLoginEmailField.getText();
			String password = frmLoginPasswordField.getText();
			UserController uc = new UserController();
			User user = uc.login(email, password);
			if (user != null) {
				UserSession.getInstance(user.getId(), user.getEmail(), user.getName(), user.getSurname());
				HotelApp.window.dispose();
				HotelApp.window = new SearchRoomWindow(dim);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong credentials");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			loginBtn.setEnabled(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			login(dim);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
