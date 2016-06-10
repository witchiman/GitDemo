import java.sql.*;

public class ArticleTree {
	 
	public static void main(String[] args) {
		new ArticleTree().show();

	}
	
	public void show() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bbs?user=root&password=root");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from article where pid = 0");			
			while(rs.next()) {
				System.out.println(rs.getString("cont"));
				tree(conn, rs.getInt("id"), 1);
			}
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
				if(stmt != null) {
					stmt.close();
					stmt = null;
				}
				if(rs != null) {
					rs.close();
					rs = null;
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void tree(Connection conn, int id,int level) {
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer preStr = new StringBuffer("");
		for(int i=0; i<level; i++) {
			preStr.append("    ");
		}
		try {				 
			stmt = conn.createStatement();
			String sql ="select * from article where pid =" + id;
			rs = stmt.executeQuery(sql);			
			while(rs.next()) {
				System.out.println(preStr + rs.getString("cont"));
				if(rs.getInt("isLeaf") != 0) {
					tree(conn, rs.getInt("id"), level+1);
				}
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {				
				if(stmt != null) {
					stmt.close();
					stmt = null;
				}
				if(rs != null) {
					rs.close();
					rs = null;
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
}
