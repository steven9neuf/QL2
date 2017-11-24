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
public class Player extends Object {	
	private int reloadTime;
	private int lastShoot;
	
	public Player(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.speed = 5;
		this.reloadTime = 15;
		this.lastShoot = 0;
		this.image = new Image("img/player.bmp", filter);
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
}
