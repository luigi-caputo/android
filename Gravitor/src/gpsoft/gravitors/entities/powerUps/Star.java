package gpsoft.gravitors.entities.powerUps;

import gpsoft.gravitors.Loader;

public class Star extends PowerUP{

	public Star(float x, float y) {
		super(x, y);
		sprite = Loader.star;
		myScore = 1;
	}

}
