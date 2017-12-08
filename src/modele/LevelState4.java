/**
 * 
 */
package modele;

/**
 * @author steven
 *
 */
public class LevelState4 implements LevelState {
	public void assignState (Player p) {
		p.setLevelState(this);
	}
	
	public String toString() {
		return "Level 4";
	}
	
	public int getLevel() {
		return 4;
	}
}
