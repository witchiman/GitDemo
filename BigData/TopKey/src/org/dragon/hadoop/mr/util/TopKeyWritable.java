package org.dragon.hadoop.mr.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class TopKeyWritable implements WritableComparable<TopKeyWritable>{
	 private String languageType;
	 private String songName;
	 private Long playTimes;
	 
	 public TopKeyWritable() {};
	
	 public TopKeyWritable(String languageType, String songName, Long playTimes) {
		 this.set(languageType, songName, playTimes);
	 };

	@Override
	public void readFields(DataInput in) throws IOException {
		this.languageType = in.readUTF();
		this.songName = in.readUTF();
		this.playTimes = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(languageType);
		out.writeUTF(songName);
		out.writeLong(playTimes);
	}

	@Override
	public int compareTo(TopKeyWritable o) { 
		return -this.getPlayTimes().compareTo(o.getPlayTimes()); //显示为倒序
	}
	

	public String getLanguageType() {
		return languageType;
	}

	public String getSongName() {
		return songName;
	}

	public Long getPlayTimes() {
		return playTimes;
	} 
	
	public void set(String languageType, String songName, Long playTimes) {
		this.languageType = languageType;
		this.songName = songName;
		this.playTimes = playTimes;
	}

	@Override
	public String toString() {
		return languageType + "\t" + songName + "\t" + playTimes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((languageType == null) ? 0 : languageType.hashCode());
		result = prime * result + ((playTimes == null) ? 0 : playTimes.hashCode());
		result = prime * result + ((songName == null) ? 0 : songName.hashCode());
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
		TopKeyWritable other = (TopKeyWritable) obj;
		if (languageType == null) {
			if (other.languageType != null)
				return false;
		} else if (!languageType.equals(other.languageType))
			return false;
		if (playTimes == null) {
			if (other.playTimes != null)
				return false;
		} else if (!playTimes.equals(other.playTimes))
			return false;
		if (songName == null) {
			if (other.songName != null)
				return false;
		} else if (!songName.equals(other.songName))
			return false;
		return true;
	}
	
}
