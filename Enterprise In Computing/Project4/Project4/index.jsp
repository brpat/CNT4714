<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%--Have a clear table in the beginning --%>
<% String message = (String) session.getAttribute("message"); 
   if (message == null) 
   		message = "";
   else
	   session.setAttribute("message", "");
%>

<html>
	<head>
	<title>CNT 4714 Remote Database Management System</title>
	<style>input.sqlbox{text-align:left}</style>
	<script src="reset.js"></script>
	</head>
	<body style="background-color:blue;">
		<h2 align="center" style="color:white;">Welcome to the Fall 2019 Project 4 Enterprise System</h2>
		<h2 align="center" style="color:white;">A Remote Database Management System</h2>
		<hr>
		<p align="center" style="color:white;" >
			You are connected to the project 4 Enterprise System Database <br>
			Please enter any valid SQL query or update statement.<br>
			If no query/update command is initially provided, the Execute button will display 
			all supplier information in the database <br>
			All execution results will appear below. <br>
		</p>
		<%-- SQL QUERY Text Box --%>>
		<div align="center">
			<form action="Project4Servlet" method="POST">
				<TextArea rows="10" cols="73" name="queryText" id="sqlbox" style="background-color:black;color:#02cd1d;">
				</TextArea>> <br><br>
				<input type="submit" value="Execute Command" style="background-color:black;color:yellow;" />
				<input type="submit" formaction="reset.js" value="Reset Form" style="background-color:black;color:yellow;" />
				<hr>
				<div align="center" style="color:white;"> Database Results: </div> <br>
			</form>
		</div>
		<style>			
			tr:nth-child(even) {background-color: #f2f2f2;}
			tr:nth-child(odd) {background-color: grey;}
		</style>
		<%-- JSP Message --%>
		<div id="MyDiv" align="center"><%=message%></div>
	</body>
</html>