import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetClient {
	private static int UDP_START_PORT = 2223;
	private int udpPort;
	Socket s = null;
	TankClient tc = null;
	
	public NetClient(TankClient tc) {
		this.tc = tc;		
		udpPort = this.UDP_START_PORT ++;
	}
	
	public void connect(String IP, int port) {
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tc.myTank.id = id;
			dos.writeInt(udpPort);
			
System.out.println("connect successfully!Get the ID:" + id);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(s != null) {
				try {
					s.close();
					s = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
