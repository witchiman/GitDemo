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
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.dragon.hadoop.mr.util.TopWritable;

public class TopKeysMapReduce4_MultiMap {
	public static final int KEY = 3; 
	
	//Mapper
	static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		private Text outputKey = new Text(); 
		private LongWritable outputValue = new LongWritable();
		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {  
			String lineValue = value.toString();
			String[] strs = lineValue.split("\t");
			long tempValue = Long.valueOf(strs[1]);   
			
			outputKey.set(strs[0]);
			outputValue.set(tempValue);
			context.write(outputKey, outputValue);
		}

		@Override
		public void cleanup(Context context)
				throws IOException, InterruptedException {  
			super.cleanup(context);
		}

		@Override
		public void setup(Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException { 
			
		}
				
	}
	
	//Reduce Code
	static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
		private TreeSet<TopWritable> topSet = new TreeSet<TopWritable>(new Comparator<TopWritable>(){

				@Override
				public int compare(TopWritable o1, TopWritable o2) {
					return o1.getCount().compareTo(o2.getCount());
				} 
			}
		); 

		@Override
		public void cleanup(Reducer<Text, LongWritable, Text, LongWritable>.Context context)
				throws IOException, InterruptedException { 
			for(TopWritable top : topSet) {
				context.write(new Text(top.getWord()), new LongWritable(top.getCount()));
			}
		}

		@Override
		public void reduce(Text key, Iterable<LongWritable> values,
				Context context) throws IOException, InterruptedException {  
			long count = 0L;
			for(LongWritable value : values) {
				count += value.get();
			}
			topSet.add(new TopWritable(key.toString(), count));
			
			//compare
			if(topSet.size() > KEY) {
				topSet.remove(topSet.first());
			}
		}

		@Override
		public void setup(Reducer<Text, LongWritable, Text, LongWritable>.Context context)
				throws IOException, InterruptedException { 
			
		}
		
	}
	
	//Driver Code
	public int run(String[] args) throws Exception{		
		Configuration conf = new Configuration();		
		Job job = new Job(conf, TopKeysMapReduce4_MultiMap.class.getSimpleName());
		//1:设置Job运行的类
		job.setJarByClass(TopKeysMapReduce4_MultiMap.class);
		
		//2：设置Map 
		job.setMapperClass(MyMapper.class); 
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		 
		//设置Reduce
		 job.setReducerClass(MyReducer.class);
		 job.setOutputKeyClass(Text.class);
		 job.setOutputValueClass(LongWritable.class);
		
				
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
			 int status = new TopKeysMapReduce4_MultiMap().run(args);
			 System.exit(status);
		}
}

