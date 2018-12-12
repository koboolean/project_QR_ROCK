<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@ include file="OracleConnection.jsp"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.json.simple.*"%>
<%@ page import ="static db.JdbcUtil.*" %>
<%
	JSONArray arr = new JSONArray(); //JSON 배열에 캡슐화하는 느낌으로 안드로이드랑 통신
	JSONObject obj=new JSONObject();
	
	String user_Device_Id= "지원3";/*  request.getParameter("user_Device_Id"); */
	String user_QR_Code=request.getParameter("user_QR_Code");
	String user_Phone=request.getParameter("user_Phone");
	String user_Password=request.getParameter("user_Password");
	String mac_Add=request.getParameter("mac_add");
	
	
	 int insertcount = 0;
	
	
	
try{
	
	
	
	//sql="insert into QRock(User_QR_Device_Id,User_QR_Code,User_Phone) values(?,?,?)";
	sql="insert into qr_user(user_Device_Id, user_QR_Code, user_Phone, user_Password, user_Admin) values(?,?,?,?,?)";
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, user_Device_Id);
	pstmt.setString(2, user_Device_Id);
	pstmt.setString(3, user_QR_Code);
	pstmt.setString(4, user_Phone);
	pstmt.setString(5, user_Password);
	pstmt.setInt(6,1);
	
	insertcount=pstmt.executeUpdate();
	
	obj.put("insertcount",insertcount);
	
	
	if (obj != null) {
		obj.put("user_Device_Id",user_Device_Id);
		
	}else{
		obj.put("user_Device_Id","?");
	}
	 
} catch (SQLException se) {
	out.println(se.getMessage());
} finally {
	if (rs != null)
		rs.close();
	if (pstmt != null)
		pstmt.close();
	if (con != null)
		con.close();
}

%>
<%=obj.toJSONString() %>