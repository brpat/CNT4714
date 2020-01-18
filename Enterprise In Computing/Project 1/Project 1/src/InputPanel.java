import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class InputPanel extends JPanel {
	// All the input fields and their associated text will be
	// created as part of this class.

	String[] labels;
	JTextField totalQuantity, bookId, itemQuantity, itemInfo, subtotal;
	JLabel itemQLabel, bookIdLabel, itemQuantityLabel, itemInfoLabel, subtotalLabel;
	private JTextField[] fieldList;
	private JLabel[] labelList;

	public InputPanel(String[] labels) {
		setLabels(labels);

		totalQuantity = new JTextField(40);
		totalQuantity.setBackground(Color.WHITE);
		itemQLabel = new JLabel(labels[0]);
		;
		itemQLabel.setForeground(Color.yellow);

		bookId = new JTextField(40);
		bookId.setBackground(Color.WHITE);
		bookIdLabel = new JLabel(labels[1]);
		bookIdLabel.setForeground(Color.yellow);

		itemQuantity = new JTextField(40);
		itemQuantity.setBackground(Color.WHITE);
		itemQuantityLabel = new JLabel(labels[2]);
		itemQuantityLabel.setForeground(Color.yellow);

		itemInfo = new JTextField(40);
		itemInfo.setBackground(Color.white);
		itemInfo.setEditable(false);
		itemInfoLabel = new JLabel(labels[3]);
		itemInfoLabel.setForeground(Color.yellow);

		subtotal = new JTextField(40);
		subtotal.setBackground(Color.white);
		subtotal.setEditable(false);
		subtotalLabel = new JLabel(labels[4]);
		subtotalLabel.setForeground(Color.yellow);

		// Not the best way of doing this, however for this project
		// I'm not worried. Normally I would loop through an already initialized
		// array of text-fields and labels.
		fieldList = new JTextField[5];
		fieldList[0] = totalQuantity;
		fieldList[1] = bookId;
		fieldList[2] = itemQuantity;
		fieldList[3] = itemInfo;
		fieldList[4] = subtotal;

		labelList = new JLabel[5];
		labelList[0] = itemQLabel;
		labelList[1] = bookIdLabel;
		labelList[2] = itemQuantityLabel;
		labelList[3] = itemInfoLabel;
		labelList[4] = subtotalLabel;
		/*
		 * totalQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		 * bookId.setHorizontalAlignment(SwingConstants.RIGHT);
		 */
		SpringLayout layout = new SpringLayout();

		// Adjust the positions of each label and text field. Hardcoded Positions.
		// the "this" keyword references this particular Panel Object.
		// North position puts the label or textfield in the northern most part of the
		// remaining
		// free space of a container.
		layout.putConstraint(SpringLayout.NORTH, itemQLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, totalQuantity, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, itemQLabel, 200, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, totalQuantity, 10, SpringLayout.EAST, itemQLabel);

		layout.putConstraint(SpringLayout.NORTH, bookIdLabel, 5, SpringLayout.SOUTH, itemQLabel);
		layout.putConstraint(SpringLayout.WEST, bookIdLabel, 270, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, bookId, 5, SpringLayout.SOUTH, totalQuantity);
		layout.putConstraint(SpringLayout.WEST, bookId, 5, SpringLayout.EAST, bookIdLabel);

		layout.putConstraint(SpringLayout.NORTH, itemQuantityLabel, 10, SpringLayout.SOUTH, bookIdLabel);
		layout.putConstraint(SpringLayout.WEST, itemQuantityLabel, 258, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, itemQuantity, 5, SpringLayout.SOUTH, bookId);
		layout.putConstraint(SpringLayout.WEST, itemQuantity, 5, SpringLayout.EAST, itemQuantityLabel);

		layout.putConstraint(SpringLayout.NORTH, itemInfoLabel, 10, SpringLayout.SOUTH, itemQuantityLabel);
		layout.putConstraint(SpringLayout.WEST, itemInfoLabel, 340, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, itemInfo, 5, SpringLayout.SOUTH, itemQuantity);
		layout.putConstraint(SpringLayout.WEST, itemInfo, 5, SpringLayout.EAST, itemInfoLabel);

		layout.putConstraint(SpringLayout.NORTH, subtotalLabel, 10, SpringLayout.SOUTH, itemInfoLabel);
		layout.putConstraint(SpringLayout.WEST, subtotalLabel, 257, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, subtotal, 5, SpringLayout.SOUTH, itemInfo);
		layout.putConstraint(SpringLayout.WEST, subtotal, 5, SpringLayout.EAST, subtotalLabel);

		setLayout(layout);

		add(itemQLabel);
		add(totalQuantity);
		add(bookIdLabel);
		add(bookId);
		add(itemQuantityLabel);
		add(itemQuantity);
		add(itemInfo);
		add(itemInfoLabel);
		add(subtotal);
		add(subtotalLabel);

	}

	public JTextField[] getTextFields() {
		return this.fieldList;
	}

	public JLabel[] getLabelList() {
		return this.labelList;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;

	}

}
