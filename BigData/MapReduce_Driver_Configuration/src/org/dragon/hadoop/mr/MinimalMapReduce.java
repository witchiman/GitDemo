package org.dragon.hadoop.mr;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MinimalMapReduce {
	/*
	 * Mapper 
	 * 
	*/
	
	
	/*
	 * Reducer
	 * 	 * 
	*/
	
	/*
	 * Driver
	 * */
	
	public static void main(String[] args) throws Exception {
		args = new String[] {"hdfs://hadoop.dragon.org:9000/hdfs/input",
				"hdfs://hadoop.dragon.org:9000/hdfs/output"};
		
		Configuration conf = new Configuration();
		
		Job job = new Job(conf,MinimalMapReduce.class.getSimpleName());		
		job.setJarByClass(MinimalMapReduce.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		boolean isSuccess = job.waitForCompletion(true);
		System.exit(isSuccess ? 1:0);
		
	}
}
