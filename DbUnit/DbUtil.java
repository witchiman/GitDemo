package com.hui.maven.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 

public class DbUtil {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/maven","root","root");
		} catch (SQLException e) {			 
			e.printStackTrace();
		}
		return conn;		
	}
	
	public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
		PreparedStatement ps = null;
		try {
			ps =  conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	public static void close(Connection conn) {
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void close(PreparedStatement ps) {
		if(ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
