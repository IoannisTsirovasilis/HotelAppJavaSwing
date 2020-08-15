package com.sarantos.kalampoukas.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sarantos.kalampoukas.Controllers.RoomController;

// By default JTable's behaviour does not support button clicks in its cells
// This class solves this problem by getting the pointer of the mouse the moment it was clicked.
// Checks if the cell is of type JButton and forces this button's click.
// https://www.cordinc.com/blog/2010/01/jbuttons-in-a-jtable.html
public class JTableButtonMouseListener extends MouseAdapter {
	private final JTable table;
		
	public JTableButtonMouseListener(JTable table) {
		this.table = table;
    }

    @Override 
    public void mouseClicked(MouseEvent e) {
    	int column = table.getColumnModel().getColumnIndexAtX(e.getX());
    	int row = e.getY()/table.getRowHeight(); 

    	// True if mouse (on click) hits a cell
    	if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
    		Object value = table.getValueAt(row, column);
    		// if it is a button, trigger its click method
    		if (value instanceof JButton) {
    			((JButton)value).doClick();
    		}
    		
    		// if it is text field then an admin wants to change the price of a room from his panel
    		if (value instanceof JTextField) {
    			String answer = JOptionPane.showInputDialog("Enter a price");
    			while (answer != null) {
    				try {
        				double price = Double.parseDouble(answer);
        				if (price < 0) {
        					throw new NumberFormatException();
        				}
        				RoomController rc = new RoomController();
        				String text = ((JLabel)table.getValueAt(row, 0)).getText();
        				int id = Integer.parseInt(text);
        				rc.setRoomPrice(id, price);
        				((JTextField)value).setText(Double.toString(price));
        				break;
        			} catch (NumberFormatException ex) {
        				System.out.println(ex.toString());
        				JOptionPane.showMessageDialog(null, "Please provide a valid price.");
        			}
    				
    				answer = JOptionPane.showInputDialog("Enter a price");
    			}    			
    		}
    	}
    }
}