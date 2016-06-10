import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetClient {
	 
	private int udpPort;
	Socket s = null;
	TankClient tc = null;
	DatagramSocket ds = null;
	
	public NetClient(TankClient tc) {
		this.tc = tc;			 
		
	}
	
	public void connect(String IP, int port) {
		try {
			ds = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tc.myTank.id = id;
			if(id%2 == 0) tc.myTank.good = false;
			else tc.myTank.good =true;
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
		
		TankNewMsg msg = new TankNewMsg(tc.myTank);
		send(msg);
		new Thread(new UDPRecvThread()).start();
	}

	public void send(Msg msg) {
		msg.send(ds, "127.0.0.1", TankServer.UDP_PORT);		
	}
	
	private class UDPRecvThread implements Runnable {
		byte[] buf = new byte[1024];
		@Override
		public void run() {
			
			while(ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);					
System.out.println("a datagrampack has received from server!");
					parse(dp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		private void parse(DatagramPacket dp) {
			 ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0,dp.getLength());
			 DataInputStream dis = new DataInputStream (bais);
			 Msg  msg = null;
			 int msgType = 0;
			try {
				msgType = dis.readInt(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			 switch(msgType) {
			 case Msg.TANK_NEW_MSG:
				 msg = new TankNewMsg(NetClient.this.tc);
				 msg.parse(dis);
				 break;
			 case Msg.TAN_MOVE_MSG:
				 msg = new TankMoveMsg(NetClient.this.tc);
				 msg.parse(dis);
				 break;
			 case Msg.MISSILE_NEW_MSG:
				 msg = new MissileNewMsg(NetClient.this.tc);
				 msg.parse(dis);
				 break;
			 case Msg.TANK_DEAD_MSG:
				 msg = new TankDeadMsg(NetClient.this.tc);
				 msg.parse(dis);
				 break;
			 }
			
		}
	}

	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
}
