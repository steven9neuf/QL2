/**
 * 
 */
package Modele;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Steven
 *
 */
public class Enemy extends Object {
	private int type;
	private int reloadTime;
	private int lastShoot;
	public Enemy(int x, int y, int width, int height, int type, int reloadTime, int lastShoot) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.reloadTime = reloadTime;
		this.lastShoot = lastShoot;
		switch(type){
			case 0:
				this.image = new Image("img/enemy0.png");
				break;
			default:
				break;
		}
		
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
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
	 * @return the last_shoot
	 */
	public int getLastShoot() {
		return lastShoot;
	}
	/**
	 * @param last_shoot the last_shoot to set
	 */
	public void setLastShoot(int lastShoot) {
		this.lastShoot = lastShoot;
	}
}
