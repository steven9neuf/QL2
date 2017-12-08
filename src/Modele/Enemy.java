package modele;



import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Steven
 *
 */
public class Enemy extends Base {
	private int type;
	private int reloadTime;
	private int lastShoot;
	private int life;
	private int maxLife;
	private Image info_bar;
	private int score;
	public Enemy(int x, int y, int width, int height, int type, int reloadTime, int lastShoot, int life) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.reloadTime = reloadTime;
		this.lastShoot = lastShoot;
		this.info_bar = new Image("img/info_bar.png");
		switch(type){
			case 0:
				this.image = new Image("img/enemy0.png");
				this.life = life;
				this.maxLife = life;
				this.score = 200;
				break;
			case 1:
				this.image = new Image("img/enemy1.bmp", this.filter);
				this.life = life;
				this.maxLife = life;
				this.score = 300;
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

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the info_bar
	 */
	public Image getInfo_bar() {
		return info_bar;
	}

	/**
	 * @param info_bar the info_bar to set
	 */
	public void setInfo_bar(Image info_bar) {
		this.info_bar = info_bar;
	}

	/**
	 * @return the maxLife
	 */
	public int getMaxLife() {
		return maxLife;
	}

	/**
	 * @param maxLife the maxLife to set
	 */
	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
}
