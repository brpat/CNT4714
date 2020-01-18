/*
 * Brijesh Patel
 * CNT 4714 Enterprise in Computing
 * Project 3 
 */

package proj3;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.ScrollPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class MainFrame {

	private JFrame frame;
	private JTextField userNameTextField;
	private ResultSetTableModel tableModel;
	String[] comboBoxDriver = { "com.mysql.cj.jdbc.Driver" };
	String[] comboBoxUrl = { "jdbc:mysql://localhost:3306/project3" };
	static final String DEFAULT_QUERY = "SELECT * FROM racewinners";
	private JTable table;
	private String username = "";
	private String password = "";
	private JPasswordField passwordTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
					// Catch all other exceptions.
				} catch (Exception e) {
					System.out.println("Main Method Catch Triggered");
					System.exit(1);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {

			System.out.println("Setting Table Model");
			frame = new JFrame();
			frame.setTitle("SQL GUI Client - (BJP - CNT 4714 - Fall 2019");
			frame.setBounds(100, 100, 730, 532);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);

			JTextArea sqlInputTextBox = new JTextArea();
			sqlInputTextBox.setWrapStyleWord(true);
			sqlInputTextBox.setLineWrap(true);
			sqlInputTextBox.setBounds(286, 35, 391, 158);
			frame.getContentPane().add(sqlInputTextBox);

			JLabel resultLabel = new JLabel("SQL Execution Result Window");
			resultLabel.setForeground(Color.blue);
			resultLabel.setBounds(10, 251, 201, 23);
			frame.getContentPane().add(resultLabel);

			JButton connectionStatusButton = new JButton("No Connection Now");
			connectionStatusButton.setBackground(Color.BLACK);
			connectionStatusButton.setForeground(Color.red);
			connectionStatusButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			connectionStatusButton.setBounds(10, 217, 201, 23);
			frame.getContentPane().add(connectionStatusButton);

			// Use this button to establish connection to MYSQL Instance
			JButton connectionButton = new JButton("Connect to a Database");
			connectionButton.setBackground(Color.blue);
			connectionButton.setForeground(Color.yellow);
			connectionButton.setBounds(221, 217, 150, 23);
			frame.getContentPane().add(connectionButton);
			// Read in Username and password from respective fields.
			connectionButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					username = userNameTextField.getText();
					password = new String(passwordTextField.getPassword());
					// System.out.println(username + " " + password);

					connectionStatusButton.setText("Connected to jdbc:mysql://localhost:3306/project3");
				}
			});

			JButton clearButton = new JButton("Clear SQL Command");
			clearButton.setBackground(Color.white);
			clearButton.setForeground(Color.red);
			clearButton.setBounds(393, 217, 139, 23);
			frame.getContentPane().add(clearButton);

			// Clear Input in SQL Text Box
			clearButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sqlInputTextBox.setText("");
				}
			});

			JButton executeButton = new JButton("Execute SQL Command");
			executeButton.setBackground(Color.green);
			executeButton.setForeground(Color.black);
			executeButton.setBounds(542, 217, 162, 23);
			frame.getContentPane().add(executeButton);

			executeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					username = userNameTextField.getText();
					password = new String(passwordTextField.getPassword());

					String str = sqlInputTextBox.getText().substring(0, 6);
					System.out.println(str);
					// Client can only use SELECT command. Check syntax for SELECT
					if (str.compareToIgnoreCase("SELECT") != 0 && username.compareToIgnoreCase("client") == 0) {
						System.out.println("No access");
						JOptionPane
								.showMessageDialog(
										null, str + " " + "command denied to user" + "'" + username
												+ "'@'localhost' for " + tableModel.getTable(),
										"Database error", JOptionPane.ERROR_MESSAGE);

					}
					// If SQL Command is valid execute below block
					else {
						try {
							try {
								tableModel = new ResultSetTableModel(sqlInputTextBox.getText(), username, password);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							connectionStatusButton.setText("Connected to jdbc:mysql://localhost:3306/project3");
							// Will store results
							table = new JTable(tableModel);
							table.setBounds(370, 382, 200, 200);
							frame.getContentPane().add(table);

							// SQL Result Output Container
							JScrollPane sqlOutputScrollPane = new JScrollPane(table,
									ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
									ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
							sqlOutputScrollPane.setBounds(4, 269, 657, 189);
							frame.getContentPane().add(sqlOutputScrollPane);

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			});

			JLabel driverLabel = new JLabel("JDBC Driver");
			driverLabel.setBounds(10, 34, 76, 14);
			frame.getContentPane().add(driverLabel);

			JLabel urlLabel = new JLabel("Database URL");
			urlLabel.setBounds(10, 62, 94, 14);
			frame.getContentPane().add(urlLabel);

			JLabel usernameLabel = new JLabel("Username");
			usernameLabel.setBounds(10, 90, 64, 14);
			frame.getContentPane().add(usernameLabel);

			JLabel passwordLabel = new JLabel("Password");
			passwordLabel.setBounds(10, 115, 48, 14);
			frame.getContentPane().add(passwordLabel);

			JComboBox driverComboBox = new JComboBox(comboBoxDriver);
			driverComboBox.setBounds(101, 35, 150, 22);
			frame.getContentPane().add(driverComboBox);

			JComboBox urlComboBox = new JComboBox(comboBoxUrl);
			urlComboBox.setBounds(101, 58, 150, 22);
			frame.getContentPane().add(urlComboBox);

			userNameTextField = new JTextField();
			userNameTextField.setBounds(101, 87, 150, 20);
			frame.getContentPane().add(userNameTextField);
			userNameTextField.setColumns(10);

			passwordTextField = new JPasswordField();
			passwordTextField.setBounds(101, 113, 150, 19);
			frame.getContentPane().add(passwordTextField);

			JButton clearResultButton = new JButton("Clear Result Window");
			clearResultButton.setBounds(10, 459, 174, 23);
			clearResultButton.setBackground(Color.yellow);
			clearResultButton.setForeground(Color.black);
			frame.getContentPane().add(clearResultButton);

			// Clear output window
			clearResultButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					table.setModel(new DefaultTableModel());
					tableModel = null;
				}

			});

			JLabel leftHeader = new JLabel("Enter Database Information");
			leftHeader.setBounds(10, 11, 162, 14);
			leftHeader.setForeground(Color.blue);
			frame.getContentPane().add(leftHeader);

			JLabel rightHeader = new JLabel("Enter SQL Command");
			rightHeader.setBounds(286, 11, 133, 14);
			rightHeader.setForeground(Color.blue);
			frame.getContentPane().add(rightHeader);

		} catch (Exception e) {
			System.out.println("Error creating GUI");
		}

	}
}

// Implement Back-end Logic
class Processor {
	public Processor() {

	}
}
