package org.dragon.hadoop.mr;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class QQReducer extends Reducer<Text, Text, Text, Text>{

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		 Set<String> set = new HashSet<String>();
		 for(Text value : values) {
			 set.add(value.toString());
		 }
		 if(set.size()>1) {
			 for(Iterator i=set.iterator();i.hasNext();) {
				 String name = (String) i.next();
				 for(Iterator j=set.iterator();j.hasNext();) {
					 String otherName = (String) j.next();
					 if(!name.equals(otherName)) {
						 context.write(new Text(name), new Text(otherName));
					 }
				 }
			 }
		 }
	}
	
}
