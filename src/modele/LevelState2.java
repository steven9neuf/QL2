/**
 * 
 */
package modele;

/**
 * @author steven
 *
 */
public class LevelState2 implements LevelState {
	public void assignState (Player p) {
		p.setLevelState(this);
	}
	
	public String toString() {
		return "Level 2";
	}
	
	public int getLevel() {
		return 2;
	}
}
