package com.hui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class ChatSocket extends Thread{
	private Socket s;
	private String socketName;
	
	public ChatSocket(Socket s) {
		this.s = s;
	}
	
	public void response(String msg) throws IOException {
		try {
			 BufferedWriter bw = new BufferedWriter(
					 new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
			 bw.write(msg+"\n");
			 bw.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	@Override
	public void run() {
		 try {
			response("您已经连接到本服务器！\n");
			BufferedReader br = new BufferedReader(
					 new InputStreamReader(
							 s.getInputStream(),"UTF-8"));
			String line;
			while((line=br.readLine())!=null) {
				System.out.println(line);
				SocketManager.getSocketManager().publish(this, getSocketName()+": " +line +"\n");
			}
		 }catch(SocketException e) {
			e.printStackTrace();
			SocketManager.getSocketManager().delete(this);
			JOptionPane.showMessageDialog(null, "客户端已经断开连接");
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
	}

	public String getSocketName() {
		return socketName;
	}

	public void setSocketName(String socketName) {
		this.socketName = socketName;
	}
	
}