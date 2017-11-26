/**
 * 
 */
package Modele;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Steven
 *
 */
public class Teleportation extends Object {
	private int timer;
	public Teleportation(int x, int y, int frame_duration, int width, int height) throws SlickException {
		this.timer = 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.animation = new Animation();
		this.animation.addFrame(new Image("img/tp1.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp2.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp3.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp4.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp5.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp6.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp7.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp8.gif", filter), frame_duration);
		this.animation.addFrame(new Image("img/tp9.gif", filter), frame_duration);
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

}
