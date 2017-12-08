package modele;
/**
 * 
 */


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class Shoot extends Base {
	private String type;
	public Shoot(int x, int y, String type) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 7;
		this.speed = 8;
		this.type = type;
		this.image = new Image("img/shoot.bmp", filter);
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
