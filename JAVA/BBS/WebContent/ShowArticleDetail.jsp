<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*;" %>
<%
String strId = request.getParameter("id"); 
int id = Integer.parseInt(strId);
Class.forName("com.mysql.jdbc.Driver");
String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
Connection conn = DriverManager.getConnection(url);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("select * from article where id=" + id);
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<table border=1>
<%if(rs.next()) { %>
		<tr><td>ID</td><td><%=rs.getInt("id") %></td></tr>
		<tr><td>TITLE</td><td><%=rs.getString("title") %></td></tr>
		<tr><td>CONTENT</td><td><%=rs.getString("cont") %></td></tr>
<%} %>
	</table>
<a href="Reply.jsp?id=<%= rs.getInt("id") %>&rootid=<%=rs.getString("rootid") %>">回复</a>
</body>

<%
try {
if(rs != null) {
	rs.close();
	rs = null;
}
if(stmt != null) {
	stmt.close();
	stmt = null;
}
if(conn != null) {
	conn.close();
	conn = null;
}
}catch (SQLException e) {
e.printStackTrace();
}

%>
</html>