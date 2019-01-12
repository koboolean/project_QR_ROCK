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
	String mac_qr_code=request.getParameter("USER_QR_CODE");

	int insertCount = 0;
	
	JSONObject obj=new JSONObject();
try{
	sql="select * from qr_mac where mac_qr_code=?";
	
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, mac_qr_code);
	
	rs = pstmt.executeQuery();
		
	if(rs.next()){
		
		String mac_add=rs.getString("mac_add");

		obj.put("MAC_ADD", mac_add);
		
	}else{
		obj.put("MAC_ADD","?");	
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