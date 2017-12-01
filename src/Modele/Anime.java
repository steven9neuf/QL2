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
				this.animation.addFrame(new Image("img/tp1.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp2.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp3.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp4.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp5.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp6.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp7.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp8.gif"), frame_duration);
				this.animation.addFrame(new Image("img/tp9.gif"), frame_duration);
				break;
			case "LU":
				this.animation.addFrame(new Image("img/levelup/frame-01.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-02.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-03.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-04.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-05.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-06.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-07.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-08.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-09.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-10.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-11.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-12.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-13.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-14.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-15.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-16.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-17.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-18.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-19.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-20.gif"), frame_duration);
				this.animation.addFrame(new Image("img/levelup/frame-21.gif"), frame_duration);
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
