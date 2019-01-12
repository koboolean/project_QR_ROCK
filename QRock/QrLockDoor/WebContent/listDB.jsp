<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@ include file="OracleConnection.jsp"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="static db.JdbcUtil.*"%>
<%@ page import="vo.User" %>
<%
	//QR주소를 사용하고 있는 사람들의 리스트에 대해서 안드로이드아이디와 이름에 따른 리스트작성
	
	String user_QR_Code = request.getParameter("user_QR_Code");
	
	String user_Name = "1";
	String user_Phone = "1";
	String user_admin="1";
	JSONObject obj = new JSONObject();
	JSONArray infoarry = new JSONArray();
	List<User> user_List = new ArrayList<User>();
	
try{
	
	sql ="select user_Name, user_Phone, user_admin from qr_user where user_QR_Code=?";
	
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, user_QR_Code);
	
	rs = pstmt.executeQuery();
	
	if (rs.next()){
		User user = new User();
		
		user.setUser_Name(rs.getString("user_Name"));
		user.setUser_Phone(rs.getString("user_Phone"));
		user.setUser_admin(rs.getInt("user_admin"));
		
		user_List.add(user);
		
		obj.put("user_Name",user_Name);
		obj.put("user_Phone", user_Phone);
		obj.put("user_admin",user_admin);
		
	}else{
		
	}
		
}catch (SQLException se) {
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