<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@ include file="OracleConnection.jsp"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.json.simple.*"%>
<%@ page import ="static db.JdbcUtil.*" %>
<%
	String User_QR_Device_Id=request.getParameter("User_QR_Device_Id");
	String User_QR_Code=request.getParameter("User_QR_Code");
	String User_Phone=request.getParameter("User_Phone");
	int insertCount = 0;
	JSONArray arr = new JSONArray(); //JSON 배열에 캡슐화하는 느낌으로 안드로이드랑 통신
	
try{
	JSONObject obj=new JSONObject();
	
	sql="insert into QRock(User_QR_Device_Id,User_QR_Code,User_Phone) values(?,?,?)";
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, User_QR_Code);
	pstmt.setString(2, User_QR_Code);
	pstmt.setString(3, User_Phone);
	insertCount=pstmt.executeUpdate();
	
	obj.put("insertCount",insertCount);
	
	if (obj != null) {
		arr.add(obj);
	}else{
		arr.add("?");	//디비에서 해당 값을 찾지못하거나 애트리뷰트가 비어있을 때
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