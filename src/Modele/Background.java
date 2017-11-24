/**
 * 
 */
package Modele;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class Background extends Object{
	public Background(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.speed = 1;
		this.image = new Image("img/bg.jpg", new Color(200, 150, 200));
	}
}
