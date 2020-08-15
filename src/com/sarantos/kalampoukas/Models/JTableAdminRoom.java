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
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.codec.binary.Base64;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Views.AdminRooms;

public class JTableAdminRoom extends AbstractTableModel {
	   private Object[][] rows;
	   private static final String[] COLUMN_NAMES = new String[] {"Id", "Image", "Description", "Persons", "Status", "Price", "Activate", "Deactivate"};
    private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {JLabel.class, JLabel.class, JLabel.class, JLabel.class, JLabel.class, JTextField.class, JButton.class, JButton.class};
    private int i = 0;
	   
	   public JTableAdminRoom(List<Room> rooms) {
		   rows = new Object[rooms.size()][COLUMN_NAMES.length];
		   for (Room room : rooms) {
			   byte[] btDataFile = Base64.decodeBase64(room.getImageEncoded());
			   BufferedImage image;
			   try {
				   rows[i][0] = new JLabel();
				   ((JLabel) rows[i][0]).setText(Integer.toString(room.getId()));
				   
				   image = ImageIO.read(new ByteArrayInputStream(btDataFile));
				   rows[i][1] = new JLabel();
				   ((JLabel) rows[i][1]).setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				   
				   rows[i][2] = new JLabel();
				   ((JLabel) rows[i][2]).setText(room.getDescription());
				   
				   rows[i][3] = new JLabel();
				   ((JLabel) rows[i][3]).setText(Integer.toString(room.getCapacity()));
				   
				   rows[i][4] = new JLabel();
				   ((JLabel) rows[i][4]).setText(room.getStatus().getName());		
				   
				   rows[i][5] = new JTextField();
				   ((JTextField) rows[i][5]).setText(Double.toString(room.getPrice()));
				   			       
			       rows[i][6] = new JButton("Activate");
			       ((JButton) rows[i][6]).setFont(new Font("Tahoma", Font.PLAIN, 16));
			       if (room.getStatusId() == 1) ((JButton) rows[i][6]).setEnabled(false);
			       ((JButton) rows[i][6]).addActionListener(new ActionListener() {
			    	   
						public void actionPerformed(ActionEvent event) {
							JButton button = (JButton) event.getSource();
							try {
								button.setEnabled(false);
								RoomController rc = new RoomController();
								boolean succeeded = rc.activateRoom(room.getId());
								if (succeeded) {									
									JOptionPane.showMessageDialog(null, "The room has been activated.");
									List<Room> rooms = rc.getRooms();
									Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
									HotelApp.window.dispose();
									HotelApp.window = new AdminRooms(dim, rooms);
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
			       
			       rows[i][7] = new JButton("Deactivate");
			       ((JButton) rows[i][7]).setFont(new Font("Tahoma", Font.PLAIN, 16));
			       if (room.getStatusId() == 2) ((JButton) rows[i][7]).setEnabled(false);
			       ((JButton) rows[i][7]).addActionListener(new ActionListener() {
			    	   
						public void actionPerformed(ActionEvent event) {
							JButton button = (JButton) event.getSource();
							try {
								button.setEnabled(false);
								RoomController rc = new RoomController();
								boolean succeeded = rc.deactivateRoom(room.getId());
								if (succeeded) {									
									JOptionPane.showMessageDialog(null, "The room has been deactivated.");
									List<Room> rooms = rc.getRooms();
									Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
									HotelApp.window.dispose();
									HotelApp.window = new AdminRooms(dim, rooms);
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