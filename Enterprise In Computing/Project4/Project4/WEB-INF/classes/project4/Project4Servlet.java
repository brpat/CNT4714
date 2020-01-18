/*
Brijesh Patel
CNT 4714
Enterprise in Computing Fall 2019
Project 4
*/

package project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

/**
 * Servlet implementation class Project4Servlet
 */
@WebServlet("/Project4Servlet")
public class Project4Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResultSetTableModel tableModel;
       
 
    public Project4Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String tableString = null;
		
		String message = request.getParameter("queryText");
		if(message.length() == 4)
		{
			System.out.println("Reset");
			System.exit(0);
			

			HttpSession session = request.getSession();
			session.setAttribute("message", "");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
		System.out.println(message);
		try {
			try {
				tableModel = new ResultSetTableModel(message);
				tableString = ResultSetToString(tableModel);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Will store results
			//table = new JTable(tableModel);
			

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		HttpSession session = request.getSession();
		session.setAttribute("message", tableString);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}


	public String ResultSetToString(ResultSetTableModel tableModel) throws SQLException
	{
		String tableHeaders = "<table align='center'> <tr>" + "<th bgcolor='#d1291e'>";
		
		if(tableModel.getSet() == null)
			return tableModel.getMessageBoxString();
		ResultSetMetaData rsmd = (ResultSetMetaData) tableModel.getSet().getMetaData();
		int columnCount = rsmd.getColumnCount();
		System.out.println("Starting Loop");
		// The column count starts from 1
		for (int i = 1; i <= columnCount; i++ ) {
		  String name = rsmd.getColumnName(i);
		  if(i != columnCount)
			  tableHeaders = tableHeaders + name + "</th>" + "<th bgcolor='#d1291e'>";
		  else
			  tableHeaders = tableHeaders + name + "</th>";
		}
		
		try {
			while (tableModel.getSet().next()) {	
				//System.out.println(resultSet.getString("sname"));
				tableHeaders = tableHeaders + "<tr>";
				// Generate Table String
				for(int i = 1; i <= columnCount; i++)
				{
					//System.out.println(resultSet.getString((rsmd.getColumnName(i))));
					tableHeaders = tableHeaders + "<td>" + tableModel.getSet().getString((rsmd.getColumnName(i))) + "</td>";
				}
				tableHeaders = tableHeaders + "</tr>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableHeaders = tableHeaders + "</table>";
		return tableHeaders;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}