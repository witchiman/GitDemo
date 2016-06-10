import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMsg implements Msg {
	Tank tank = null;
	TankClient tc = null;
	int msgType = Msg.TANK_NEW_MSG;
	
	public TankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	public TankNewMsg(TankClient tc) {
		this.tc = tc;		
	};
	
	public void send(DatagramSocket ds, String IP,int udpPort) {
		byte[] buf = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try {
			dos.writeInt(msgType);
			dos.writeInt(tank.id);
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.good);
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
			 boolean good = dis.readBoolean();
//System.out.println("id:" + id +",x:" + x + ",y:" + y + ",dir:" + dir + ",good:" + good);
			 boolean exists = false;
			 for(int i=0; i<tc.tanks.size(); i++) {
				 Tank t = tc.tanks.get(i);
				 if(id == t.id) {
					 exists = true;
				 }
			 }
			 if(!exists) { 
				 TankNewMsg tnMsg = new TankNewMsg(tc.myTank);
				 tc.nc.send(tnMsg);
				 Tank t = new Tank(x, y, good, dir, tc);
				 t.id = id;
				 tc.tanks.add(t);
			 }
		} catch (IOException e) {			
			e.printStackTrace();
		}
		 
		
	}
		
}
