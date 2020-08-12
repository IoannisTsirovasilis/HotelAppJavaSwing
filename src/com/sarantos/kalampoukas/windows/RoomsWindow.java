package com.sarantos.kalampoukas.windows;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.Room;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RoomsWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5686584634538209687L;

	public RoomsWindow(Dimension dim, List<Room> rooms) {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel frmToLabel = new JLabel("To");
		frmToLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmToLabel.setBounds(260, 222, 56, 16);
		
		JLabel frmToLabel2 = new JLabel("To");
		frmToLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmToLabel.setBounds(260, 182, 56, 16);
		
		List<JLabel> a = new ArrayList<JLabel>();
		a.add(frmToLabel);
		a.add(frmToLabel2);
		getContentPane().setLayout(null);
		
		
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
		
		Button logOffBtn = new Button("Log Off");
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
		
		JList<Room> listRooms = new JList(rooms.toArray());
		listRooms.setCellRenderer(new RoomRenderer());
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setBounds(12, 40, 860, 440);
		panel.add(new JScrollPane(listRooms),
                BorderLayout.CENTER);
		getContentPane().add(panel);
		
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = format.format(UserSession.getInstance().getCheckIn());
		String dateTo = format.format(UserSession.getInstance().getCheckOut());
		String dateRange = dateFrom + " - " + dateTo;
		JLabel lblDateRange = new JLabel(dateRange);
		lblDateRange.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateRange.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateRange.setBounds(325, 13, 250, 24);
		getContentPane().add(lblDateRange);
		//getContentPane().add(panel);
		setVisible(true);
	}
}
