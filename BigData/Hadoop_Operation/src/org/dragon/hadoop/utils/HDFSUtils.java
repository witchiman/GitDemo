package org.dragon.hadoop.utils;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.junit.Test;


public class HDFSUtils {

	
	public static FileSystem getFileSystem() {
		
		FileSystem  hdfs =null;
		try{
			//获取配置文件信息
			Configuration conf = new Configuration();
			//获取文件系统
			hdfs = FileSystem.get(conf);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return hdfs;
		
	}
	 
}
