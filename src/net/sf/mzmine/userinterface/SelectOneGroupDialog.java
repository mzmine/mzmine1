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

public class SelectOneGroupDialog extends ModalJInternalFrame implements java.awt.event.ActionListener {

	private static final int LISTBOXWIDTH = 250;
	private static final int LISTBOXHEIGHT = 200;
	private static final int BUTTONWIDTH = 90;
	private static final int BUTTONHEIGHT = 35;


	// These are used to store information from caller
	private MainWindow mainWin;
	private String title;
	private String group1Question;
	private String[] additionalQuestions;
	private Float[] defaultValues;



	// Number of additional questions given by caller
	private int numOfAdditionalQuestions;

	// Number formatting used in additional questions
	private java.text.NumberFormat decimalNumberFormat;

	// Selections
	Vector sourceItems;
	Vector targetItemsG1;

	// Exit code (1=OK, -1=Cancel)
	private int exitCode = -1;


    /**
     * Constructor: creates new form SelectOneGroupDialog
     */
    public SelectOneGroupDialog(MainWindow _mainWin, String _title, String _group1Question, Object[] _sourceObjects, String[] _additionalQuestions, Float[] _defaultValues) {
		//super(_mainWin, true);

		additionalQuestions = _additionalQuestions;

		// Store info from caller
		mainWin = _mainWin;
		title = _title;
		group1Question = _group1Question;
		sourceItems = new Vector();
		for (int ind = 0; ind<_sourceObjects.length; ind++) { sourceItems.add(_sourceObjects[ind]); }
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

		// Build the form
        initComponents();

		// Put items to list boxes
		targetItemsG1 = new Vector();
		lstG1SourceList.setListData(sourceItems);
		lstG1TargetList.setListData(targetItemsG1);

    }

	/**
	 * Implementation of ActionListener interface
	 */
    public void actionPerformed(java.awt.event.ActionEvent e) {
		Object src = e.getSource();

		// OK button
		if (src == btnOK) {

			// Validate group selections
			if ((targetItemsG1.size()==0)) {
				mainWin.displayErrorMessage("Please select something to the group.");
				return;
			}

			// Set exit code
			exitCode = 1;

			// Hide form
			disposeModal();
			//setVisible(false);
		}

		// Cancel button
		if (src == btnCancel) {

			// Set exit code
			exitCode = -1;

			// Hide form
			disposeModal();
			//setVisible(false);
		}


		// Select to group one button
		if (src == btnG1SelectToOne) {

			// Get selected items in source list
			Object[] selObjs = lstG1SourceList.getSelectedValues();

			// Put them to g1 target and remove from source list
			for (int ind=0; ind<selObjs.length; ind++) {
				targetItemsG1.add(selObjs[ind]);
				sourceItems.remove(selObjs[ind]);
			}

			lstG1SourceList.setListData(sourceItems);
			lstG1TargetList.setListData(targetItemsG1);

		}

		// Remove from group one button
		if (src == btnG1RemoveFromOne) {
			// Get selected items in source list
			Object[] selObjs = lstG1TargetList.getSelectedValues();

			// Put them to g1 target and remove from source list
			for (int ind=0; ind<selObjs.length; ind++) {
				targetItemsG1.remove(selObjs[ind]);
				sourceItems.add(selObjs[ind]);
			}

			lstG1SourceList.setListData(sourceItems);
			lstG1TargetList.setListData(targetItemsG1);

		}

	}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

		// Construct components
		pnlAll = new javax.swing.JPanel();
		pnlSelections = new javax.swing.JPanel();

		pnlG1Selection = new javax.swing.JPanel();
		pnlG1Top = new javax.swing.JPanel();
		lblG1Question = new javax.swing.JLabel();
		pnlG1Left = new javax.swing.JPanel();
		scrG1SourceList = new javax.swing.JScrollPane();
		lstG1SourceList = new javax.swing.JList();
		pnlG1Mid = new javax.swing.JPanel();
		pnlG1FillMidUpper = new javax.swing.JPanel();
		btnG1SelectToOne = new javax.swing.JButton();
		btnG1RemoveFromOne = new javax.swing.JButton();
		pnlG1FillMidLower = new javax.swing.JPanel();
		pnlG1Right = new javax.swing.JPanel();
		scrG1TargetList = new javax.swing.JScrollPane();
		lstG1TargetList = new javax.swing.JList();

