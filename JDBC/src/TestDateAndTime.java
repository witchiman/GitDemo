import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class TestDateAndTime {
 
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());//自1970年零时零分零秒伊始到现在过了多少毫秒
		
		Date d = new Date();		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(d) );
		
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.YEAR));
		
		String s = "2012-12-30 08:22:31.12";
		Timestamp ts = Timestamp.valueOf(s);
		System.out.println(ts);
		c.setTime(ts);  //ts继承date就是一个date类型，用setTime传入一个date类型
		System.out.println(c.get(Calendar.YEAR));
		
		Calendar cJapan = new GregorianCalendar(TimeZone.getTimeZone("Japan")); //获取小日本儿的当地时间
		System.out.println(cJapan.get(Calendar.HOUR_OF_DAY));
		
		/*for(String str : TimeZone.getAvailableIDs()) {
			System.out.println(str); //获取TimeZone里的所有ID
		}
		*/
		
	}

}
