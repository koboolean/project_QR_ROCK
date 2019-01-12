<%@ page language="java" import="java.sql.*"%>

<%
	
//해당 디비주소를 입력하면 어디서든 작업가능
	String DB_URL = "jdbc:oracle:thin:@203.244.145.214:1521:XE";
	String DB_USER = "QRock";
	String DB_PASSWORD = "1234";

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
	catch (SQLException e) {
		out.println(e);
	}
%>
