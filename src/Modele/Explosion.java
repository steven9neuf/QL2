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
public class Explosion extends Object {
	private int timer;
	public Explosion(int x, int y, int frame_duration, int width, int height) throws SlickException {
		this.timer = 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.animation = new Animation();
		this.animation.addFrame(new Image("img/explo.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo1.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo2.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo3.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo4.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo5.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo6.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo7.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo8.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo9.bmp", filter), frame_duration);
		this.animation.addFrame(new Image("img/explo10.bmp", filter), frame_duration);
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
