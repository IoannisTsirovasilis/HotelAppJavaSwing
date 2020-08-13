package com.sarantos.kalampoukas.windows;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

import com.sarantos.kalampoukas.HotelApp;
import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Models.Room;

public class RoomRenderer extends JPanel implements ListCellRenderer<Room> {
	private JLabel lbIcon = new JLabel();
    private JLabel lbDescription = new JLabel();
    private JLabel lbCapacity = new JLabel();
    private JButton bookBtn = new JButton();
    private JTable table = new JTable(1, 1);
    private long rentDays;
 
    public RoomRenderer() {
        setLayout(new BorderLayout());
        
        table.add(lbDescription);
        table.add(lbCapacity);
        table.add(bookBtn);
        add(lbIcon, BorderLayout.WEST);
        add(table, BorderLayout.CENTER);

        long diffInMillies = Math.abs(UserSession.getInstance().getCheckOut().getTime() - UserSession.getInstance().getCheckIn().getTime());
        rentDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends Room> list,
    		Room room, int index, boolean isSelected, boolean cellHasFocus) {
    	byte[] btDataFile = Base64.decodeBase64(room.getImageEncoded());
    	BufferedImage image;
		try {
			image = ImageIO.read(new ByteArrayInputStream(btDataFile));
			lbIcon.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
			lbIcon.setSize(10, 10);
	        lbDescription.setText(room.getDescription());
	        lbCapacity.setText(room.getCapacity() + " Persons");
	        
	        bookBtn.setLabel("Book");
			bookBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) throws NumberFormatException {
					
					try {
						bookBtn.setEnabled(false);
						System.out.println(1);	
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
						bookBtn.setEnabled(true);
					}
				}	        	
			});
			bookBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
			table.add(bookBtn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
 
        return this;
    }
}
