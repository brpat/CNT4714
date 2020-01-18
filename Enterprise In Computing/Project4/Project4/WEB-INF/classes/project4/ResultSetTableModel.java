/*
Brijesh Patel
CNT 4714
Enterprise in Computing Fall 2019
Project 4
*/

package project4;

// A TableModel that supplies ResultSet data to a JTable.
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.Properties;
import javax.sql.DataSource;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.MysqlDataSource;

// ResultSet rows and columns are counted from 1 and JTable 
// rows and columns are counted from 0. When processing 
// ResultSet rows or columns for use in a JTable, it is 
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is 
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class ResultSetTableModel extends AbstractTableModel {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet = null;
	private String messageBoxString = null;
	private ResultSetMetaData metaData;
	private int numberOfRows;

	// keep track of database connection status
	private boolean connectedToDatabase = false;

	// constructor initializes resultSet and obtains its meta data object;
	// determines number of rows
	public ResultSetTableModel(String query) throws SQLException, ClassNotFoundException {
		Properties properties = new Properties();
		FileInputStream filein = null;
		MysqlDataSource dataSource = null;
		// read properties file
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			// filein = new FileInputStream("db.properties");
			// properties.load(filein);
			dataSource = new MysqlDataSource();
			dataSource.setURL("jdbc:mysql://localhost:3306/project4");
			dataSource.setUser("root");
			dataSource.setPassword("root");
			// dataSource.setUser(username);
			// dataSource.setPassword(password);
			// MYSQL_DB_DRIVER_CLASS=com.mysql.cj.jdbc.Driver

			// connect to database bikes and query database
			// establish connection to database
			Connection connection = dataSource.getConnection();
			System.out.println("HERE NOW");
			// create Statement to query database
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// update database connection status
			connectedToDatabase = true;
			System.out.println("Connection to MYSQL Instance Successful");
			// set query and execute it
			int tmp = 0;
			// Data Manipulation Commands
			// if (query.substring(0, 6).compareToIgnoreCase("SELECT") != 0)
			// tmp = statement.executeUpdate(query);
			if(query.contains("insert") || query.contains("update")
			   || query.contains("Insert") || query.contains("Update"))
			{
				System.out.println("Update or Insert");
				// set update and execute it
				setMessageBoxString(setUpdate (query));
			}
			else {
				// specify query and execute it (SELECT)
				setQuery(query);
				System.out.println("Select QUERY Executed");
			}

			// set update and execute it
			// setUpdate (query);
		} // end try
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);

		} // end catch
	} // end constructor ResultSetTableModel
	
	public ResultSet getSet()
	{
		return resultSet;
	}

	// get class that represents column type
	public Class getColumnClass(int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// determine Java class of column
		try {
			String className = metaData.getColumnClassName(column + 1);

			// return Class object that represents className
			return Class.forName(className);
		} // end try
		catch (Exception exception) {
			exception.printStackTrace();
		} // end catch

		return Object.class; // if problems occur above, assume type Object
	} // end method getColumnClass

	// get number of columns in ResultSet
	public int getColumnCount() throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// determine number of columns
		try {
			return metaData.getColumnCount();
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch

		return 0; // if problems occur above, return 0 for number of columns
	} // end method getColumnCount

	// get name of a particular column in ResultSet
	public String getColumnName(int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// determine column name
		try {
			return metaData.getColumnName(column + 1);
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch

		return ""; // if problems, return empty string for column name
	} // end method getColumnName

	// return number of rows in ResultSet
	public int getRowCount() throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		return numberOfRows;
	} // end method getRowCount

	// obtain value in particular row and column
	public Object getValueAt(int row, int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// obtain a value at specified ResultSet row and column
		try {
			resultSet.next(); /* fixes a bug in MySQL/Java with date format */
			resultSet.absolute(row + 1);
			return resultSet.getObject(column + 1);
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch

		return ""; // if problems, return empty string object
	} // end method getValueAt

	
	// set new database query string
	public void setQuery(String query) throws SQLException, IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");
		resultSet = statement.executeQuery(query);
		/*
		while (resultSet.next()) {	
			System.out.println(resultSet.getString("bikename"));
		}
		*/
		// obtain meta data for ResultSet
		//metaData = resultSet.getMetaData();

		// determine number of rows in ResultSet
		//resultSet.last(); // move to last row
		//numberOfRows = resultSet.getRow(); // get row number

		// notify JTable that model has changed
		//fireTableStructureChanged();
	} // end method setQuery

// set new database update-query string
	public String setUpdate(String query) throws SQLException, IllegalStateException {
		int res;
		int affectedRows;
		String messageBox = "";
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");
		System.out.println("Updating database");
		// specify query and execute it
		res = statement.executeUpdate(query);
		
		
		affectedRows = res;
		messageBox = "<Textarea rows=10 cols=64 style=background-color:lime;color:Azure;>";
		messageBox = messageBox +"The Statement executed successfully." + affectedRows + " rows(s) affected.\n";
		
		if(query.toLowerCase().contains("insert into shipments")) {
			System.out.println("Inserting into shipments");
			String quantity = query.substring(45).replaceAll("[^0-9]", "");
			int quantityCount = Integer.parseInt(quantity);
				if(quantityCount >= 100) {
					messageBox = messageBox + "\nBusiness Logic Detected! Updating Supplier Status...\n";
					String updateSQL = "Update suppliers set status = status + 5 where" + " snum in (select snum from shipments where quantity >= 100);";
					affectedRows = statement.executeUpdate(updateSQL);
					messageBox = messageBox + "\nBusiness Logic updated " + affectedRows + " supplier status marks.";
				}
		}
		
		if(query.contains("update shipments")) {
			messageBox = messageBox + "\nBusiness Logic Detected! Updating Supplier Status...\n";
			String updateSQL = "Update suppliers set status = status + 5 where" + " snum in (select snum from shipments where quantity >= 100);";
			affectedRows = statement.executeUpdate(updateSQL);
			messageBox = messageBox + "\nBusiness Logic updated " + affectedRows + " supplier status mark.";
		}

		return messageBox + "</Textarea>";
		/*
		 * // obtain meta data for ResultSet metaData = resultSet.getMetaData(); //
		 * determine number of rows in ResultSet resultSet.last(); // move to last row
		 * numberOfRows = resultSet.getRow(); // get row number
		 */
		// notify JTable that model has changed
		//fireTableStructureChanged();
	} // end method setUpdate

	// Return current table name
	public String getTable() {

		try {
			return metaData.getTableName(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to get table name");
			return null;
		}
	}

	// close Statement and Connection
	public void disconnectFromDatabase() {
		if (!connectedToDatabase)
			return;
		// close Statement and Connection
		try {
			statement.close();
			connection.close();
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
		finally // update database connection status
		{
			connectedToDatabase = false;
		} // end finally
	} // end method disconnectFromDatabase

	public String getMessageBoxString() {
		return messageBoxString;
	}

	public void setMessageBoxString(String messageBoxString) {
		this.messageBoxString = messageBoxString;
	}
} // end class ResultSetTableModel
