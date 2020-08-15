package com.sarantos.kalampoukas.Models;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.codec.binary.Base64;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.BookingController;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Views.Payment;

public class JTableBooking extends AbstractTableModel {
   private Object[][] rows;
   private static final String[] COLUMN_NAMES = new String[] {"Id", "Email", "Persons", "Check In", "Check Out", 
		   "Total Price", "Cancel"};
   private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {JLabel.class, JLabel.class, JLabel.class, 
	   JLabel.class, JLabel.class, JLabel.class,JButton.class};
   private int i = 0;
	   
   public JTableBooking(List<Booking> bookings) {
	   rows = new Object[bookings.size()][COLUMN_NAMES.length];
	   for (Booking booking: bookings) {
		   rows[i][0] = new JLabel();
		   ((JLabel) rows[i][0]).setText(Integer.toString(booking.getId()));
		   
		   rows[i][1] = new JLabel();
		   ((JLabel) rows[i][1]).setText(booking.getEmail());
		   
		   rows[i][2] = new JLabel();
		   ((JLabel) rows[i][2]).setText(Integer.toString(booking.getPersons()));
		   
		   SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		   String dateFormatted = format.format(booking.getCheckIn());
		   rows[i][3] = new JLabel();
		   ((JLabel) rows[i][3]).setText(dateFormatted);
		   
		   dateFormatted = format.format(booking.getCheckOut());
		   rows[i][4] = new JLabel();
		   ((JLabel) rows[i][4]).setText(dateFormatted);
		   
		   rows[i][5] = new JLabel();
		   ((JLabel) rows[i][5]).setText(Double.toString(booking.getTotalPrice()));
		   
		   rows[i][6] = new JButton("Cancel");
		   ((JButton) rows[i][6]).setFont(new Font("Tahoma", Font.PLAIN, 16));
		   
		   // if booking is pending or successful, then let admin the choice to cancel it
		   if (booking.getStatusId() == 1 || booking.getStatusId() == 2) {
			   ((JButton) rows[i][6]).addActionListener(new ActionListener() {
		    	   
					public void actionPerformed(ActionEvent event) {
						JButton button = (JButton) event.getSource();
						try {
							button.setEnabled(false);
							int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?");
							if(dialogResult == JOptionPane.YES_OPTION){
								BookingController bc = new BookingController();
								boolean succeeded = bc.cancelBooking(booking.getId());
								if (succeeded) {									
									JOptionPane.showMessageDialog(null, "Booking has been cancelled.");
								} else {
									JOptionPane.showMessageDialog(null, "Something went wrong, please try again later.");
									button.setEnabled(true);
								}
							} else {
								button.setEnabled(true);
							}							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Exception: Something went wrong, please try again later.");
							e.printStackTrace();
							button.setEnabled(true);
						}
					}	        	
				});
		   } else {
			   ((JButton) rows[i][6]).setEnabled(false);
		   }  			   
		   i++;
	   }
   }
	   
   @Override public int getColumnCount() {
	    return COLUMN_NAMES.length;
	}
	
	@Override 
	public int getRowCount() {
	    return rows.length;
	}
	
	@Override 
	public String getColumnName(int columnIndex) {
	    return COLUMN_NAMES[columnIndex];
	}
	
	@Override 
	public Class<?> getColumnClass(int columnIndex) {
	    return COLUMN_TYPES[columnIndex];
	}
	
	@Override 
	public Object getValueAt(final int rowIndex, final int columnIndex) {
	    /*Adding components*/
	   return rows[rowIndex][columnIndex];
	}   
}