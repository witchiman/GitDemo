package com.hui;

import java.io.IOException;
import java.util.Vector;

public class SocketManager {
	public static final SocketManager manager = new SocketManager();
	private Vector<ChatSocket> vector = new Vector<ChatSocket>();
	
	private SocketManager(){}
	
	public static SocketManager getSocketManager() {
		return manager;
	}
	
	public void add(ChatSocket cs) {
		vector.add(cs);
	}
	
	public void delete(ChatSocket cs) {
		vector.remove(cs);
	}
	
	public void publish(ChatSocket cs, String msg) {
		for(int i=0; i<vector.size();i++) {
			ChatSocket socket =vector.get(i); 
			if(!cs.equals(socket)) {
				try {
					socket.response(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
