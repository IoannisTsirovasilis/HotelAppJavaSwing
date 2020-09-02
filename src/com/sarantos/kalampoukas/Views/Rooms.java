package com.sarantos.kalampoukas.Views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.JTableRoom;
import com.sarantos.kalampoukas.Models.Room;
import com.sarantos.kalampoukas.util.JButtonRenderer;
import com.sarantos.kalampoukas.util.JLabelRenderer;
import com.sarantos.kalampoukas.util.JTableButtonMouseListener;
import com.sarantos.kalampoukas.util.JTableCellRenderer;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Rooms extends Base {
	private JTable table;
	private JScrollPane scrollPane;

	public Rooms(Dimension dim, List<Room> rooms) {
		// Window properties
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setSize(900, 600);
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setLocationRelativeTo(null);
		setTitle("Available Rooms - Hotel App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);

		// Date Label
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = format.format(UserSession.getInstance().getCheckIn());
		String dateTo = format.format(UserSession.getInstance().getCheckOut());
		String dateRange = dateFrom + " - " + dateTo;
		JLabel lblDateRange = new JLabel(dateRange);
		lblDateRange.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateRange.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateRange.setBounds(325, 13, 250, 24);
		getContentPane().add(lblDateRange);

		// Table
		table = new JTable(new JTableRoom(rooms));
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
				if (UserSession.getInstance().getRoleId() == 1)
					HotelApp.window = new NewBooking(dim);
				else
					HotelApp.window = new SearchRoom(dim);
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

		// Render
		setVisible(true);
	}
}