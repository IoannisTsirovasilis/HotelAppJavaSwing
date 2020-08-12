package com.sarantos.kalampoukas;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.sarantos.kalampoukas.Controllers.RoomController;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;

public class RoomsWindow extends JFrame {
	
	public RoomsWindow(Dimension dim, JList rooms) {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setSize(900, 600);
		setLocation(dim.width/2-getSize().width/2,
				dim.height/2-getSize().height/2);					
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JLabel frmToLabel = new JLabel("To");
		frmToLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmToLabel.setBounds(260, 222, 56, 16);
		
		JLabel frmToLabel2 = new JLabel("To");
		frmToLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmToLabel.setBounds(260, 182, 56, 16);
		
		List<JLabel> a = new ArrayList<JLabel>();
		a.add(frmToLabel);
		a.add(frmToLabel2);
		getContentPane().setLayout(new MigLayout("", "[104px,grow][104px][][][][][][][][][][][][][][][][][][][][]", "[27px,grow][][][][][][][][][][][][][][][][][][]"));
		
				
		Button backBtn = new Button("Back");
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(backBtn, "cell 0 17,growx,aligny top");
		
		Button logOffBtn = new Button("Log Off");
		logOffBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(logOffBtn, "cell 21 18,growx,aligny top");
		setVisible(true);
	}
}
