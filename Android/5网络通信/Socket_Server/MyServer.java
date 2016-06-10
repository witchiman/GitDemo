package com.hui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class MyServer {
	public static final int PORT=6780;
	
	public static void main(String[] args) {
		 new ServerListener().start();
	}
}
