
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

 
<%
int pageNo;
int pageSize = 3;
String strPageNo = request.getParameter("pageNo");

if(strPageNo!=null && strPageNo.equals("")) {
	pageNo = 1;
}else {
	try {
		pageNo = Integer.parseInt(strPageNo);		
	}catch (NumberFormatException e ) {
		pageNo = 1;
	}
	if(pageNo <= 0) {
		pageNo=1;		
	}
} 

Class.forName("com.mysql.jdbc.Driver");
String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
Connection conn = DriverManager.getConnection(url);

Statement stmtCount = conn.createStatement();
ResultSet rsCount = stmtCount.executeQuery("select count(*) from article where pid = 0");
rsCount.next();
int totalRecords = rsCount.getInt(1);
int totalPages = totalRecords%pageSize == 0 ? totalRecords/pageSize :totalRecords/pageSize +1;
if(pageNo > totalPages) {
	pageNo = totalPages;
}
int startPos = (pageNo-1) * pageSize;

Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("select * from article where pid=0 order by pdate desc limit " + startPos +
								"," + pageSize);


%>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<a href="Post.jsp">发表新贴</a>
<table border="1">
<% while(rs.next()) { %>
  <tr><td><%= rs.getString("title") %></td></tr>
<% }
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
</table>
共<%= totalPages %>页 第<%= pageNo %>页
<a href="ShowArticleFlag.jsp?pageNo=<%=pageNo-1 %>" style="text-decoration:none"> < </a>
<a href="ShowArticleFlag.jsp?pageNo=<%=pageNo+1 %>" style="text-decoration:none"> > </a>
<form name="form1" action="ShowArticleFlag.jsp" >
	<select name="pageNo" onchange="document.form1.submit()">
		<%for(int i=1; i<=totalPages; i++) {%>
			<option value=<%= i %> <%= (pageNo==i) ? "selected" : "" %>>第<%= i %>页</option>>
		<% } %>
	</select>
</form>

<form name="form2" action="ShowArticleFlag.jsp">
	<input type="text" size="4" name="pageNo" value="<%= pageNo %>" />
	<input type="submit" value="go">
</form>
</body>
</html>