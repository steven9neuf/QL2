/**
 * 
 */
package Modele;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class Player extends Object {	
	private int reloadTime;
	private int lastShoot;
	private int life;
	
	public Player(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.speed = 5;
		this.reloadTime = 15;
		this.lastShoot = 0;
		this.life = 3;
		this.animation = new Animation();
		this.animation.addFrame(new Image("img/player_frame1.bmp", filter), 100);
		this.animation.addFrame(new Image("img/player_frame2.bmp", filter), 100);
	}

	/**
	 * @return the reload
	 */
	public int getReloadTime() {
		return reloadTime;
	}

	/**
	 * @param reload the reload to set
	 */
	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	/**
	 * @return the lastShoot
	 */
	public int getLastShoot() {
		return lastShoot;
	}

	/**
	 * @param lastShoot the lastShoot to set
	 */
	public void setLastShoot(int lastShoot) {
		this.lastShoot = lastShoot;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}	
}
