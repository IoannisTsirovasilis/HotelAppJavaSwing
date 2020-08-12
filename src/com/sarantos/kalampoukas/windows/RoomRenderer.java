package com.sarantos.kalampoukas.windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.ListCellRenderer;

import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Controllers.RoomController;
import com.sarantos.kalampoukas.Models.Room;

public class RoomRenderer extends JPanel implements ListCellRenderer<Room> {
	private JLabel lbIcon = new JLabel();
	private JLabel lbDateRange = new JLabel();
    private JLabel lbDescription = new JLabel();
    private JLabel lbCapacity = new JLabel();
    private JButton bookBtn = new JButton();
    private long rentDays;
 
    public RoomRenderer() {
        setLayout(new BorderLayout());
 
        JPanel panelText = new JPanel();
        panelText.add(lbDescription);
        panelText.add(lbCapacity);
       
        add(lbIcon, BorderLayout.WEST);
        add(panelText, BorderLayout.CENTER);
        add(bookBtn, BorderLayout.EAST);
        
        long diffInMillies = Math.abs(UserSession.getInstance().getCheckOut().getTime() - UserSession.getInstance().getCheckIn().getTime());
        rentDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends Room> list,
    		Room room, int index, boolean isSelected, boolean cellHasFocus) {
    	String s = "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";
    	byte[] btDataFile = Base64.decodeBase64(s);
    	BufferedImage image;
		try {
			image = ImageIO.read(new ByteArrayInputStream(btDataFile));
			lbIcon.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
			lbIcon.setSize(10, 10);
	        lbDescription.setText(room.getDescription());
	        lbCapacity.setText(room.getCapacity() + " Persons");
	        bookBtn.setText("Book " + (room.getPrice() * rentDays));
	        bookBtn.addMouseListener(new MouseAdapter() {
	        	@Override
				public void mouseClicked(MouseEvent e) throws NumberFormatException {
	        		bookBtn.setEnabled(false);
					//RoomController.book(room.getId());
				}	        	
	        });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
 
        return this;
    }
}
