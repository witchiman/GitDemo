import java.io.IOException;
import java.net.*;

public class NetClient {
	
	public void connect(String IP, int port) {
		try {
			Socket s = new Socket(IP, port);
System.out.println("connect successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
