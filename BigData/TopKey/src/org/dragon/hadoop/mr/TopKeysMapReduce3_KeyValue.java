package org.dragon.hadoop.mr;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.dragon.hadoop.mr.util.TopWritable;

public class TopKeysMapReduce3_KeyValue {
	//Mapper
	static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		public static final int KEY = 3; 
		private TreeSet<TopWritable> topSet = new TreeSet<TopWritable>(new Comparator<TopWritable>(){

				@Override
				public int compare(TopWritable o1, TopWritable o2) {
					return o1.getCount().compareTo(o2.getCount());
				} 
			}
		); 
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {  
			String lineValue = value.toString();
			String[] strs = lineValue.split("\t");
			long tempValue = Long.valueOf(strs[1]);  
			
			topSet.add(new TopWritable(strs[0],tempValue));
			
			if(topSet.size() > KEY) { 
				topSet.remove(topSet.first());
			}
		}

		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {  
			for(TopWritable top : topSet) { 
				context.write(new Text(top.getWord()), new LongWritable(top.getCount()));
			}
		}

		@Override
		protected void setup(Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException { 
			
		}
				
	}
	
	//Driver Code
	public int run(String[] args) throws Exception{		
		Configuration conf = new Configuration();		
		Job job = new Job(conf, TopKeysMapReduce3_KeyValue.class.getSimpleName());
		//1:设置Job运行的类
		job.setJarByClass(TopKeysMapReduce3_KeyValue.class);
		
		//2：设置Mapper和Reducer类
		job.setMapperClass(MyMapper.class); 
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		 
		//set no reduce task 
		job.setNumReduceTasks(0);
				
		//3:设置输入文件的目录和输出文件的目录 
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//4：输出结果key和value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//5:提交Job,等待运行结果，并在Client显示运行信息
		boolean isSuccess = job.waitForCompletion(true);
		
		//返回状态码
		return isSuccess ? 1 : 0;
	}
		 public static void main(String[] args) throws Exception {
			 args = new String[]{
						"hdfs://hadoop.dragon.org:9000/hdfs/output",
						"hdfs://hadoop.dragon.org:9000/hdfs/topkeyput"};
			 int status = new TopKeysMapReduce3_KeyValue().run(args);
			 System.exit(status);
		}
}

