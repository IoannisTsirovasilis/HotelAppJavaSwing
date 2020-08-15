package com.sarantos.kalampoukas.Views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.sarantos.kalampoukas.DateLabelFormatter;
import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.BookingController;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Controllers.UserController;
import com.sarantos.kalampoukas.Models.Booking;
import com.sarantos.kalampoukas.Models.JTableBooking;
import com.sarantos.kalampoukas.Models.Room;
import com.sarantos.kalampoukas.Models.User;
import com.sarantos.kalampoukas.util.JTableButtonMouseListener;
import com.sarantos.kalampoukas.util.JTableCellRenderer;
import javax.swing.JTextField;

public class NewBooking extends JFrame implements KeyListener {
	JButton searchRoomBtn;
	JDatePickerImpl datePickerFrom;
	JDatePickerImpl datePickerTo;
	JSpinner frmPersonsField;
	Dimension dim;
	private JButton backBtn;
	private JTextField emailField;
	private JLabel lblEmailLabel;
	
	public NewBooking(Dimension dim) {
		this.dim = dim;
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Search Room - Hotel App");
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
		
		frmPersonsField = new JSpinner();
		frmPersonsField.setBounds(347, 259, 227, 32);
		((JSpinner.DefaultEditor)frmPersonsField.getEditor()).getTextField().addKeyListener(new KeyListener(){

            @Override
            public void keyPressed(KeyEvent e) {                    
            }

            @Override
            public void keyReleased(KeyEvent e) {
            	if (e.getKeyCode() == KeyEvent.VK_ENTER)
        			searchRooms(dim);        
            }

            @Override
            public void keyTyped(KeyEvent e) {                    
            }

        });
		getContentPane().add(frmPersonsField);
		
		Date today = new Date();
		UtilDateModel modelFrom = new UtilDateModel();
		modelFrom.setDate(today.getYear() + 1900, today.getMonth(), today.getDate());
		modelFrom.setSelected(true);
		Properties pFrom = new Properties();
		pFrom.put("text.today", "Today");
		pFrom.put("text.month", "Month");
		pFrom.put("text.year", "Year");
		
		UtilDateModel modelTo = new UtilDateModel();
		
		
		// If the user can't find a choice of preference and hits the back button,
		// render the previously selected dates
		// else render today date
		Date check_in = UserSession.getInstance().getCheckIn();
		Date check_out = UserSession.getInstance().getCheckOut();
		if (check_in != null) 
			modelFrom.setDate(check_in.getYear() + 1900, check_in.getMonth(), check_in.getDate());
		else
			modelFrom.setDate(today.getYear() + 1900, today.getMonth(), today.getDate());
		
		if (check_out != null) 
			modelTo.setDate(check_out.getYear() + 1900, check_out.getMonth(), check_out.getDate());
		else
			modelTo.setDate(today.getYear() + 1900, today.getMonth(), today.getDate());
		
		
		modelTo.setSelected(true);
		Properties pTo = new Properties();
		pTo.put("text.today", "Today");
		pTo.put("text.month", "Month");
		pTo.put("text.year", "Year");
		
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, pFrom);		
		datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
		datePickerFrom.setSize(227, 32);
		datePickerFrom.setLocation(347, 169);
		 
		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, pTo);		
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
		datePickerTo.setSize(227, 32);
		datePickerTo.setLocation(347, 214);
		
		getContentPane().add(datePickerFrom);
		getContentPane().add(datePickerTo);
		
		searchRoomBtn = new JButton("Search");
		searchRoomBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) throws NumberFormatException {
				int user_id = getUserIdByEmail(emailField.getText());
				UserSession.getInstance().setUserId(user_id);
				searchRooms(dim);
			}
		});
		searchRoomBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchRoomBtn.setBounds(347, 308, 227, 24);
		getContentPane().add(searchRoomBtn);
		
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
		
		backBtn = new JButton("Back");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backBtn.setEnabled(false);
				BookingController bc = new BookingController();
				HotelApp.window.dispose();
				try {
					HotelApp.window = new Bookings(dim, bc.getBookings());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");
					e1.printStackTrace();
				} finally {
					backBtn.setEnabled(true);
				}
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backBtn.setBounds(14, 507, 105, 24);
		getContentPane().add(backBtn);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailField.setBounds(347, 128, 227, 32);
		getContentPane().add(emailField);
		emailField.setColumns(10);
		
		lblEmailLabel = new JLabel("Email");
		lblEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmailLabel.setBounds(260, 136, 75, 16);
		getContentPane().add(lblEmailLabel);
		
	}
	
	private int getUserIdByEmail(String email) {
		try {
			UserController uc = new UserController();
			User user = uc.getCustomerByEmail(email);
			if (user == null) {
				JOptionPane.showMessageDialog(null, "Customer with email " + email + " does not exist.");	
				return 0;
			}
			
			return user.getId();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");
		}		
		
		return 0;
	}

	private void searchRooms(Dimension dim) {
		try {
			if (UserSession.getInstance().getUserId() == 0) return;
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
			RoomController rc = new RoomController();
			
			try {
				List<Room> rooms = rc.getRooms(from, to, persons);
				HotelApp.window.dispose();
				HotelApp.window = new Rooms(dim, rooms);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Something went wrong, please try again.");		
				e.printStackTrace();
			}			
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Please provide a valid number of persons.");					
		} finally {
			searchRoomBtn.setEnabled(true);
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			searchRooms(dim);
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