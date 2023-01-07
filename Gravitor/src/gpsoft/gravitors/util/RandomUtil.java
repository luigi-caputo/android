package gpsoft.gravitors.util;

import java.util.Random;

public class RandomUtil {
	
	private static Random random = new Random();
	
	public static int getCloseInterval(int start, int end){
		int ran = random.nextInt(Math.abs(end));
		if(ran < start){
			ran = start;
		}
		return ran;
	}
	
}
