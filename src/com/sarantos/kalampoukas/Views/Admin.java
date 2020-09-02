package com.sarantos.kalampoukas.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.Controllers.BookingController;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.Booking;
import com.sarantos.kalampoukas.Models.Room;

import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Admin extends Base {
	Dimension dim;
	
	public Admin(Dimension dim) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Admin - Hotel App");
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setResizable(false);
		getContentPane().setLayout(null);
		
		JButton btnBookings = new JButton("Bookings");
		btnBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBookings.setEnabled(false);				
				try {					
					BookingController bc = new BookingController();
					List<Booking> bookings = bc.getBookings();
					HotelApp.window.dispose();
					HotelApp.window = new Bookings(dim, bookings);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");
					e.printStackTrace();
				} finally {
					btnBookings.setEnabled(true);
				}
			}
		});
		btnBookings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBookings.setBounds(380, 122, 138, 25);
		getContentPane().add(btnBookings);
		
		JButton btnRooms = new JButton("Rooms");
		btnRooms.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnRooms.setEnabled(false);				
				try {
					RoomController rc = new RoomController();
					HotelApp.window.dispose();
					List<Room> rooms = rc.getRooms();
					HotelApp.window = new AdminRooms(dim, rooms);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");
					e.printStackTrace();
				} finally {
					btnRooms.setEnabled(true);
				}
			}
		});
		btnRooms.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRooms.setBounds(380, 167, 138, 25);
		getContentPane().add(btnRooms);
		
		JButton logOffBtn = new JButton("Log Off");
		logOffBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserController.logOff();
				HotelApp.window.dispose();
				HotelApp.window = new Login(dim);
			}
		});
		logOffBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		logOffBtn.setBounds(767, 506, 105, 24);
		getContentPane().add(logOffBtn);
		
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHelp.setBounds(328, 506, 105, 24);
		getContentPane().add(btnHelp);
		
		btnAboutUs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAboutUs.setBounds(445, 507, 105, 24);
		getContentPane().add(btnAboutUs);
		
		setVisible(true);
	}
}