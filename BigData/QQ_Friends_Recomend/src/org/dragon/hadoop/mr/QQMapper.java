package org.dragon.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class QQMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		 String lineValue = value.toString();
		 String[] words = lineValue.split("\t");
		 context.write(new Text(words[0]), new Text(words[1]));
		 context.write(new Text(words[1]), new Text(words[0]));
	}
	
}
