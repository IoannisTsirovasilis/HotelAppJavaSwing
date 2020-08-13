package com.sarantos.kalampoukas.Models;

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
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.codec.binary.Base64;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.windows.LoginWindow;
import com.sarantos.kalampoukas.windows.SearchRoomWindow;

public class JTableRoom extends AbstractTableModel {
	   private Object[][] rows;
	   private static final String[] COLUMN_NAMES = new String[] {"Image", "Description", "Persons", "Book"};
       private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {JLabel.class, String.class, Integer.class,  JButton.class};
       private int i = 0;
	   
	   public JTableRoom(List<Room> rooms) {
		   System.out.println("JTableRoom constructor...");
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
				   ((JLabel) rows[i][2]).setText(room.getCapacity() + " Persons");
				   
				   
				   long diffInMillies = Math.abs(UserSession.getInstance().getCheckOut().getTime() - UserSession.getInstance().getCheckIn().getTime());
			       long rentDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
			       rows[i][3] = new JButton("Book " + (room.getPrice() * rentDays));
			       ((JButton) rows[i][3]).setFont(new Font("Tahoma", Font.PLAIN, 16));
			       ((JButton) rows[i][3]).addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) throws NumberFormatException {
							
							try {
								((JButton) rows[i][3]).setEnabled(false);
								boolean succeeded = RoomController.book(room.getId(), room.getCapacity(), room.getPrice() * rentDays);
								Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
								if (succeeded) {
									
									HotelApp.window.dispose();
									HotelApp.window = new SearchRoomWindow(dim);
								} else {
									HotelApp.window.dispose();
									HotelApp.window = new LoginWindow(dim);
								}
							} catch (ClassNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} finally {
								((JButton) rows[i][3]).setEnabled(true);
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
       switch (columnIndex) {
           case 0: return rows[rowIndex][columnIndex];
           case 1: return rows[rowIndex][columnIndex];
           case 2: // fall through
          /*Adding button and creating click listener*/
           case 3: final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                   button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) throws NumberFormatException {
							
							try {
								((JButton) rows[0][3]).setEnabled(false);
								System.out.println(1);	
								boolean succeeded = true;// RoomController.book(room.getId(), room.getCapacity(), room.getPrice() * rentDays);
								Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
								if (succeeded) {
									
									HotelApp.window.dispose();
									HotelApp.window = new SearchRoomWindow(dim);
								} else {
									HotelApp.window.dispose();
									HotelApp.window = new LoginWindow(dim);
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} finally {
								((JButton) rows[0][3]).setEnabled(true);
							}
						}	        	
					});
                   return button;
           default: return "Error";
       }
   }   

}