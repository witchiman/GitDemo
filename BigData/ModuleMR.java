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
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ModuleMR extends Configuration implements Tool	{

	@Override
	public Configuration getConf() { 
		return null;
	}

	@Override
	public void setConf(Configuration conf) { 
		
	}
	
	/*
	 * Mapper 
	 * 
	*/
	public static class ModuleMapper extends Mapper<LongWritable, Text, LongWritable, Text>{

		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			super.setup(context);
		}
		
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException { 
			super.map(key, value, context);
		}

		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException { 
			super.cleanup(context);
		}
 
	}
	
	/*
	 * Reducer
	 * 	 * 
	*/
	public static class ModuleReducer extends Reducer<LongWritable, Text, LongWritable, Text>{

		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException { 
			super.setup(context);
		}
		
		@Override
		protected void reduce(LongWritable key, Iterable<Text> values,
				Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.reduce(key, values, context);
		}

		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException { 
			super.cleanup(context);
		}

	}
	/*
	 * Driver
	 * */
	@Override
	public int run(String[] args) throws Exception { 
			Configuration conf = new Configuration(); 		
			Job job = parseInputAndOutput(this,conf, args); 
			
			job.setJarByClass(ModuleMR.class);	 
			job.setInputFormatClass(TextInputFormat.class); 
			
			job.setMapperClass(ModuleMapper.class); 
			job.setMapOutputKeyClass(LongWritable.class);
			job.setMapOutputValueClass(Text.class); 
			job.setPartitionerClass(HashPartitioner.class); 
			job.setNumReduceTasks(1); 
			
			/*job.setSortComparatorClass(LongWritable.Comparator.class); 
			job.setGroupingComparatorClass(LongWritable.Comparator.class); 
			job.setCombinerClass(null);*/
			 
			job.setReducerClass(ModuleReducer.class); 
			job.setOutputKeyClass(LongWritable.class);
			job.setOutputValueClass(Text.class);
			 
			boolean isSuccess = job.waitForCompletion(true);
			return isSuccess ? 1: 0; 
	}
	
	public Job parseInputAndOutput(Tool tool, Configuration conf, String[] args) throws IOException {
		if(args.length != 2) {
			System.err.printf("Usage:%s [generic options] <input> <output>\n",
					tool.getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
		}
		 
		Job job = new Job(conf, tool.getClass().getSimpleName());
		FileInputFormat.addInputPath(job, new Path(args[0])); 
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return job;
	}
	
	public static void main(String[] args) throws Exception {
		int status = ToolRunner.run(new ModuleMR(), args);
		System.exit(status);
	}
}
