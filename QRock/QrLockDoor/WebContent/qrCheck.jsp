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
	String user_qr_code=request.getParameter("USER_QR_CODE");
	
	int insertCount = 0;
	
	JSONObject obj= null;
	JSONArray jArray = new JSONArray();//배열이 필요할때

try{
	sql="select * from qr_user where user_qr_code=?";
	
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, user_qr_code);
	
	rs = pstmt.executeQuery();
		
	while(rs.next()){
		obj = new JSONObject();//배열 내에 들어갈 json

		obj.put("user_device_id",rs.getString("user_device_id"));
		obj.put("user_qr_code",rs.getString("user_qr_code"));
		obj.put("user_phone",rs.getString("user_phone"));
		obj.put("user_password",rs.getString("user_password"));
		obj.put("user_admin",rs.getString("user_admin"));
		obj.put("user_name",rs.getString("user_name"));
		
		//String mac_add=rs.getString("mac_add");
		jArray.add(obj);
			
	}
	if(!rs.next())
		obj.put("MAC_ADD","?");

} catch (SQLException se) {
	out.println(se.getMessage());
} finally {
	
}
%>
<%=jArray.toString()%>