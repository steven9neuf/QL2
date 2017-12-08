package modele;



import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class Cell extends Base{
	private String type;
	private int value;
	
	public Cell(int x, int y, String type) throws SlickException {
		this.x = x;
		this.y = y;
		this.type = type;
		switch(type) {
			case "wall":
				this.image = new Image("img/wall00.bmp", filter);
				break;
			case "ammo":
				this.image = new Image("img/ammo.png");
				this.width = 16;
				this.height = 16;
				break;
			case "life":
				this.image = new Image("img/life.png");
				this.width = 16;
				this.height = 16;
				break;
			default:
				break;
		}
	}
	
	public Cell(int x, int y, String type, int value) throws SlickException {
		this(x, y, type);
		this.value = value;
	}
	
	public Cell(int x, int y, String type, int w, int h) throws SlickException {
		this(x, y, type);
		this.width = w;
		this.height = h;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
