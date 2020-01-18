import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Processor {
	// Implement logic for actions performed
	// on GUI components

	public int counter = 1;
	private int currentItemNumber = 1;
	private InputPanel panel;
	private int totalItems = 0;
	private BufferedReader reader;
	private HashMap<String, String> inventory;
	private Double totalCost = 0.0, prevItemCost = 0.0;
	private ArrayList<String> order = new ArrayList<>();

	// I'm not worried about Exceptions at the moment
	public Processor() throws IOException {
		inventory = new HashMap<String, String>();
		reader = new BufferedReader(new FileReader("inventory.txt"));
		String str;
		String[] arr;
		// initialize inventory
		do {
			str = reader.readLine();

			if (str != null) {
				arr = str.split(",");
				inventory.put(arr[0], str);
			}
		} while (str != null);
	}

	// Process data in text field. Will return true or false
	// depending on if the action was succesful
	public Boolean processItem(String item) throws IOException {
		// Parse each row in csv file

		if (inventory.containsKey(item)) {
			System.out.println("Item found");

			int itemAmount = Integer.parseInt(panel.itemQuantity.getText());
			double discount = 0.0;

			if (itemAmount > 4 && itemAmount < 10)
				discount = .10;
			else if (itemAmount >= 10 && itemAmount <= 14)
				discount = .15;
			else if (itemAmount > 14)
				discount = .20;

			String[] tmp = inventory.get(item).split(",");
			int amount = Integer.parseInt(panel.itemQuantity.getText());

			Double newCost = amount * (Double.parseDouble(tmp[2]) - (Double.parseDouble(tmp[2]) * discount));
			//System.out.println(Double.parseDouble(tmp[2]) + " " + newCost);
			newCost = Math.floor(newCost * 100) / 100;
			// I'm multiplying the discount by 100 for visual clarity. It is easier to
			// to read 10.0 % than .1%
			String str = inventory.get(item) + " " + panel.itemQuantity.getText() + " " + discount * 100 + "%" + " "
					+ "$" + newCost;
			order.add(str);
			panel.itemInfo.setText(str);

			// Save the cost of this item for use later
			setPrevItemCost(newCost);

			totalItems = Integer.parseInt(panel.totalQuantity.getText());

		} else {
			// If Book ID is invalid(Title does not exist in file) then present the
			// the user with a message.
			String str = "Book ID " + item + " Not in File";
			JOptionPane.showMessageDialog(panel, str, "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;

	}

	public void setPanel(InputPanel panel) {
		this.panel = panel;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getCurrentItemNumber() {
		return currentItemNumber;
	}

	public void setCurrentItemNumber(int currentItemNumber) {
		this.currentItemNumber = currentItemNumber;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public ArrayList<String> getOrder() {
		return this.order;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getPrevItemCost() {
		return prevItemCost;
	}

	public void setPrevItemCost(Double prevItemCost) {
		this.prevItemCost = prevItemCost;
	}

	// Update subtotal amount
	public void updateTotal() {
		// Update sub-total
		Double total = getTotalCost() + getPrevItemCost();
		total = Math.floor(total * 100) / 100;
		setTotalCost(total);

	}

	public void displayOrderInvoice() {
		// Display date and time in a specific format
		String timePattern = "MM-dd-yyyy, HH:mm:ss a";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
		// Get current time and date, and then change that timestamp to the
		// pattern format we created.
		String date = simpleDateFormat.format(new Date()) + " EDT\n\n";
		String output = "Date: ";

		output = output + date;
		output = output + "Number of line items:" + getTotalItems() + "\n\n";
		output = output + "Item# / ID / Title / Price / Qty / Disc% / SubTotal\n\n";

		String tmp = "";
		ArrayList<String> tmpOrder = getOrder();
		int len = tmpOrder.size();

		System.out.println("Displaying Invoice");
		// We are building an output string to display to the user
		// containing their entire order
		for (int i = 0; i < len; i++) {
			String tmp2 = (i + 1) + ". " + (order.get(i) + "\n");
			tmp = tmp + tmp2;
		}

		// Create format for invoice
		output = output + tmp + "\n\n\n";
		output = output + "Order Subtotal: $" + this.totalCost + "\n\n";
		output = output + "Tax Rate:   6%\n\n";
		// Round to two decimal places
		Double tax = Math.floor((.06 * this.totalCost) * 100) / 100;
		output = output + "Tax Amount: $" + tax + "\n\n";
		// Round to two decimal places
		this.totalCost = Math.floor(this.totalCost * 1.06 * 100) / 100;
		output = output + "Order Total: $" + this.totalCost + "\n\n";
		output = output + "Thank you for shopping at the Ye Old Book Shoppe!";

		JOptionPane.showMessageDialog(panel, output, "Message", JOptionPane.INFORMATION_MESSAGE);

	}

	public int getTotalItems() {
		return totalItems;
	}

	public void clearTotalItems() {
		this.totalItems = 0;
	}

	public void clearOrder() {
		order = new ArrayList<>();
	}

	public void logTransaction() throws IOException {
		FileWriter writer = new FileWriter("transactions.txt", true);

		// Display date and time in a specific format
		String timePattern = "ddMMyyyyHHmmss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
		// Get current time and date, and then change that timestamp to the
		// pattern format we created.
		String date = simpleDateFormat.format(new Date());

		// Display date and time in a specific format
		String timePattern2 = "MM-dd-yyyy, HH:mm:ss a";
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(timePattern2);
		// Get current time and date, and then change that timestamp to the
		// pattern format we created.
		String date2 = simpleDateFormat2.format(new Date()) + " EDT\n\n";

		int len = order.size();
		for (int i = 0; i < len; i++) {

			writer.write(date + ", " + order.get(i) + ", " + date2);
			writer.write(System.getProperty("line.separator"));
		}

		writer.close();

	}

}
