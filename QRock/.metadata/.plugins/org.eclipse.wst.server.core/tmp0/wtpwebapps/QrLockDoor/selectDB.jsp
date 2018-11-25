<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

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
	JSONArray arr = new JSONArray();
	System.out.println("--------------11");
	
try{
	
	sql="select * from qr_user";
	
	pstmt = con.prepareStatement(sql);
	

	rs = pstmt.executeQuery();
	
	

	if(rs.next()){
		JSONObject obj=new JSONObject();
		
		String id=rs.getString("User_QR_Device_Id");
		String code=rs.getString("User_QR_Code");
		String phone=rs.getString("User_Phone"); 
		System.out.println("---------------3");
		System.out.println(id);
		
		obj.put("id",id);
		obj.put("code", code);
		obj.put("phone", phone);
		
		System.out.print(id);
		if (obj != null) {
			arr.add(obj);
		}else{
			arr.add("?");
		}
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
<%=arr.toJSONString() %>
