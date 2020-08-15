package com.sarantos.kalampoukas.Models;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
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
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Views.Payment;
import com.sarantos.kalampoukas.Views.SearchRoom;
	
public class JTableRoom extends AbstractTableModel {
	   private Object[][] rows;
	   private static final String[] COLUMN_NAMES = new String[] {"Image", "Description", "Persons", "Book"};
       private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {JLabel.class, JLabel.class, JLabel.class,  JButton.class};
       private int i = 0;
	   
	   public JTableRoom(List<Room> rooms) {
		   rows = new Object[rooms.size()][COLUMN_NAMES.length];
		   for (Room room : rooms) {
			   byte[] btDataFile = Base64.decodeBase64(room.getImageEncoded());
			   BufferedImage image;
			   try {
				   image = ImageIO.read(new ByteArrayInputStream(btDataFile));
				   rows[i][0] = new JLabel();
				   ((JLabel) rows[i][0]).setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				   
				   rows[i][1] = new JLabel();
				   ((JLabel) rows[i][1]).setText(room.getDescription());
				   
				   rows[i][2] = new JLabel();
				   ((JLabel) rows[i][2]).setText(Integer.toString(room.getCapacity()));
				   
				   
				   long diffInMillies = Math.abs(UserSession.getInstance().getCheckOut().getTime() - UserSession.getInstance().getCheckIn().getTime());
			       long rentDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
			       
			       rows[i][3] = new JButton((room.getPrice() * rentDays) + " \u20ac");
			       ((JButton) rows[i][3]).setFont(new Font("Tahoma", Font.PLAIN, 16));
			       ((JButton) rows[i][3]).addActionListener(new ActionListener() {
			    	   
						public void actionPerformed(ActionEvent event) {
							JButton button = (JButton) event.getSource();
							try {
								button.setEnabled(false);
								RoomController rc = new RoomController();
								UserSession.getInstance().setTotalPrice(room.getPrice() * rentDays);
								long bookingId = rc.book(room.getId(), room.getCapacity(), UserSession.getInstance().getTotalPrice());
								Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
								if (bookingId > 0) {									
									HotelApp.window.dispose();
									HotelApp.window = new Payment(dim, bookingId);
								} else {
									JOptionPane.showMessageDialog(null, "Something went wrong, please try again later.");
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Exception: Something went wrong, please try again later.");
								e.printStackTrace();
							} finally {
								button.setEnabled(true);
							}
						}	        	
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  	
			   
			   i++;
		   }
	   }
	   
	   @Override public int getColumnCount() {
           return COLUMN_NAMES.length;
       }

       @Override public int getRowCount() {
           return rows.length;
       }

       @Override public String getColumnName(int columnIndex) {
           return COLUMN_NAMES[columnIndex];
       }

       @Override public Class<?> getColumnClass(int columnIndex) {
           return COLUMN_TYPES[columnIndex];
       }
       
       @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
           /*Adding components*/
    	   return rows[rowIndex][columnIndex];
   }   

}