import java.sql.*;
public class TestJDBC {


	public static void main(String[] args) {
		 Connection conn = null;
		 Statement stmt = null;
		 ResultSet  rs = null;		 
		try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			// new oracle.jdbc.driver.OracleDriver();
			 conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","system","Aa4637311" );
			 stmt = conn.createStatement();
			 rs = stmt.executeQuery("select * from dept");
			 while(rs.next()) {
				 System.out.println(rs.getString("deptno"));
				 System.out.println(rs.getInt("deptno"));
				 
			 }
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try { 
				if(rs != null) {
					rs.close();
					rs = null;
				}
				if(rs !=null) {
					stmt.close();
					stmt = null;
				}
				if(rs !=null) {
					conn.close();
					rs = null;
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}	 
	}

}
