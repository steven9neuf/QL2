/**
 * 
 */
package modele;

/**
 * @author steven
 *
 */
public class LevelState3 implements LevelState {
	public void assignState (Player p) {
		p.setLevelState(this);
	}
	
	public String toString() {
		return "Level 3";
	}
	
	public int getLevel() {
		return 3;
	}
}
