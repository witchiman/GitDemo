package org.dragon.hadoop.mr;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.dragon.hadoop.uti.DataWritable;

public class DataTotalMapReduce {
	//Mapper 
	static class DataTotalMapper extends Mapper<LongWritable, Text, Text, DataWritable> {
		private DataWritable dw = new DataWritable();
		private Text mapOutputKey = new Text();
		public void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException { 
			String lineValue = value.toString();
			
			//split
			String[] strs = lineValue.split("\t");
			//get data
			String phoneNum = strs[0];
			int upPackNum = Integer.valueOf(strs[1]);
			int downPackNum = Integer.valueOf(strs[2]);
			int upPayLoad = Integer.valueOf(strs[3]);
			int downPayLoad = Integer.valueOf(strs[4]);
			
			//set map output key/value
			dw.set(upPackNum, upPayLoad, downPackNum, downPayLoad); 
			mapOutputKey.set(phoneNum);
			context.write(mapOutputKey, dw);
		}
	}
	
	//Reducer
	static class DataTotalReducer extends Reducer<Text, DataWritable, Text, DataWritable> {
		private DataWritable dw = new DataWritable();
		
		@Override
		protected void reduce(Text key, Iterable<DataWritable> values,Context context) 
				throws IOException, InterruptedException {  
			int upPackNum = 0;
			int downPackNum = 0;
			int upPayLoad = 0;
			int downPayLoad = 0;
			
			//iterator
			for(DataWritable data : values) {
				upPackNum += data.getUpPackNum();
				downPackNum += data.getDownPackNum();
				upPayLoad += data.getUpPayLoad();
				downPayLoad += data.getDownPayLoad();
			}
			
			dw.set(upPackNum, upPayLoad, downPackNum, downPayLoad);
			context.write(key, dw);
					
		}
		
	}
	
	//Driver
	public int run (String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		//crreate and set job
		Job job = new Job(conf, DataTotalMapReduce.class.getSimpleName());
		job.setJarByClass(DataTotalMapReduce.class);
		
		//input
		Path inputDir = new Path(args[0]); 
		FileInputFormat.addInputPath(job, inputDir);
		
		//map
		job.setMapperClass(DataTotalMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DataWritable.class);
		
		//reduce
		job.setReducerClass(DataTotalReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DataWritable.class);
		
		//output
		Path outputDir = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, outputDir);
		
		//submit
		boolean isSuccess = job.waitForCompletion(true);
		
		//·µ»Ø×´Ì¬Âë
		return  isSuccess ? 0 : 1;
	}
	
	//run mapreduce
	public static void main(String[] args) throws Exception {
		args = new String[]{
				"hdfs://hadoop.dragon.org:9000/hdfs/input",
				"hdfs://hadoop.dragon.org:9000/hdfs/output"
		};
		int status = new DataTotalMapReduce().run(args);
		System.exit(status);
	}
}
