import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.*;
import java.sql.*;

public class ShowRsUseBean extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("gb2312");
		PrintWriter out = resp.getWriter();
		
		out.println("<table border=1>");
		out.println("<tr><td>Conten:</td></tr>");
		
		Connection conn = DB.getConn();
		Statement stmt = DB.getStatement(conn);
		ResultSet rs = DB.getResultSet(stmt, "select * from article");
		
		try {
			while(rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("title") + "</td>");
				out.println("</td>");
			}
			out.println("</table>");
		}catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DB.close(stmt);
			DB.close(rs);
			DB.close(conn);
		}
	}
	
}
