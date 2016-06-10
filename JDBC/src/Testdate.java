import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Testdate {
 
	public static void main(String[] args) throws Exception{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://127.0.0.1:3306/bbs?user=root&password=root");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from article ");
		while(rs.next()) {
			//Date d = rs.getDate("pdate"); //Date类型只能获取日期不能获取数据库时间，即没有时、分、秒
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");			
			//System.out.println(sdf.format(d));
			
			/*Calendar c = Calendar.getInstance();
			System.out.print(c.get(Calendar.MONTH)+1 + "-"); //Calendar.MONTH的值是从0~11，所以记得加上1			 
			System.out.println(c.get(Calendar.DAY_OF_YEAR));*/
			
			Timestamp ts = rs.getTimestamp("pdate");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //参数缺省时，为完整时间格式
			System.out.println(sdf.format(ts));
		 	 
			
		}
		rs.close();
		stmt.close();
		conn.close();

	}

}
