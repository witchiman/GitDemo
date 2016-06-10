package org.dragon.hadoop.mr;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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
	public static class ModuleMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private Text word = new Text();
		private final static IntWritable count = new IntWritable(1);

		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			super.setup(context);
		}
		
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {  
			String lineValue = value.toString(); 
			StringTokenizer st = new StringTokenizer(lineValue);
			
			while(st.hasMoreTokens()) { 
				String wordValue = st.nextToken(); 
				word.set(wordValue); 
				context.write(word, count);
			}
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
	public static class ModuleReducer extends Reducer<Text, IntWritable, Text,IntWritable>{
		private IntWritable result = new IntWritable();
		
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException { 
			super.setup(context);
		}
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum  = 0;
			for(IntWritable value : values) {
				sum += value.get();					
			} 
			result.set(sum);
			context.write(key, result);
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
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class); 
			job.setPartitionerClass(HashPartitioner.class); 
			job.setNumReduceTasks(1); 
			
			/*job.setSortComparatorClass(LongWritable.Comparator.class); 
			job.setGroupingComparatorClass(LongWritable.Comparator.class); */
			job.setCombinerClass(ModuleReducer.class);
			 
			job.setReducerClass(ModuleReducer.class); 
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			 
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
		args = new String[] {"hdfs://hadoop.dragon.org:9000/hdfs/input",
		"hdfs://hadoop.dragon.org:9000/hdfs/output"};
		
		int status = ToolRunner.run(new ModuleMR(), args);
		System.exit(status);
	}
}
