package org.dragon.hadoop.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobRun {
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		try {
			Job job = new Job(conf,"qq");
			job.setJarByClass(JobRun.class);
			job.setMapperClass(QQMapper.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setReducerClass(QQReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			FileInputFormat.addInputPath(job, new Path("hdfs://hadoop.dragon.org:9000/hdfs/input"));
			FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop.dragon.org:9000/hdfs/output"));
			System.exit(job.waitForCompletion(true) ? 1 : 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
