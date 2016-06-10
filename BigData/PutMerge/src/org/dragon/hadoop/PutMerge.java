package org.dragon.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/*
 *在向HDFS上传文件 同时进行合并 
 * 
*/
public class PutMerge {
	//复制上传文件并将文件合并 
	public static void put(String localDir, String HDFSFile) {
		Configuration conf = new Configuration();
		Path localPath = new Path(localDir);
		Path HDFSPath = new Path(HDFSFile);
		try {
			FileSystem localFs = FileSystem.getLocal(conf);  //获取本地文件系统			
			FileSystem hdfs = FileSystem.get(conf);			//获取本地文件系统中的所有文件
			FileStatus[] statuses = localFs.listStatus(localPath);
			FSDataOutputStream fsos = hdfs.create(HDFSPath);     //打开HDFS上的输出流
			
			for(FileStatus status : statuses) {			
				Path path = status.getPath();                  //获取文件路径
				System.out.println("文件为：" + path.getName());				
				FSDataInputStream fsis = localFs.open(path);    //打开文件输入流
				byte[] buffer = new byte[1024];        //开始流的读写操作
				int len = 0;
				while((len = fsis.read(buffer)) > 0) {
					fsos.write(buffer, 0, len);
				}
				fsis.close();                         
			}
			fsos.close();							//所有文件操作完成后，关闭HDFS的输出流
		}catch ( Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static void main(String[] args) {
		String localDir = "d:/merge";
		String hdfsFile = "/hdfs/merge.data";
		put(localDir, hdfsFile);
	}
}
