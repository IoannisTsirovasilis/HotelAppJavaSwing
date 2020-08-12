package com.sarantos.kalampoukas.windows;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.sarantos.kalampoukas.DateLabelFormatter;
import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Controllers.UserController;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;

public class SearchRoomWindow extends JFrame {
	public SearchRoomWindow(Dimension dim) {
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Find Room - Hotel App");
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		
		JLabel frmFromLabel = new JLabel("From");
		frmFromLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmFromLabel.setBounds(260, 177, 56, 16);
		getContentPane().add(frmFromLabel);
		
		JLabel frmToLabel = new JLabel("To");
		frmToLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmToLabel.setBounds(260, 222, 56, 16);
		getContentPane().add(frmToLabel);
		
		JLabel frmPersonsLabel = new JLabel("Persons");
		frmPersonsLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmPersonsLabel.setBounds(260, 267, 56, 16);
		getContentPane().add(frmPersonsLabel);
		
		JSpinner frmPersonsField = new JSpinner();
		frmPersonsField.setBounds(347, 259, 227, 32);
		getContentPane().add(frmPersonsField);
		
		UtilDateModel modelFrom = new UtilDateModel();
		Properties pFrom = new Properties();
		pFrom.put("text.today", "Today");
		pFrom.put("text.month", "Month");
		pFrom.put("text.year", "Year");
		
		UtilDateModel modelTo = new UtilDateModel();
		Properties pTo = new Properties();
		pTo.put("text.today", "Today");
		pTo.put("text.month", "Month");
		pTo.put("text.year", "Year");
		
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, pFrom);		
		JDatePickerImpl datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
		datePickerFrom.setSize(227, 32);
		datePickerFrom.setLocation(347, 169);
		 
		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, pTo);		
		JDatePickerImpl datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
		datePickerTo.setSize(227, 32);
		datePickerTo.setLocation(347, 214);
		
		getContentPane().add(datePickerFrom);
		getContentPane().add(datePickerTo);
		
		Button searchRoomBtn = new Button("Search");
		searchRoomBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) throws NumberFormatException {
				try {
					searchRoomBtn.setEnabled(false);
					Date from = (Date) datePickerFrom.getModel().getValue();
					Date to = (Date) datePickerTo.getModel().getValue();
					if (from == null || to == null) {
						JOptionPane.showMessageDialog(null, "Please fill both date fields.");
						return;
					}
					
					int persons = (Integer) frmPersonsField.getValue();
					
					if (persons <= 0) {
						throw new NumberFormatException();
					}
					
					if (from.compareTo(to) > 0) {
						JOptionPane.showMessageDialog(null, "Date field 'From' must occur before date field 'To'.");
						return;
					}
					
					// Compare date parts of 'from' and today to check if 'from' comes after today (or from = today). 
					// https://stackoverflow.com/questions/18402698/comparing-dates-in-java-only-years-months-and-days
					Calendar calFrom = Calendar.getInstance();
					Calendar calToday = Calendar.getInstance();
					Date today = new Date();
					calFrom.setTime(from);
					calToday.setTime(today);
					boolean isFromValidDate = calFrom.get(Calendar.YEAR) == calToday.get(Calendar.YEAR) &&
				              calFrom.get(Calendar.DAY_OF_YEAR) >= calToday.get(Calendar.DAY_OF_YEAR);
					
					if (!isFromValidDate) {						
						JOptionPane.showMessageDialog(null, "Date field 'From' cannot be a past date.");
						return;
					}
					
					HotelApp.window.dispose();
					HotelApp.window = new RoomsWindow(dim, RoomController.getRooms(from, to, persons));
					
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please provide a valid number of persons.");					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					searchRoomBtn.setEnabled(true);
				}
				
			}
		});
		searchRoomBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchRoomBtn.setBounds(347, 308, 227, 24);
		getContentPane().add(searchRoomBtn);
		
		Button logOffBtn = new Button("Log Off");
		logOffBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserController.logOff();
				HotelApp.window.dispose();
				HotelApp.window = new LoginWindow(dim);
			}
		});
		logOffBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		logOffBtn.setBounds(767, 506, 105, 24);
		getContentPane().add(logOffBtn);
	}
}
