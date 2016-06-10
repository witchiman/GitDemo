package from1_20;

/*有四个人，甲乙丙丁，有一个人是凶手，甲说不是我，乙说是丙，丙说是丁，丁说丙说的是假话。
四个人说的话三真一假。问题是写个小程序判断，输出凶手。*/

public class Solution {
	
	public static void main(String[] args) {
		int sum =0;
		for(int i=0;i<4; i++) {
			char killer='A';
			killer+=i; 
			sum	= ((killer!='A')?1:0) + ((killer=='C')?1:0) + 
					((killer=='D')?1:0) + ((killer!='D')?1:0);
			if(sum == 3) {
				System.out.println("the killer is :" + killer);	
				
			} 
		} 
	}
	 
}
