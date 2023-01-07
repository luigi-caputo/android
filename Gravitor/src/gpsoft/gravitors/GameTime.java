package gpsoft.gravitors;

public class GameTime {
public static int time = 0;
public static void tick(){
	time = (int) System.currentTimeMillis();
}
}
