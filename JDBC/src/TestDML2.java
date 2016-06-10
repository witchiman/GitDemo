import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class TestDML2 {

	
	public static void main(String[] args) {
		 Connection conn = null;
		 Statement stmt = null;
		 int deptno = 0;
		 String dname = null;
		 String loc = null;
		 
		 if(args.length != 3) {
			 System.out.println("Parameter Error!Please input again!");
			 System.exit(-1);
		 }
		 try {
			 deptno = Integer.parseInt(args[0]);
		 }catch (NumberFormatException e) {
			 System.out.println("Parameter Error!You should input the type of number!");
			 System.exit(-1);
		 }
		 
		 dname = args[1];
		 loc = args[2];
		 
		 try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","system","Aa4637311");
			 stmt = conn.createStatement();
			 String sql = "insert into dept values (" + deptno+ ",'" + dname + "','" + loc +"')";
			 System.out.println(sql);
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
