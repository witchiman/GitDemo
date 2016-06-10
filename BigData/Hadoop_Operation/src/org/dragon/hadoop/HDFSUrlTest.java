package org.dragon.hadoop;

import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class HDFSUrlTest {
	
	//让JAVA程序识别HDFS的URL
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	
	//查看文件内容
	@Test
	public void testRead() throws Exception{
		InputStream in = null;
		//文件路径
		String fileUrl = "hdfs://hadoop.dragon.org:9000/hdfs/01.data";
		try {
			//获取文件输入流
			in = new URL(fileUrl).openStream();
			//将文件内容读取出来输出到控制台
			IOUtils.copyBytes(in, System.out, 4096, false);
		} finally {
			IOUtils.closeStream(in);
		}
	}
}
