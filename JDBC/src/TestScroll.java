import java.sql.*;

public class TestScroll {

	 
	public static void main(String[] args) {
		try {
			new oracle.jdbc.driver.OracleDriver();
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			Connection conn = DriverManager
					.getConnection(url, "system", "Aa4637311");
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt
					.executeQuery("select * from dept order by deptno");
			rs.next();
			System.out.println(rs.getInt(1)); //在这里相当于re.getInt("deptno")
			rs.last();
			System.out.println(rs.getString(1));
			System.out.println(rs.isLast());
			System.out.println(rs.isAfterLast());
			System.out.println(rs.getRow()); //获得行号
			rs.previous();
			System.out.println(rs.getString(1));
			rs.absolute(6);//直接定位到第六行上
			System.out.println(rs.getString(1));
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
