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
	private int reload;
	
	public Player(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.speed = 2;
		this.reload = 5;
		this.image = new Image("img/player.bmp", filter);
	}	
}
