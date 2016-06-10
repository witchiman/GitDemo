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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {
	//Mapper 代码
	/*KEYIN, VALUEIN, KEYOUT, VALUEOUT
	 * 分别为输入key类型、value类型和输出key类型、value类型
	 * */
	static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private Text word = new Text();
		private final static IntWritable one = new IntWritable(1);
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {  
			//获取每行数据的值
			String lineValue = value.toString();
			//进行分割
			StringTokenizer st = new StringTokenizer(lineValue);
			while(st.hasMoreTokens()) {
				//获取每个值
				String wordValue = st.nextToken();
				//设置map输出的key值	
				word.set(wordValue);
				//上下文输出map的key和value
				context.write(word, one);
			}
		}
		 
	}
	
	//Reducer 代码
	static class MyReducer extends Reducer<Text , IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException { 
			int sum  = 0;
			for(IntWritable value : values) {
				sum += value.get();					
			}
			//设置总次数
			result.set(sum);
			context.write(key, result);
		}
		
	}
	
	//Client 代码
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		args = new String[]{
				"hdfs://hadoop.dragon.org:9000/hdfs/input",
				"hdfs://hadoop.dragon.org:9000/hdfs/output"};
		Configuration conf = new Configuration();
		//避免警告
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if(otherArgs.length != 2) {
			System.err.println("Usage:WordCount <in> <out>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "wc");
		//1:设置Job运行的类
		job.setJarByClass(WordCount.class);
		
		//2：设置Mapper和Reducer类
		job.setMapperClass(MyMapper.class);
		//job.setCombinerClass(MyReducer.class);   //设置合并
		job.setReducerClass(MyReducer.class);
				
		//3:设置输入文件的目录和输出文件的目录 
		FileInputFormat.setInputPaths(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		//4：输出结果key和value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//5:提交Job,等待运行结果，并在Client显示运行信息
		boolean isSuccess = job.waitForCompletion(true);
		
		//结束程序
		System.exit(isSuccess ? 1 : 0);
	}
	 
}
