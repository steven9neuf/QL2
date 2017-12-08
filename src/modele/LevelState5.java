/**
 * 
 */
package modele;

/**
 * @author steven
 *
 */
public class LevelState5 implements LevelState {
	public void assignState (Player p) {
		p.setLevelState(this);
	}
	
	public String toString() {
		return "Level 5";
	}
	
	public int getLevel() {
		return 5;
	}
}
