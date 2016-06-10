<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*" %>
<%
request.setCharacterEncoding("GBK"); 
int id = Integer.parseInt(request.getParameter("id"));
int rootid = Integer.parseInt(request.getParameter("rootid"));
String title = request.getParameter("title");
String cont = request.getParameter("cont");

if(title == null) {
	out.println("Error!Please input again.");
	return;
}
title = title.trim();
if(cont == "") {
	out.println("cont could not be empty!");
	return;
}
cont  = cont.trim();
if(cont == "") {
	out.println("cont could not be empty!");
	return;
}
cont = cont.replaceAll("\n","<br>");

Class.forName("com.mysql.jdbc.Driver");
String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
Connection conn = DriverManager.getConnection(url);
String sql = "insert into article values (null, ?, ?, ?, ?, now(), 0)";
PreparedStatement pstmt = conn.prepareStatement(sql);
Statement stmt = conn.createStatement();
conn.setAutoCommit(false);

pstmt.setInt(1, id);
pstmt.setInt(2, rootid);
pstmt.setString(3, title);
pstmt.setString(4, cont);
pstmt.executeUpdate();
stmt.executeUpdate("update article set isLeaf = 1 where id=" + id);
conn.commit();
conn.setAutoCommit(true);

pstmt.close();
stmt.close();
conn.close();

response.sendRedirect("ShowArticleTree.jsp");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title> </title>
</head>
<body>
 ºß
</body>
</html>