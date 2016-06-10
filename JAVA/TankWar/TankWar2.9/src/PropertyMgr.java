import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
	
	static Properties props = new Properties();
	static {	
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("tank.properties"));
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
	}
	
	private PropertyMgr() {}; //谁也不能new PropertyMgr类
	public static String getProperty(String key) {
		
		return props.getProperty(key);
	}
}
