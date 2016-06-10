package org.dragon.hadoop.mr;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner; 

public class DefaultMapReduce {
	
	public static void main(String[] args) throws Exception {
		args = new String[] {"hdfs://hadoop.dragon.org:9000/hdfs/input",
		"hdfs://hadoop.dragon.org:9000/hdfs/output"};
		
		//sttep 1: conf
		Configuration conf = new Configuration(); 
		
		//step 2: create job
		Job job = new Job(conf, DefaultMapReduce.class.getSimpleName());
		
		//step 1: set job
		//1 set run jar class
		job.setJarByClass(DefaultMapReduce.class);		
		//2 set inputformat
		job.setInputFormatClass(TextInputFormat.class);		
		//3 set input path
		FileInputFormat.addInputPath(job, new Path(args[0]));
		//4 set mapper
		job.setMapperClass(Mapper.class);
		//5 set map output key/value
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
		//6 set partitioner class
		job.setPartitionerClass(HashPartitioner.class);
		//7 set reduce number
		job.setNumReduceTasks(1);
		//8 set sort comparator class
		job.setSortComparatorClass(LongWritable.Comparator.class);
		//9 set group comparator class
		job.setGroupingComparatorClass(LongWritable.Comparator.class);		
		//10 set combiner class，使用默认配置时如果设置此项，可能出错 
		//job.setCombinerClass(null);
		
		//11 set reducer class
		job.setReducerClass(Reducer.class);
		//12 set output key/value
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		//13 set job output path
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//step 4: commit job
		boolean isSuccess = job.waitForCompletion(true);
		
		//step 5: exit
		System.exit(isSuccess ? 1 : 0);
		
	}
	
}
