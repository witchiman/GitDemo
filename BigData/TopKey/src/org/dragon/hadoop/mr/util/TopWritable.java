package org.dragon.hadoop.mr.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class TopWritable implements WritableComparable<TopWritable>{
	private String word;
	private Long count;
	
	public TopWritable() {}
	
	public TopWritable(String word, Long count) {
		this.set(word, count);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		this.word = in.readUTF();
		this.count = in.readLong();
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(word);
		out.writeLong(count);
		
	}

	@Override
	public int compareTo(TopWritable o) {
		int cmp = word.compareTo(o.getWord());
		if(0 != cmp) return cmp;
		return count.compareTo(o.getCount());
	}

	public String getWord() {
		return word;
	}

	public Long getCount() {
		return count;
	}
	
	public void set(String word, Long count) {
		this.word = word;
		this.count = count;
	}

	@Override
	public String toString() { 
		return word + "\t" + count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopWritable other = (TopWritable) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	 
	
}
