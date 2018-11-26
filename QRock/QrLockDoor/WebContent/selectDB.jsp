<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@ include file="OracleConnection.jsp"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="static db.JdbcUtil.*"%>
<%
	//안드로이드에서 던지는 값 여기서 받음
	String User_Device_Id=request.getParameter("User_Device_Id");
	/* String User_QR_Code=request.getParameter("User_QR_Code");
	String User_Phone=request.getParameter("User_Phone"); */
	 
	
	int insertCount = 0;
	/* JSONArray arr = new JSONArray(); //JSON 배열에 캡슐화하는 느낌으로 안드로이드랑 통신 */
	JSONObject obj=new JSONObject();
try{
	sql="select * from qr_user where User_Device_Id=?";
	
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, User_Device_Id);
	
	rs = pstmt.executeQuery();
	
	
	if(rs.next()){
		
		
		String id=rs.getString("User_Device_Id");
		String code=rs.getString("User_QR_Code");
		String phone=rs.getString("User_Phone"); 
		
		obj.put("id",id);
		obj.put("code", code);
		obj.put("phone", phone);
		
	}else{
		obj.put("id","?");
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