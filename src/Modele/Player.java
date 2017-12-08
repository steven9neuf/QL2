package modele;



import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class Player extends Base {	
	private int reloadTime;
	private int lastShoot;
	private int life;
	private int ammo;
	private int tp_reload;
	private int last_tp;
	private Image info_bar;
	private int exp;
	private int level;
	private int damage;
	private LevelState levelState = null;
	
	public Player(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = 40;
		this.height = 17;
		this.speed = 5;
		this.reloadTime = 15;
		this.lastShoot = 0;
		this.life = 3;
		this.ammo = 32;
		this.tp_reload = 300;
		this.last_tp = 300;
		this.exp = 0;
		this.level = 1;
		this.damage = 1;
		this.animation = new Animation();
		this.animation.addFrame(new Image("img/player_frame1.bmp", filter), 100);
		this.animation.addFrame(new Image("img/player_frame2.bmp", filter), 100);
		this.info_bar = new Image("img/info_bar.png");
	}
	
	public LevelState getLevelState() {
		return this.levelState;
	}
	
	public void setLevelState(LevelState l) {
		this.levelState = l;
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
	 * @return the ammo
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	/**
	 * @return the tp_reload
	 */
	public int getTp_reload() {
		return tp_reload;
	}

	/**
	 * @param tp_reload the tp_reload to set
	 */
	public void setTp_reload(int tp_reload) {
		this.tp_reload = tp_reload;
	}

	/**
	 * @return the last_tp
	 */
	public int getLast_tp() {
		return last_tp;
	}

	/**
	 * @param last_tp the last_tp to set
	 */
	public void setLast_tp(int last_tp) {
		this.last_tp = last_tp;
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
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void downgrade_player_life() {
		if (getLife() > 0) {
			setLife(getLife() - 1);
		}
	}
}
