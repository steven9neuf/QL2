/**
 * 
 */
package Modele;

import org.newdawn.slick.*;


/**
 * @author steven
 *
 */
public class Anime extends Object {
	private int timer;
	private String type;
	
	public Anime(int x, int y, int frame_duration, int width, int height, String type) throws SlickException {
		this.timer = 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.animation = new Animation();
		switch(type) {
			case "explosion":
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
				break;
			case "tp":
				this.animation.addFrame(new Image("img/tp1.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp2.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp3.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp4.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp5.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp6.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp7.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp8.gif", filter), frame_duration);
				this.animation.addFrame(new Image("img/tp9.gif", filter), frame_duration);
				break;
			default:
				break;
		}
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
