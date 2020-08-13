package com.sarantos.kalampoukas.windows;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RoomsWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5686584634538209687L;
	private JTable table;
    private JScrollPane scrollPane;
	public RoomsWindow(Dimension dim, List<Room> rooms) {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		getContentPane().setLayout(new BorderLayout());
		
		table = new JTable(new JTableRoom(rooms));
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		TableCellRenderer buttonRenderer = new JTableRoomRenderer();
        table.getColumn("Book").setCellRenderer(buttonRenderer);
		add(scrollPane);
		setVisible(true);
		
		Button backBtn = new Button("Back");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HotelApp.window.dispose();
				HotelApp.window = new SearchRoomWindow(dim);
			}
		});
		backBtn.setBounds(14, 506, 105, 24);
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(backBtn);
		
		JButton logOffBtn = new JButton("Log Off");
		logOffBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserController.logOff();
				HotelApp.window.dispose();
				HotelApp.window = new LoginWindow(dim);
			}
		});
		logOffBtn.setBounds(767, 506, 105, 24);
		logOffBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(logOffBtn);
		
		
//		JList<Room> listRooms = new JList(rooms.toArray());
//		listRooms.setCellRenderer(new RoomRenderer());
//		JPanel panel = new JPanel(new BorderLayout());
//		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
//		panel.setBounds(12, 40, 860, 440);
//		panel.add(new JScrollPane(listRooms),
//                BorderLayout.CENTER);
//		getContentPane().add(panel);
		
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = format.format(UserSession.getInstance().getCheckIn());
		String dateTo = format.format(UserSession.getInstance().getCheckOut());
		String dateRange = dateFrom + " - " + dateTo;
		JLabel lblDateRange = new JLabel(dateRange);
		lblDateRange.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateRange.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateRange.setBounds(325, 13, 250, 24);
		getContentPane().add(lblDateRange);
		setVisible(true);
	}
}