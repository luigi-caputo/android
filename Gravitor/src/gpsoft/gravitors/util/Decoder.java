package gpsoft.gravitors.util;

public class Decoder {

	public static String DecodeScore(String score){
		try{
			String decodedString = "";
			String[] numbers = score.split("=");
			for(int i = 0; i<numbers.length; i++){
				decodedString += Integer.parseInt(numbers[i])+1000;
			}
			return decodedString;
		}catch(Exception e){
			return null;
		}
	}
	
}
