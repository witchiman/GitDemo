import java.io.*;
import java.net.*;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;	
	
	public static void main(String[] args) {
		new ChatServer().start();		
	}
	
	public void start () {
		try {
			ss = new ServerSocket(8888);
			started = true;
		}catch(BindException e) {
			System.out.println("The port has been used!");
			System.exit(-1);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		try {			
			while(started) {
				boolean bConnected = false;
				Socket s = ss.accept();
				Client c = new Client(s); //main是静态方法内不能直接new 动态类 要加 static或单独封装成一个方法
				new Thread(c).start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		
	}
	
	class Client implements Runnable { 
		private Socket s;
		private DataInputStream dis = null;
		private boolean bConnected = false;
		public Client(Socket s ) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				bConnected = true;
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void run() {
					
			try {
				while(bConnected) {
					String str = dis.readUTF();				
					System.out.println(str);
				}
			}catch (EOFException e) {			
				System.out.println("Client close!");
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(dis != null) dis.close();
					
				} catch (IOException e) {				
					e.printStackTrace();
				}
			}			
			
		}
		
	}
}
