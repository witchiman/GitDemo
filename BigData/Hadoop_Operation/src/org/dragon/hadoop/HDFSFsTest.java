package org.dragon.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.IOUtils;
import org.dragon.hadoop.utils.HDFSUtils;
import org.junit.BeforeClass;
import org.junit.Test;
/*
 *HDFS JAVA API 需要配置文件才能连接
 * */
public class HDFSFsTest {
	 private static FileSystem hdfs;
	 @BeforeClass
	 public static void init() {
		 hdfs = HDFSUtils.getFileSystem();
	 }
	//读取文件内容
	@Test
	public void testReadFile() throws IOException { 
		Path path = new Path("/hdfs/data/02.data");
		//打开文件输入流
		FSDataInputStream inStream = hdfs.open(path);
		//读取文件到控制台显示
		IOUtils.copyBytes(inStream, System.out,4096, false);
		//关闭流
		IOUtils.closeStream(inStream); 
		
	}
	
	//查看目录
	@Test
	public void testList() 	throws Exception { 
		Path path = new Path("/hdfs");
		FileStatus[] fileStatus = hdfs.listStatus(path);
		for(FileStatus fs : fileStatus) {
			Path p = fs.getPath();
			String info = fs.isDir()?"目录":"文件";
			System.out.println(info + ":" + p);
		}
	}
	
		//创建目录
		@Test
		public void testCreateDirectory() throws IOException { 
			Path path = new Path("/hdfs/data/dir");
			boolean isSuccess = hdfs.mkdirs(path);
			String info = isSuccess ? "成功":"失败";
			System.out.println("创建目录" + path + info);
		}
		
		//上传文件
		@Test
		public void testPut() throws IOException {
			//本地文件
			Path srcPath = new Path("d:/SQL/bbs.sql");
			//HDFS上传路径
			Path dstPath = new Path("/hdfs/data");
			hdfs.copyFromLocalFile(srcPath, dstPath);
			
		}
		
		//创建HDFS文件并写入内容
		@Test
		public void testCreateFile() throws IOException {
			Path path = new Path("/hdfs/data/02.data");
			FSDataOutputStream fs = hdfs.create(path);
			//通过输出流写入数据
			fs.writeUTF("hello this is a test from windows");
			fs.close();
		}
		
		//对HDFS 上文件进行重命名
		@Test
		public void testRename() throws IOException {
			Path srcPath = new Path("/hdfs/data/02.data");
			Path dstPath = new Path("/hdfs/data/02.data.bak");
			boolean flag = hdfs.rename(srcPath, dstPath);
			System.out.println(flag);
		}
		
		//删除文件
		@Test
		public void testDeleteFile() throws IOException {
			Path dstPath = new Path("/hdfs/data/02.data");
			boolean flag = false;
			//第二个参数设定为true时，使用递归删除目录及其目录下所有文件
			flag = hdfs.delete(dstPath,false);
			System.out.println(flag);
		}
		
		//查找某个文件在集群在位置
		@Test
		public void testLocation() throws IOException {
			Path dstPath = new Path("/hdfs/hadoop-2.7.1.tar.gz");
			FileStatus fs = hdfs.getFileStatus(dstPath);
			BlockLocation[] bls = hdfs.getFileBlockLocations(fs, 0, fs.getLen());
			for(BlockLocation bs : bls) {
				String[] hosts = bs.getHosts();
				for(String host : hosts) {
					System.out.print(host+"|");
				}
			}
		}
		
		//获取集群所有节点的信息
		@Test
		public void testCluster() throws IOException {
			DistributedFileSystem dfs = (DistributedFileSystem) hdfs;
			DatanodeInfo[] infos =  dfs.getDataNodeStats();
			for(DatanodeInfo info : infos) {
				System.out.println(info.getHostName());
			}
		}

}
