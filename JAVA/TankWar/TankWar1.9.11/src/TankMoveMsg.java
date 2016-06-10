import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMsg implements Msg {
	
	int id;
	int x,y;
	Direction dir;
	Direction ptDir;
	int msgType = Msg.TAN_MOVE_MSG;
	TankClient tc = null;
	
	public TankMoveMsg(int id, int x, int y,Direction dir, Direction ptDir) {
		this.id = id;
		this.dir = dir;
		this.ptDir = ptDir;
		this.x = x;
		this.y = y;
	}
	
	public TankMoveMsg(TankClient tc) {
		this.tc = tc;
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		byte[] buf = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try {
			dos.writeInt(msgType);			
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(ptDir.ordinal());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
		try {
			ds.send(dp);
System.out.println("a datagrampacket has sent!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void parse(DataInputStream dis) {
		try {
			 
			 int id = dis.readInt();
			 if(tc.myTank.id == id) return;
			 int x = dis.readInt();
			 int y = dis.readInt();			 
			 Direction dir = Direction.values()[dis.readInt()];
			 Direction ptDir = Direction.values()[dis.readInt()];
			 boolean exists = false;
			 for(int i=0; i<tc.tanks.size(); i++) {
				 Tank t = tc.tanks.get(i);
				 if(t.id == id) {
					 t.dir = dir;
					 t.ptDir = ptDir;
					 exists = true;
					 break;
				 }
			 }
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		 
	}

}
