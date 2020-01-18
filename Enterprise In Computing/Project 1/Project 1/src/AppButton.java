import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppButton extends JPanel implements ActionListener {
	// Create buttons, textfields, and custom classes.
	// This class controls what each app button does.
	private JButton process, confirm, viewOrder, finishOrder, newOrder, exit;
	// All textfields
	private JTextField[] fieldList;
	private JLabel[] labelList;
	private Processor processor;

	public AppButton(Processor processor) {
		// Processor controls app logic
		this.processor = processor;
		process = new JButton("Process Item#" + processor.counter);
		confirm = new JButton("Confirm Item#" + processor.counter);
		viewOrder = new JButton("View Order");
		finishOrder = new JButton("Finish Order");
		newOrder = new JButton("New Order");
		exit = new JButton("Exit");

		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Add listeners for all the buttons
		process.addActionListener(this);
		confirm.addActionListener(this);
		viewOrder.addActionListener(this);
		finishOrder.addActionListener(this);
		newOrder.addActionListener(this);
		exit.addActionListener(this);

		confirm.setEnabled(false);
		viewOrder.setEnabled(false);
		finishOrder.setEnabled(false);

		add(process);
		add(confirm);
		add(viewOrder);
		add(finishOrder);
		add(newOrder);
		add(exit);
	}

	// Pass in an array of text-fields.
	public void setTextFieldList(JTextField[] fields) {
		this.fieldList = fields;
	}

	public void setLabelList(JLabel[] labels) {
		this.labelList = labels;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// We need to find out which button was clicked
		// Then we take the appropriate action.
		JButton whichButton = (JButton) e.getSource();

		if (whichButton == process) {
			// My personal debugging message
			String str = fieldList[0].getText();

			fieldList[0].setEnabled(false);
			// Process Book ID
			try {
				Boolean val = processor.processItem(fieldList[1].getText());

				// Item Processing was successful
				// Adjust buttons
				if (val) {
					confirm.setEnabled(true);
					process.setEnabled(false);
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("IO Exception has Occured");
			}

			// There are more items to add
			if(processor.counter > 1)
			{
				System.out.println("Changing label");
				labelList[3].setText("Item#" + processor.counter + " Info");
			}

		}
		// User clicked the confirm button
		else if (whichButton == confirm) {

			processor.updateTotal();

			fieldList[4].setText("$" + processor.getTotalCost());

			// Clear input fields for next item to be processed(if there are more)
			fieldList[1].setText("");
			fieldList[2].setText("");
			// Display Item Accepted message to user if Item is valid.
			JOptionPane.showMessageDialog(this, "Item# " + processor.counter + " accepted", "Message",
					JOptionPane.INFORMATION_MESSAGE);

			// The total number of books the user wanted to buy have been bought
			// alternative Integer.parseInt(fieldList[0].getText())
			if (processor.getTotalItems() == processor.counter) {
				// Don't decrement on the final order
				labelList[4].setText("Order subtotal for " + (processor.counter) + " items");
				finishOrder.setEnabled(true);
				fieldList[1].setEnabled(false);
				fieldList[2].setEnabled(false);
				process.setEnabled(false);
				confirm.setEnabled(false);
			}
			else
			{

				processor.counter++;
				labelList[1].setText("Enter book id for Item#" + processor.counter);
				labelList[2].setText("Enter Quantity for Item#" + processor.counter);
				// Initial sub-total items count is zero. Zero items to begin with
				labelList[4].setText("Order subtotal for " + (processor.counter - 1) + " items");

				process.setText("Process Item#" + processor.counter);
				confirm.setText("Confirm Item#" + processor.counter);

				confirm.setEnabled(false);
				process.setEnabled(true);
			}

			viewOrder.setEnabled(true);
		}

		else if (whichButton == viewOrder) {
			String output = "";
			ArrayList <String> order = processor.getOrder();
			int len = order.size();

			// We are building an output string to display to the user
			// containing their entire order
			for(int i = 0; i < len; i++)
			{
				String tmp = (i + 1) + ". " + (order.get(i) + "\n");
				output = output + tmp;
			}

			JOptionPane.showMessageDialog(this, output, "Message",
					JOptionPane.INFORMATION_MESSAGE);

		}

		else if (whichButton == finishOrder) {

			processor.displayOrderInvoice();
			try {
				processor.logTransaction();
			} catch (IOException e1) {
				System.out.println("IO Exception occured when writing transaction");
			}

		}

		// Restart Order
		// Loop through all textfields and clear them out
		else if (whichButton == newOrder) {
			int len = fieldList.length;

			fieldList[0].setEnabled(true);
			fieldList[1].setEnabled(true);
			fieldList[0].setEnabled(true);

			for (int i = 0; i < len; i++)
				fieldList[i].setText("");

			labelList[1].setText("Enter book id for Item#" + processor.getCurrentItemNumber());
			labelList[2].setText("Enter Quantity for Item#" + processor.getCurrentItemNumber());
			labelList[3].setText("Item#1 Info");
			labelList[4].setText("Order subtotal for 0 items");

			// reset sub-total
			processor.setTotalCost(0.0);
			processor.clearTotalItems();
			processor.counter = 1;
			processor.clearOrder();
			process.setEnabled(true);
			viewOrder.setEnabled(false);
			finishOrder.setEnabled(false);
			//Reset Button Text
			process.setText("Process Item#" + processor.counter);
			confirm.setText("Confirm Item#" + processor.counter);

		}
		// Close Program
		else if (whichButton == exit) {
			System.exit(0);
		}

	}

}
