/*
    Copyright 2005 VTT Biotechnology

    This file is part of MZmine.

    MZmine is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    MZmine is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with MZmine; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package net.sf.mzmine.userinterface;
import net.sf.mzmine.alignmentresultmethods.*;
import net.sf.mzmine.alignmentresultvisualizers.*;
import net.sf.mzmine.datastructures.*;
import net.sf.mzmine.distributionframework.*;
import net.sf.mzmine.miscellaneous.*;
import net.sf.mzmine.peaklistmethods.*;
import net.sf.mzmine.rawdatamethods.*;
import net.sf.mzmine.rawdatavisualizers.*;
import net.sf.mzmine.userinterface.*;


import java.util.*;
import java.text.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;


public class SelectClassLabelsDialog extends ModalJInternalFrame implements java.awt.event.ActionListener {

	private String[] classValues = { "", "1", "2", "3", "4", "5", "6", "7", "8"};

	// These are used to store information from caller
	private MainWindow mainWin;
	private String title;
	private String labelingQuestion;
	private String[] additionalQuestions;
	private Double[] defaultValues;
	private NumberFormat[] formats;




	// Number of additional questions given by caller
	private int numOfAdditionalQuestions;

	// Number formatting used in additional questions
	private java.text.NumberFormat decimalNumberFormat;

	// Items
	Object[] items;


	// Use-selected class numbers for each item
	int[] itemsClasses;


	// Exit code (1=OK, -1=Cancel)
	private int exitCode = -1;


	/**
	 * Constructor: creates new form SelectOneGroupDialog
	 */
	public SelectClassLabelsDialog(MainWindow _mainWin, String _title, String _labelingQuestion, Object[] _items, String[] _additionalQuestions, Double[] _defaultValues, NumberFormat[] _formats) {
		super();

		additionalQuestions = _additionalQuestions;
		formats = _formats;

		// Store info from caller
		mainWin = _mainWin;
		title = _title;
		labelingQuestion = _labelingQuestion;
		items = _items;

		additionalQuestions = _additionalQuestions;
		defaultValues = _defaultValues;
		if (additionalQuestions == null) {
			numOfAdditionalQuestions = 0;
		} else {
			numOfAdditionalQuestions = additionalQuestions.length;
		}

		// Setup number format for answers to addditional questions
		decimalNumberFormat = java.text.NumberFormat.getNumberInstance();
		decimalNumberFormat.setMinimumFractionDigits(1);

		// Assign all items to class '0' (unassigned)
		String[] defaultClassSelection = new String[items.length];
		for (int ind=0; ind<items.length; ind++) { defaultClassSelection[ind] = classValues[0]; }

		// Build the form
		initComponents();

		mdlClasses = new MyTableModel(items, defaultClassSelection);
		mdlClasses.setTable(tblSamples);
		tblSamples.setModel(mdlClasses);
		tblSamples.setRowSelectionAllowed(true);
		tblSamples.setColumnSelectionAllowed(false);

/*
		TableColumn col = tblSamples.getColumnModel().getColumn(1);
		col.setCellEditor(new MyComboBoxEditor(classValues));
*/
	}


	/**
	 * Implementation of ActionListener interface
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		Object src = e.getSource();

		// OK button
		if (src == btnOK) {

			// Store item classes
			//Vector<String> classValuesV = new Vector<String>();
			//for (String cv : classValues) { classValuesV.add(cv); }
			itemsClasses = new int[items.length];
			for (int ind=0; ind<items.length; ind++) {
				try {
					itemsClasses[ind] = Integer.parseInt((String)(mdlClasses.getValueAt(ind,1)));
				} catch (Exception exe) {
					itemsClasses[ind] = 0;
				}
				//itemsClasses[ind] = classValuesV.indexOf(mdlClasses.getValueAt(ind,1));
			}

			// Set exit code
			exitCode = 1;

			// Hide form
			disposeModal();
/*
			dispose();
			mainWin.getDesktop().remove(this);
*/
		}

		// Cancel button
		if (src == btnCancel) {

			// Set exit code
			exitCode = -1;

			// Hide form
			disposeModal();
/*
			dispose();
			mainWin.getDesktop().remove(this);
*/

		}

	}

	public int[] getClasses() {
		return itemsClasses;
	}



	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents

		// Construct components

		pnlAdditionalQuestions = new javax.swing.JPanel();
		if (additionalQuestions != null) {
			pnlSingleQuestion = new javax.swing.JPanel[additionalQuestions.length];
			lblSingleQuestion = new javax.swing.JLabel[additionalQuestions.length];
			txtSingleQuestion = new javax.swing.JFormattedTextField[additionalQuestions.length];
		} else {
			pnlSingleQuestion = null;
			lblSingleQuestion = null;
			txtSingleQuestion = null;
		}
		for (int ind=0; ind<numOfAdditionalQuestions; ind++) {
			pnlSingleQuestion[ind] = new javax.swing.JPanel();
			lblSingleQuestion[ind] = new javax.swing.JLabel();
			txtSingleQuestion[ind] = new javax.swing.JFormattedTextField(formats[ind]);
		}

		// This one should be removed/modified
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		// Place components to frame



		// - Middle panel (scroll & table)
		pnlMiddle = new javax.swing.JPanel();
		pnlMiddle.setLayout(new java.awt.BorderLayout());

		tblSamples = new javax.swing.JTable();
		scrSamples = new javax.swing.JScrollPane(tblSamples);
		scrSamples.setPreferredSize(new java.awt.Dimension(350,200));

		pnlMiddle.add(scrSamples, java.awt.BorderLayout.CENTER);




		// - Bottom panel (buttons)
		pnlBottom = new javax.swing.JPanel();
		pnlBottom.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
		pnlButtons = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();


		pnlBottom.setLayout(new java.awt.BorderLayout());

		btnOK.setText("OK");
		btnCancel.setText("Cancel");
		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);
		pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
		pnlButtons.add(btnOK);
		pnlButtons.add(btnCancel);

		pnlBottom.add(pnlButtons, java.awt.BorderLayout.SOUTH);

		//	* additional questions

		if (additionalQuestions != null) {
			pnlAdditionalQuestions.setLayout(new java.awt.GridLayout(additionalQuestions.length, 2));

			for (int ind=0; ind<numOfAdditionalQuestions; ind++) {
				lblSingleQuestion[ind].setText(additionalQuestions[ind]);
				txtSingleQuestion[ind].setValue(new Double(defaultValues[ind]));
				txtSingleQuestion[ind].setColumns(8);

				pnlAdditionalQuestions.add(lblSingleQuestion[ind]);
				pnlAdditionalQuestions.add(txtSingleQuestion[ind]);
			}

			pnlBottom.add(pnlAdditionalQuestions, java.awt.BorderLayout.CENTER);
		}


		// - Finally add everything to the main pane
		pnlAll = new javax.swing.JPanel();
		pnlAll.setLayout(new java.awt.BorderLayout());
		pnlAll.add(pnlBottom, java.awt.BorderLayout.SOUTH);
		pnlAll.add(pnlMiddle, java.awt.BorderLayout.CENTER);

		getContentPane().add(pnlAll, java.awt.BorderLayout.CENTER);

		setTitle(title);

		setResizable(true);
		pack();
	}//GEN-END:initComponents



	/**
	 * Method for reading contents of a field
	 * @param	fieldNum	Number of field
	 * @return	Value of the field
	 */
	public double getFieldValue(int fieldNum) {
		return ((Number)txtSingleQuestion[fieldNum].getValue()).doubleValue();
	}

	/**
	 * Method for reading exit code
	 * @return	1=OK clicked, -1=cancel clicked
	 */
	public int getExitCode() {
		return exitCode;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables

	private javax.swing.JPanel pnlAll;	// contains everything


		private javax.swing.JPanel pnlMiddle;	// contains table for sample names and current labels
			private javax.swing.JScrollPane scrSamples;
				private javax.swing.JTable tblSamples;
				private MyTableModel mdlClasses;


		private javax.swing.JPanel pnlBottom;	// contains additional questions and OK & Cancel buttons
			private javax.swing.JPanel pnlAdditionalQuestions;
				private javax.swing.JPanel[] pnlSingleQuestion;
					private javax.swing.JLabel[] lblSingleQuestion;
					private javax.swing.JFormattedTextField[] txtSingleQuestion;
			private javax.swing.JPanel pnlButtons;
				private javax.swing.JButton btnOK;
				private javax.swing.JButton btnCancel;

	// End of variables declaration//GEN-END:variables


	public class MyComboBoxEditor extends DefaultCellEditor {
		public MyComboBoxEditor(String[] items) {
			super(new JComboBox(items));
		}
	}


	private class MyTableModel extends AbstractTableModel {

		String[] colNames = { "Raw data", "Class ID" };

		private Object[] items;
		private Object[] itemLabels;
		private JTable myTable;

		public MyTableModel(Object[] _items, Object[] _itemLabels) {
			items = _items;
			itemLabels = _itemLabels;
		}

		public void setTable(JTable _myTable) {
			myTable = _myTable;
		}

		public int getColumnCount() { return 2;}

		public int getRowCount() {
			if (items==null) { return 0; }
			return items.length;
		}

		public Object getValueAt(int row, int column) {
			if (items==null) { return null; }
			if (column == 0) { return items[row]; }
			if (column == 1) { return itemLabels[row]; }
			return null;
		}

		public void setValueAt(Object aValue, int row, int column) {
			if (itemLabels==null) { return; }
			if (myTable==null) { return; }
			if (column!=1) { return; }

			int[] rows = myTable.getSelectedRows();
			for (int r : rows) { itemLabels[r]=aValue; }
			myTable.repaint();
			return;
		}

		public String getColumnName(int colInd) {
			if (colInd>=colNames.length) { return null; }
			return colNames[colInd];
		}

		public boolean isCellEditable(int rowIndex, int colIndex) {
			if (colIndex==0) { return false; }
			if (colIndex==1) { return true; }
			return false;
		}

	}

}




