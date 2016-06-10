import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileNewMsg implements Msg {
	
	TankClient tc;
	Missile m;
	int msgType = Msg.MISSILE_NEW_MSG;
	int tankId;
	
	public MissileNewMsg(TankClient tc) {
		this.tc = tc;
	}
	
	public MissileNewMsg(Missile m) {
		this.m = m;
	}
	
	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		byte[] buf = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try {
			dos.writeInt(msgType);	
			dos.writeInt(tankId);
			dos.writeInt(m.x);
			dos.writeInt(m.y);
			dos.writeInt(m.dir.ordinal());
			dos.writeBoolean(m.good);
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

	@Override
	public void parse(DataInputStream dis) {
		 try {			 
			 
			 int tankId = dis.readInt();
			 if (tankId == tc.myTank.id) return;
 			 int x = dis.readInt();
			 int y = dis.readInt();
			 Direction dir = Direction.values()[dis.readInt()];
			 boolean good = dis.readBoolean();
			 Missile m = new Missile(tankId, x, y, good, dir, tc);
			 tc.missiles.add(m);
			 
		} catch (IOException e) {			
			e.printStackTrace();
		}

	}

}
