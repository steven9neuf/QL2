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
public class Wall extends Object{
	public Wall(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.image = new Image("img/wall00.bmp", filter);
	}
}
