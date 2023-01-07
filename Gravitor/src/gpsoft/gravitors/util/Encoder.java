package gpsoft.gravitors.util;

public class Encoder {

	public static String EncodeScore(int score){
		try{
			String scoreAsString = Integer.toString(score);
			String encodedScore = "";
			for(int i = 0; i<scoreAsString.length(); i++){
				encodedScore += Integer.parseInt(Character.toString((scoreAsString.charAt(i))))-1000 + "=";
			}
			return encodedScore;
		}catch(Exception e){
			return null;
		}
	}
	
}
