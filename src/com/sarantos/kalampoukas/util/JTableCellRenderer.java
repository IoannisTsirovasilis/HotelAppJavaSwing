package com.sarantos.kalampoukas.util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

//By default a JTable renders an object as text (i.e. toString() method)
//Custom cell renderers are needed to render the graphical representation of an object
public class JTableCellRenderer extends DefaultTableCellRenderer {	   
    @Override 
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    return (Component) value;
	}
}
