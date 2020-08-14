package com.sarantos.kalampoukas.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;

// By default JTable's behaviour does not support button clicks in its cells
// This class solves this problem by getting the pointer of the mouse the moment it was clicked.
// Checks if the cell is of type JButton and forces this button's click.
// https://www.cordinc.com/blog/2010/01/jbuttons-in-a-jtable.html
public class JTableButtonMouseListener extends MouseAdapter {
	private final JTable table;
		
	public JTableButtonMouseListener(JTable table) {
		this.table = table;
    }

    @Override public void mouseClicked(MouseEvent e) {
    	int column = table.getColumnModel().getColumnIndexAtX(e.getX());
    	int row = e.getY()/table.getRowHeight(); 

    	if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
    		Object value = table.getValueAt(row, column);
    		if (value instanceof JButton) {
    			((JButton)value).doClick();
    		}
    	}
    }
}