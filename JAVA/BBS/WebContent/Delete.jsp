
<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page import="java.sql.*"%>

<%! 
	
private void del(Connection conn, int id) {		
	 
     Statement stmt = null;
     ResultSet rs = null;
	try {
		stmt = conn.createStatement();
		String sql = "select * from article where pid=" + id;
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			 del(conn, rs.getInt("id"));
		}
		stmt.executeUpdate("delete from article where id =" + id);
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
%>

<%
String admin = (String)session.getAttribute("admin");
if(admin == null || !admin.equals("true")) {
	out.println("Hi,buddy,what're you dong here?");
	return;
}
%>

<% 
int id = Integer.parseInt(request.getParameter("id"));
int pid = Integer.parseInt(request.getParameter("pid"));

Class.forName("com.mysql.jdbc.Driver");
String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
Connection conn = DriverManager.getConnection(url);

conn.setAutoCommit(false);

del(conn, id);
Statement stmt = conn.createStatement();
ResultSet rs =  stmt.executeQuery("select count(*) from article where pid=" + pid);
rs.next();
int count = rs.getInt(1);
rs.close();

if(count <=0 ) {
	Statement stmtUpdate = conn.createStatement();
	stmtUpdate.executeUpdate("update article set isLeaf=0 where id=" + pid);
	stmtUpdate.close();
	
}
conn.commit();
conn.setAutoCommit(true);

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

response.sendRedirect("ShowArticleTree.jsp");
%> 
 