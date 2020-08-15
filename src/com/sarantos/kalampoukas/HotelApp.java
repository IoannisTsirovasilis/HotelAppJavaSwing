package com.sarantos.kalampoukas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.Dimension;

import javax.swing.JTextField;

import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Views.Login;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JPasswordField;

public class HotelApp {	
	private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static JFrame window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Login(dim);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
