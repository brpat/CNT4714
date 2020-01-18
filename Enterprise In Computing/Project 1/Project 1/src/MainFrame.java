import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class MainFrame extends JFrame{
	// Controller class for all GUI Components
	
	private AppButton buttonList;
	private InputPanel inputPanel;
	private String[] labels;
	private Processor processor;
	// Initial
	private int currentItemNumber = 1;

	public MainFrame() throws IOException
	{
		// Call JFrame's constructor to create Window
		super("Ye Olde Book Shoppe");
		String currentItem = Integer.toString(currentItemNumber);
		//Initial Labels
		
		processor = new Processor();
		
		String[] labels = {"Enter number of items in this order:",
				"Enter book id for Item#" + processor.getCurrentItemNumber(), "Enter Quantity for Item #" + currentItem, 
				"Item#1 Info", "Order subtotal for 0 items"};

		setLayout(new BorderLayout());
		buttonList = new AppButton(processor);
		inputPanel = new InputPanel(labels);

		// Add all components to Window
		// Add action buttons
		add(buttonList, BorderLayout.SOUTH);
		buttonList.setBackground(Color.BLUE);
		
		// Add all labels and text fields
		add(inputPanel, BorderLayout.CENTER);
		inputPanel.setBackground(Color.black);

		buttonList.setTextFieldList(inputPanel.getTextFields());
		buttonList.setLabelList(inputPanel.getLabelList());
		processor.setPanel(inputPanel);
		
		// Properly size JFrame Window
		setSize(730,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
}
