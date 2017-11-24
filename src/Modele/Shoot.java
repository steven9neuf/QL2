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
public class Shoot extends Object {
	public Shoot(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.speed = 4;
		this.image = new Image("img/shoot.bmp", filter);
	}
}
