package org.dragon.hadoop.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TestWritable implements WritableComparable<TestWritable> {
	private Text first;
	private Text second;
	
	public TestWritable() {
		
	}
	
	public TestWritable(Text first, Text second) {
		set(first, second);
	}
	
	public void set(Text first, Text second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		 first.readFields(arg0);
		 second.readFields(arg0);
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		first.write(arg0);
		second.write(arg0);
		
	}

	@Override
	public int compareTo(TestWritable o) {
		int cmp  = this.first.compareTo(o.getFirst());
		if(0 != cmp) return cmp; 
		return  this.second.compareTo(o.getSecond());
	}

	public Text getFirst() {
		return first;
	}

	public void setFirst(Text first) {
		this.first = first;
	}

	public Text getSecond() {
		return second;
	}

	public void setSecond(Text second) {
		this.second = second;
	}
	
	@Override
	public String toString() {
		return first + "\t" + second;
	}
	
	@Override
	public int hashCode() {
		return first.hashCode() * 163 + second.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof TestWritable) {
			TestWritable pair = (TestWritable)o;
			return first.equals(pair.getFirst()) && second.equals(pair.getSecond());
		}
		return false;  
	}

}
