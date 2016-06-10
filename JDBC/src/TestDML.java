import java.sql.*;

public class TestDML {

	public static void main(String[] args) {
		 Connection conn = null;
		 Statement stmt = null;
		 try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","system","Aa4637311");
			 stmt = conn.createStatement();
			 String sql = "insert into dept values(98,'GAME','BJ' )";
			 stmt.executeUpdate(sql);
			 
		 }catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 }catch (SQLException e) {
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
				 
			 }catch (SQLException e) {
				 e.printStackTrace();
			 }
		 }

	}

}
