/**
 * 
 */
package modele;

/**
 * @author steven
 *
 */
public class LevelState1 implements LevelState {
	public void assignState (Player p) {
		p.setLevelState(this);
	}
	
	public String toString() {
		return "Level 1";
	}
	
	public int getLevel() {
		return 1;
	}
}
