package modele;
/**
 * 
 */


import org.newdawn.slick.Color;

/**
 * @author Steven
 *
 */
public class Text extends Base {
	private String type;
	private String text;
	private int timer;
	public Text(int x, int y, String text, Color c, String type) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.timer = 0;
		this.text = text;
		this.filter = c;
	}
	/**
	 * @return the value
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param value the value to set
	 */
	public void setText(String text) {
		this.text = text;
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
}
