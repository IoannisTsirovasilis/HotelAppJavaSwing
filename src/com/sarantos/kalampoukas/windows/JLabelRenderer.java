package com.sarantos.kalampoukas.windows;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JLabelRenderer implements TableCellRenderer {	   
	   @Override 
	   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		   if(value instanceof Component)
		         return (Component)value;
         return this.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	   }
	}