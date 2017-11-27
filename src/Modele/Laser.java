/**
 * 
 */
package Modele;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class Laser extends Object {
	private int timer;
	private int duration;
	public Laser(int x, int y, int width, int height, int duration) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.timer = 0;
		this.duration = duration;
		this.image = new Image("img/laser.png");
	}
	/**
	 * @return the timer
	 */
	public int getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
