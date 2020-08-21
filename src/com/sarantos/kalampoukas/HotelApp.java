package com.sarantos.kalampoukas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dimension;
import com.sarantos.kalampoukas.Views.Login;
import java.awt.Toolkit;

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
