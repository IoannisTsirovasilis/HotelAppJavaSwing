package com.sarantos.kalampoukas.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

// By default a JTable renders an object as text (i.e. toString() method)
// Custom cell renderers are needed to render the graphical representation of an object
public class JLabelRenderer extends DefaultTableCellRenderer {	   
    @Override 
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    return (JLabel) value;
	}
    
    @Override
    public void setHorizontalAlignment(int alignment) {
    	super.setHorizontalAlignment(alignment);
    }
}