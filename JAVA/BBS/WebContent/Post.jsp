<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*" %>
<% 
request.setCharacterEncoding("gbk");
String action =  request.getParameter("action");
if(action != null) {
	String title = request.getParameter("title");
	String cont = request.getParameter("cont");
	cont = cont.replaceAll("\n","<br>");
	
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
	Connection conn = DriverManager.getConnection(url);
	String sql = "insert into article values (null, 0, ?, ?, ?, now(), 0)";
	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	Statement stmt = conn.createStatement();
	
	conn.setAutoCommit(false);
	
	pstmt.setInt(1, -1);
	pstmt.setString(2, title);
	pstmt.setString(3, cont);	
	pstmt.executeUpdate();
 
	ResultSet rsKey = pstmt.getGeneratedKeys();
	rsKey.next();
	int key = rsKey.getInt(1);
	rsKey.close();
	stmt.executeUpdate("update article set rootid  =" + key + " where id ="+ key);	 
	
	conn.commit();
	conn.setAutoCommit(true);
	
	stmt.close();
	pstmt.close();	 
	conn.close();
	 
	response.sendRedirect("ShowArticleTree.jsp");
}

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title> </title>
</head>
<body>
 <body>
<form action="Post.jsp" method="post" >
	<input type="hidden" name="action" value="post">	
	<table>	
	<tr>
		<td><input type="text" name="title" size="50"></td>
	</tr>
	<tr>
		<td><textarea name="cont" cols="80" rows="12"></textarea></td>
	</tr>
	<tr>
		<td><input type="submit" value="Ìá½»"></td>
	</tr>
	</table>
</form>
</body>
</html>