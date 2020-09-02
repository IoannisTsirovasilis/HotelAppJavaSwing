package com.sarantos.kalampoukas.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.JTableAdminRoom;
import com.sarantos.kalampoukas.Models.JTableRoom;
import com.sarantos.kalampoukas.Models.Room;
import com.sarantos.kalampoukas.util.JTableButtonMouseListener;
import com.sarantos.kalampoukas.util.JTableCellRenderer;

public class AdminRooms extends Base {
	private JTable table;
    private JScrollPane scrollPane;
    
	public AdminRooms(Dimension dim, List<Room> rooms) {
		// Window properties
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setTitle("Rooms - Hotel App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setResizable(false);
		getContentPane().setLayout(null);
		
		// Table
		table = new JTable(new JTableAdminRoom(rooms));		
		table.setRowHeight(130);
		JTableCellRenderer cellRenderer = new JTableCellRenderer();
		table.setDefaultRenderer(Object.class, cellRenderer);
			
        table.addMouseListener(new JTableButtonMouseListener(table));
        table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
        scrollPane = new JScrollPane(table);
		scrollPane.setBounds(new Rectangle(0, 50, 885, 430));
		scrollPane.getViewport().add(table);
		getContentPane().add(scrollPane);
				
		// Back button
		JButton backBtn = new JButton("Back");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HotelApp.window.dispose();
				HotelApp.window = new Admin(dim);
			}
		});
		backBtn.setBounds(14, 506, 105, 24);
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(backBtn);
		
		// Log off button
		JButton logOffBtn = new JButton("Log Off");
		logOffBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserController.logOff();
				HotelApp.window.dispose();
				HotelApp.window = new Login(dim);
			}
		});
		logOffBtn.setBounds(767, 506, 105, 24);
		logOffBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(logOffBtn);		
		
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHelp.setBounds(328, 506, 105, 24);
		getContentPane().add(btnHelp);
		
		btnAboutUs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAboutUs.setBounds(445, 507, 105, 24);
		getContentPane().add(btnAboutUs);
		
		// Render
		setVisible(true);
	}
}