		pnlBottom = new javax.swing.JPanel();
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
			txtSingleQuestion[ind] = new javax.swing.JFormattedTextField(decimalNumberFormat);
		}
		pnlButtons = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();


		// This one should be removed/modified

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);


        // Place components to frame

        pnlAll.setLayout(new java.awt.BorderLayout());

		// - Bottom panel
        pnlBottom.setLayout(new java.awt.BorderLayout());

		// 	* buttons
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
				pnlSingleQuestion[ind].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
				lblSingleQuestion[ind].setText(additionalQuestions[ind]);
				pnlSingleQuestion[ind].add(lblSingleQuestion[ind]);

				txtSingleQuestion[ind].setValue(new Float(defaultValues[ind]));
				txtSingleQuestion[ind].setColumns(8);

				pnlSingleQuestion[ind].add(txtSingleQuestion[ind]);

				pnlAdditionalQuestions.add(pnlSingleQuestion[ind]);
			}

			pnlBottom.add(pnlAdditionalQuestions, java.awt.BorderLayout.CENTER);
		}

        pnlAll.add(pnlBottom, java.awt.BorderLayout.SOUTH);

		// - Group selectors

        pnlSelections.setLayout(new java.awt.GridLayout(1, 1));

		//	* selector for G1

        pnlG1Selection.setLayout(new java.awt.BorderLayout());

		// 		. left panel: source list of items
        pnlG1Left.setLayout(new java.awt.BorderLayout());
        pnlG1Left.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        scrG1SourceList.setViewportView(lstG1SourceList);
        scrG1SourceList.setMinimumSize(new java.awt.Dimension(LISTBOXWIDTH,LISTBOXHEIGHT));
        scrG1SourceList.setPreferredSize(new java.awt.Dimension(LISTBOXWIDTH,LISTBOXHEIGHT));
        pnlG1Left.add(scrG1SourceList, java.awt.BorderLayout.CENTER);
        pnlG1Selection.add(pnlG1Left, java.awt.BorderLayout.WEST);

		// 		. mid panel: buttons and place holders
        pnlG1Mid.setLayout(new java.awt.GridLayout(4, 1));
        pnlG1Mid.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 1, 1, 5)));
        pnlG1Mid.add(pnlG1FillMidUpper);

        btnG1SelectToOne.setText(">");
        btnG1SelectToOne.addActionListener(this);
        btnG1SelectToOne.setMinimumSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
        btnG1SelectToOne.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
        pnlG1Mid.add(btnG1SelectToOne);

        btnG1RemoveFromOne.setText("<");
        btnG1RemoveFromOne.addActionListener(this);
        btnG1RemoveFromOne.setMinimumSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
        btnG1RemoveFromOne.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
        pnlG1Mid.add(btnG1RemoveFromOne);

        pnlG1Mid.add(pnlG1FillMidLower);

        pnlG1Selection.add(pnlG1Mid, java.awt.BorderLayout.CENTER);

        // 		. right panel: target list of items
        pnlG1Right.setLayout(new java.awt.BorderLayout());
        pnlG1Right.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        scrG1TargetList.setViewportView(lstG1TargetList);
        scrG1TargetList.setMinimumSize(new java.awt.Dimension(LISTBOXWIDTH,LISTBOXHEIGHT));
        scrG1TargetList.setPreferredSize(new java.awt.Dimension(LISTBOXWIDTH,LISTBOXHEIGHT));
        pnlG1Right.add(scrG1TargetList, java.awt.BorderLayout.CENTER);
        pnlG1Selection.add(pnlG1Right, java.awt.BorderLayout.EAST);

		//		. top panel: question (user gives)
        pnlG1Top.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        lblG1Question.setText(group1Question);
        pnlG1Top.add(lblG1Question);
        pnlG1Selection.add(pnlG1Top, java.awt.BorderLayout.NORTH);

        pnlSelections.add(pnlG1Selection);


        pnlAll.add(pnlSelections, java.awt.BorderLayout.NORTH);

		// - Finally add everything to the main pane
        getContentPane().add(pnlAll, java.awt.BorderLayout.CENTER);

		setTitle(title);


        pack();
    }//GEN-END:initComponents



	/**
	 * Method for retrieving items selected to the group
	 * @return	Vector containing items selected to the group
	 */
	public Vector getSelectedItems() {
		return targetItemsG1;
	}

	/**
	 * Method for reading contents of a field
	 * @param	fieldNum	Number of field
	 * @return	Value of the field
	 */
	public float getFieldValue(int fieldNum) {
		return ((Number)txtSingleQuestion[fieldNum].getValue()).floatValue();
	}

	/**
	 * Method for reading exit code
	 * @return	1=OK clicked, -1=cancel clicked
	 */
	public int getExitCode() {
		return exitCode;
	}



/*
    public static void main(String args[]) {

				String[] items = { "Run1", "Run2", "Run3", "Run4", "Run5", "Run6"};
				String[] aQs = { "Jep1", "jep2"};
				Float[] dVs = { new Float(10),new Float(2) };

				SelectOneGroupDialog stgd = new SelectOneGroupDialog(null, "Hello world!", "Good morning!", items, aQs, dVs);

                stgd.setVisible(true);

	}
	*/


    // Variables declaration - do not modify//GEN-BEGIN:variables

	private javax.swing.JPanel pnlAll;	// contains everything

		private javax.swing.JPanel pnlSelections;	// contains panels for both G1 and G2 selections

			private javax.swing.JPanel pnlG1Selection;	// contains all stuff needed for selecting items to the first group (G1)
				private javax.swing.JPanel pnlG1Top;	// contains question for the first group
					private javax.swing.JLabel lblG1Question;
				private javax.swing.JPanel pnlG1Left;	// contains source list for the first group
					private javax.swing.JScrollPane scrG1SourceList;
						private javax.swing.JList lstG1SourceList;
				private javax.swing.JPanel pnlG1Mid;	// contains buttons for moving items between source and target in the first group
					private javax.swing.JPanel pnlG1FillMidUpper;	// fillers used in mid panel
					private javax.swing.JButton btnG1SelectToOne;
					private javax.swing.JButton btnG1RemoveFromOne;
					private javax.swing.JPanel pnlG1FillMidLower;
				private javax.swing.JPanel pnlG1Right;	// contains target list for the first group
					private javax.swing.JScrollPane scrG1TargetList;
						private javax.swing.JList lstG1TargetList;


		private javax.swing.JPanel pnlBottom;	// contains additional questions and OK & Cancel buttons
			private javax.swing.JPanel pnlAdditionalQuestions;
				private javax.swing.JPanel[] pnlSingleQuestion;
					private javax.swing.JLabel[] lblSingleQuestion;
					private javax.swing.JFormattedTextField[] txtSingleQuestion;
			private javax.swing.JPanel pnlButtons;
				private javax.swing.JButton btnOK;
				private javax.swing.JButton btnCancel;

    // End of variables declaration//GEN-END:variables

}




