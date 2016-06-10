package com.hui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServerListener extends Thread{
	public static final int PORT = 6780;
	public static int  count = 1;
	@Override
	public void run() {
		 try {
			ServerSocket server = new ServerSocket(PORT);
			while(true) {
				Socket socket = server.accept();
				ChatSocket cs =  new ChatSocket(socket);
				cs.setSocketName("User"+(count++));
				cs.start();
				SocketManager.getSocketManager().add(cs);
				
				JOptionPane.showMessageDialog(null, "有新的客户端加入！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	
}
