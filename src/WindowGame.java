/**
 * Main class
 */
import Modele.Player;
import Modele.Shoot;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class WindowGame extends BasicGame {
	private static int maxFPS = 144;
	private static int width = 1024;
	private static int height = 768;
	private static boolean fullscreen = true;
	
	private GameContainer container;
	private Player player;
	private boolean[] moving = {false, false, false, false};
	private boolean shoot = false;
	private ArrayList<Shoot> shoots = new ArrayList<Shoot>();
	
	public static void main(String[] args) throws SlickException {
		try {
	        AppGameContainer app = new AppGameContainer(new WindowGame(), width, height, fullscreen);
	        app.setTargetFrameRate(maxFPS);
	        app.setMinimumLogicUpdateInterval(20);
	        app.setMaximumLogicUpdateInterval(20);
	        app.start();
		}
		catch (Exception e){
			System.out.println(e);
		}
    }
	
	public WindowGame() {
        super("Qualite Logicielle 2");
    }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.drawString("Player 1", 320, 240);
		
		
		// Drawing player
		int x = this.player.getX();
		int y = this.player.getY();
		this.player.getImage().draw(x, y);
		
		// Drawing bullets
		int NbBullet = this.shoots.size();
		g.drawString(Integer.toString(NbBullet), width - 20, 20);
		for(int i = 0 ; i < this.shoots.size() ; i++) {
			x = this.shoots.get(i).getX();
			y = this.shoots.get(i).getY();
			this.shoots.get(i).getImage().draw(x, y);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		this.container = gc;
		this.player = new Player(320, 240);
	}
	
	@Override
	public void keyPressed(int key, char c) {
	    switch (key) {
	        case Input.KEY_UP:     	this.moving[0] = true; break;
	        case Input.KEY_RIGHT:  	this.moving[1] = true; break;
	        case Input.KEY_DOWN:		this.moving[2] = true; break;
	        case Input.KEY_LEFT:	 	this.moving[3] = true; break;
	        case Input.KEY_SPACE:	this.shoot = true; break;
	    }
	}
	
	@Override
    public void keyReleased(int key, char c) {
        switch (key) {
        		case Input.KEY_UP:		this.moving[0] = false; break;
        		case Input.KEY_RIGHT:   	this.moving[1] = false; break;
        		case Input.KEY_DOWN:   	this.moving[2] = false; break;
        		case Input.KEY_LEFT:  	this.moving[3] = false; break;
        		case Input.KEY_SPACE:	this.shoot = false; break;
        		case Input.KEY_ESCAPE: 	this.container.exit();
        }
    }

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		int x = player.getX();
		int y = player.getY();
		
		// Moving logic
		if(this.moving[0]) {
			player.setY(y - player.getSpeed());
		}
		if(this.moving[1]) {
			player.setX(x + player.getSpeed());
		}
		if(this.moving[2]) {
			player.setY(y + player.getSpeed());
		}
		if(this.moving[3]) {
			player.setX(x - player.getSpeed());
		}
		
		// Shooting logic
		if(this.shoot && player.getLastShoot() >= player.getReloadTime()) {
			this.shoots.add(new Shoot(x, y));
			player.setLastShoot(0);
		}
		player.setLastShoot(player.getLastShoot() + 1);
		for(int i = 0 ; i < this.shoots.size() ; i++) {
			Shoot s = this.shoots.get(i);
			this.shoots.get(i).setX(s.getX() + s.getSpeed());
			if(s.getX() > width) {
				this.shoots.remove(i);
			}
		}
	}

}
