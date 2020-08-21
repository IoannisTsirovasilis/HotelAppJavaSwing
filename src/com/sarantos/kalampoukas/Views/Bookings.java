package com.sarantos.kalampoukas.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.Booking;
import com.sarantos.kalampoukas.Models.JTableBooking;
import com.sarantos.kalampoukas.util.JTableButtonMouseListener;
import com.sarantos.kalampoukas.util.JTableCellRenderer;

public class Bookings extends JFrame {
	Dimension dim;
	
	public Bookings(Dimension dim, List<Booking> bookings) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bookings - Hotel App");
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setResizable(false);
		getContentPane().setLayout(null);
		
		// Table
		JTable table = new JTable(new JTableBooking(bookings));		
		table.setRowHeight(130);
		JTableCellRenderer cellRenderer = new JTableCellRenderer();
		table.setDefaultRenderer(Object.class, cellRenderer);
			
        table.addMouseListener(new JTableButtonMouseListener(table));
        table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(new Rectangle(0, 50, 885, 430));
		scrollPane.setViewportView(table);
		getContentPane().add(scrollPane);
		
		JButton backBtn = new JButton("Back");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HotelApp.window.dispose();
				HotelApp.window = new Admin(dim);
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backBtn.setBounds(14, 506, 105, 24);
		getContentPane().add(backBtn);
		
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
		logOffBtn.setBounds(767, 516, 105, 24);
		getContentPane().add(logOffBtn);
		
		JButton btnNewBooking = new JButton("New Booking");
		btnNewBooking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HotelApp.window.dispose();
				HotelApp.window = new NewBooking(dim);
			}
		});
		btnNewBooking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewBooking.setBounds(14, 13, 147, 24);
		getContentPane().add(btnNewBooking);
		
		setVisible(true);
	}
}
