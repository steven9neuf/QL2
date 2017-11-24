/**
 * Main class
 */
import Modele.Player;
import Modele.Shoot;
import Modele.Background;
import Modele.Wall;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author steven
 *
 */
public class WindowGame extends BasicGame {
	// Game Parameters
	private static int maxFPS = 60;
	// 640 * 480 // 800 * 600 // 1024 * 768 // 1440 * 900
	private static int width = 1024; 
	private static int height = 768;
	private static int bg_width = 2000;
	private static int bg_height = 1000;
	private static boolean fullscreen = true;
	private static boolean mouseGrabbed = false;
	// 1 * 2
	private static int mesh_width = 9;
	private static int mesh_height = 18;
	private static int max_wall_delta = 2;
	private static int minimum_wall_space = 10;
	
	private Random rand = new Random();
	private GameContainer container;
	private Player player;
	private ArrayList<Background> bgs = new ArrayList<Background>();
	private boolean[] moving = {false, false, false, false};
	private boolean shoot = false;
	private boolean debug = true;
	private ArrayList<Shoot> shoots = new ArrayList<Shoot>();
	private Wall[][] walls;
	private int bottom_wall;
	private int top_wall;
	private int wall_max_y;
	private int wallMoved = 0;
	private int wallSpeed = 3;
	
	public static void main(String[] args) throws SlickException {
		try {
	        AppGameContainer app = new AppGameContainer(new WindowGame(), width, height, fullscreen);
	        app.setTargetFrameRate(maxFPS);
	        app.setMinimumLogicUpdateInterval(20);
	        app.setMaximumLogicUpdateInterval(20);
	        app.setMouseGrabbed(mouseGrabbed);
	        app.start();
		}
		catch (Exception e){
			System.out.println(e);
		}
    }
	
	public WindowGame() {
        super("Qualite Logicielle 2");
    }

	/*************************************************************************/
	/***************************** Initialization ****************************/
	/*************************************************************************/
	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		this.container = gc;
		
		// Player initialization
		this.player = new Player(320, 240);
		
		// Background initialization
		Background bg = new Background(0, 0);
		bgs.add(bg);
		
		// Bottom wall initialization
		walls = new Wall[width / mesh_width + 2][height / mesh_height + 1];
		bottom_wall = height / mesh_height;
		wall_max_y = bottom_wall;
		walls[walls.length - 1][walls[0].length - 1] = new Wall(mesh_width * (walls.length - 1), mesh_height * (walls[0].length - 1));
		
		// Top wall initialization
		top_wall = 0;
		walls[walls.length - 1][0] = new Wall(mesh_width * (walls.length - 1), 0);
	}
	
	/*************************************************************************/
	/*********************************** Render ******************************/
	/*************************************************************************/
	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Drawing background
		for(int i = 0 ; i < this.bgs.size(); i++) {
			Background bg = this.bgs.get(i);
			bg.getImage().draw(bg.getX(), bg.getY(), bg_width, bg_height, new Color(150, 100, 150));
		}
		
		// Drawing player
		int x = this.player.getX();
		int y = this.player.getY();
		//this.player.getImage().draw(x, y);
		g.drawAnimation(player.getAnimation(), x, y);
		
		// Drawing bullets
		for(int i = 0 ; i < this.shoots.size() ; i++) {
			x = this.shoots.get(i).getX();
			y = this.shoots.get(i).getY();
			this.shoots.get(i).getImage().draw(x, y);
		}	
		
		// Drawing wall
		for(int i = 0 ; i < walls.length ; i++) {
			for(int j = 0 ; j < walls[0].length ; j++) {
				if(walls[i][j] != null) {
					walls[i][j].getImage().draw(walls[i][j].getX(), walls[i][j].getY(), mesh_width, mesh_height);
				}
			}
		}
		
		// Drawing informations
		if(this.debug) {
			int padding = 150;
			g.drawString("Bullets : " + this.shoots.size(), width - padding, 10);
			g.drawString("Bg : " + this.bgs.size(), width - padding, 30);
			g.drawString("Wall_width : " + this.walls.length, width - padding, 50);
			g.drawString("Wall_height : " + this.walls[0].length, width - padding, 70);
			g.drawString("top_wall : " + top_wall, width - padding, 90);
			g.drawString("Bottom_wall : " + bottom_wall, width - padding, 110);
		}
	}
	
	/*************************************************************************/
	/******************************** Methods ********************************/
	/*************************************************************************/
	@Override
	public void keyPressed(int key, char c) {
	    switch (key) {
	        case Input.KEY_UP:     	this.moving[0] = true; break;
	        case Input.KEY_RIGHT:  	this.moving[1] = true; break;
	        case Input.KEY_DOWN:		this.moving[2] = true; break;
	        case Input.KEY_LEFT:	 	this.moving[3] = true; break;
	        case Input.KEY_SPACE:	this.shoot = true; break;
	        case Input.KEY_D:		this.debug = !this.debug; break;
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

	
	/*************************************************************************/
	/********************************* Update ********************************/
	/*************************************************************************/
	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {	
		// Background logic
		for(int i = 0 ; i < this.bgs.size() ; i++) {
			Background bg = this.bgs.get(i);
			this.bgs.get(i).setX(bg.getX() - bg.getSpeed());
			if(bg.getX() + bg_width < 0) {
				this.bgs.remove(i);
			}
			if(i == this.bgs.size() - 1) {
				if(bg.getX() + bg_width <= width) {
					Background newBg = new Background(width, 0);
					bgs.add(newBg);
				}
			}
		}
		
		// Moving logic
		int x = player.getX();
		int y = player.getY();
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
		
		// Wall logic
		wallMoved += wallSpeed;
		boolean moved = false;
		for(int i = 0 ; i < walls.length ; i++) {
			for(int j = 0 ; j < walls[0].length ; j++) {
				
				// Moving walls
				if(walls[i][j] != null) {
					walls[i][j].setX(walls[i][j].getX() - wallSpeed);
					if(wallMoved >= mesh_width) {
						moved = true;
						if(i > 0) {
							walls[i - 1][j] = walls[i][j];
						}
						walls[i][j] = null;
					}
				}
			}
			// Generated new walls
			if(i == walls.length - 1 && moved) {
				// For bottom wall
				int next = rand.nextInt(2 * max_wall_delta + 1) - max_wall_delta;
				if(bottom_wall + next < top_wall + minimum_wall_space || bottom_wall + next > wall_max_y) {
					next = 0;
				}
				bottom_wall += next;
				walls[i][bottom_wall] = new Wall(i * mesh_width, bottom_wall * mesh_height);
				
				// Fill all the walls under bottom wall
				for(int j = bottom_wall + 1 ; j < walls[0].length ; j++) {
					walls[i][j] = new Wall(i * mesh_width, j * mesh_height);
				}
				
				
				// For top wall
				next = rand.nextInt(2* max_wall_delta + 1) - max_wall_delta;
				if(top_wall + next < 0 || top_wall + next > bottom_wall - minimum_wall_space) {
					next = 0;
				}
				top_wall += next;
				walls[i][top_wall] = new Wall(i * mesh_width, top_wall * mesh_height);
				
				// Fill all the walls over top wall
				for(int j = top_wall - 1 ; j >= 0 ; j--) {
					walls[i][j] = new Wall(i * mesh_width, j * mesh_height);
				}
			}
		}
		if(wallMoved >= mesh_width) {
			wallMoved = 0;
		}
	}

}
