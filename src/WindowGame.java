/**
 * Main class
 */
import Modele.Player;
import Modele.Shoot;
import Modele.Anime;
import Modele.Text;
import Modele.Background;
import Modele.Cell;
import Modele.Enemy;
import Modele.Laser;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author steven
 *
 */


public class WindowGame extends BasicGame {
	// Game Parameters
	// private static int maxFPS = 60;
	// 640 * 480 // 800 * 600 // 1024 * 768 // 1440 * 900 // 1920 * 1080
	private static int width = 1440; 
	private static int height = 900;
	private static int bg_width = 1920;
	private static int bg_height = 1080;
	private static boolean fullscreen = true;
	private static boolean mouseGrabbed = false;
	// 1 * 2
	private static int mesh_width = 16;
	private static int mesh_height = 16;
	private static int max_wall_delta = 2;
	private static int minimum_wall_space = 10;
	
	// update refreshing freq
	private static int update_int = 20;
	
	// Life
	private static int life_rate = 10000;
	private static int life_score = 50;
	
	// Ammo
	private static int Ammo_rate = 2000;
	private static int ammo_score = 30;
	
	// Enemies
	private static int laser_duration = 40;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>(); 
	private static int[] enemy_rate = {100, 40};
	private static int[][] enemy_reload = {
			{80, 200}, // enemy 0 
			{60, 140} // enemy 1
			};
	
	// Explosion
	private static int explosion_range = 7;
	private static int explosion_frame_duration = 70;
	private static int shoot_explosion_range = 3;
	
	// TP
	private static int tp_frame_duration = 60;
	private static int tp_width = 50;
	private static int tp_height = 50;
	
	// Levelup
	private static int LU_frame_duration = 70;
	private static int LU_width = 120;
	private static int LU_height = 120;
	
	// Score
	private int score = 0;
	private int score_count = 0;
	private static int score_reduction = 50;
	
	// Text
	private int text_duration = 50;
	private int text_delta = 3;
	
	// Logger
	private static final Logger logger = LogManager.getLogger("storm");
	
	// Player
	private static int max_level = 2;
	private static int tp_range = 300;
	private static int[] level_life = {3, 4};
	private static int[] level_damage = {1, 2};
	private static int max_ammo = 500;
	private static int[] player_exp = {1000, 2000};
	
	// Info_bar
	private static int info_height = 3;
	
	// Stage
	private int stage = 1;
	
	private boolean initialization = true;
	
	private Image life;
	private Image bullet;
	
	private Random rand = new Random();
	private GameContainer container;
	private Player player;
	private ArrayList<Background> bgs = new ArrayList<Background>();
	private boolean[] moving = {false, false, false, false};
	private boolean shoot = false;
	private boolean debug = true;
	private boolean tp = false;
	private ArrayList<Shoot> shoots = new ArrayList<Shoot>();
	private ArrayList<Text> texts = new ArrayList<Text>();
	private Cell[][] grid;
	private int bottom_wall;
	private int top_wall;
	private int wall_max_y;
	private int wall_max_x;
	private int wallMoved = 0;
	// Has to be a diviser of mesh_width
	private int gridSpeed = 4;
	private Font font;
	
	private UnicodeFont HUD_font;
	private UnicodeFont text_font;
	private ArrayList<Anime> animes = new ArrayList<Anime>();
	private ArrayList<Laser> lasers = new ArrayList<Laser>();
	
	public static void main(String[] args) throws SlickException {
		try {
	        AppGameContainer app = new AppGameContainer(new WindowGame(), width, height, fullscreen);
	        //app.setTargetFrameRate(maxFPS);
	        app.setVSync(true);
	        app.setMinimumLogicUpdateInterval(update_int);
	        app.setMaximumLogicUpdateInterval(update_int);
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
		
		// Font initialization
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/pcsenior.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// HUD Font
		font = font.deriveFont(24f);
		HUD_font = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		HUD_font.addAsciiGlyphs();
		HUD_font.addGlyphs(400, 600);
		HUD_font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		HUD_font.loadGlyphs();
		
		// Text score font
		font = font.deriveFont(10f);
		text_font = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		text_font.addAsciiGlyphs();
		text_font.addGlyphs(400, 600);
		text_font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		text_font.loadGlyphs();
		
		// Image initialization
		life = new Image("img/life.png");
		bullet = new Image("img/shoot.bmp", player.getFilter());
		
		// Bottom wall initialization
		bottom_wall = height / mesh_height;
		wall_max_y = bottom_wall;
		wall_max_x = width / mesh_width + 3;
		grid = new Cell[wall_max_x + 1][wall_max_y + 1];
		grid[grid.length - 1][grid[0].length - 1] = new Cell(mesh_width * (grid.length - 1), mesh_height * (grid[0].length - 1), "wall", mesh_width, mesh_height);
		
		// Top wall initialization
		top_wall = 0;
		grid[grid.length - 1][0] = new Cell(mesh_width * (grid.length - 1), 0, "wall", mesh_width, mesh_height);
	}
	
	/*************************************************************************/
	/*********************************** Render ******************************/
	/*************************************************************************/
	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(initialization) {
			// Pre load animations
			Anime a = new Anime(-5, -5, 1, 1, 1, "explosion");
			g.drawAnimation(a.getAnimation(), -5, -5);
			a = new Anime(-5, -5, 1, 1, 1, "tp");
			g.drawAnimation(a.getAnimation(), -5, -5);
			a = new Anime(-5, -5, 1, 1, 1, "LU");
			g.drawAnimation(a.getAnimation(), -5, -5);
			initialization = false;
		}
		
		// Drawing background
		for(int i = 0 ; i < this.bgs.size(); i++) {
			Background bg = this.bgs.get(i);
			bg.getImage().draw(bg.getX(), bg.getY(), bg_width, bg_height, new Color(150, 100, 150));
		}
		
		// Drawing player
		int x = this.player.getX();
		int y = this.player.getY();
		g.drawAnimation(player.getAnimation(), x, y);
		
		// Drawing bullets
		for(int i = 0 ; i < this.shoots.size() ; i++) {
			x = this.shoots.get(i).getX();
			y = this.shoots.get(i).getY();
			this.shoots.get(i).getImage().draw(x, y);
		}
		
		// Drawing enemies
		for(int i = 0 ; i < this.enemies.size() ; i++) {
			Enemy e = enemies.get(i);
			x = e.getX();
			y = e.getY();
			e.getImage().draw(x, y, e.getWidth(), e.getHeight());
			e.getInfo_bar().draw(x, y + e.getHeight(), e.getWidth() * e.getLife() / e.getMaxLife(), info_height, new Color(244, 67, 54));	
		}
		
		// Drawing lasers
		for(int i = 0 ; i < lasers.size() ; i ++) {
			Laser l = lasers.get(i);
			l.getImage().draw(l.getX(), l.getY(), l.getWidth(), l.getHeight());
		}
		
		// Drawing wall
		for(int i = 0 ; i < grid.length ; i++) {
			for(int j = 0 ; j < grid[0].length ; j++) {
				if(grid[i][j] != null) {
					grid[i][j].getImage().draw(grid[i][j].getX(), grid[i][j].getY(), grid[i][j].getWidth(), grid[i][j].getHeight());
				}
			}
		}
		
		// Drawing Animation
		for(int i = 0 ; i < animes.size() ; i++) {
			Anime a = animes.get(i);
			a.getAnimation().draw(a.getX(), a.getY(), a.getWidth(), a.getHeight());
		}
		
		// Drawing informations
		if(this.debug) {
			int padding = 150;
			g.drawString("Bullets : " + this.shoots.size(), width - padding, 10);
			g.drawString("Bg : " + this.bgs.size(), width - padding, 30);
			g.drawString("Wall_width : " + this.grid.length, width - padding, 50);
			g.drawString("Wall_height : " + this.grid[0].length, width - padding, 70);
			g.drawString("top_wall : " + top_wall, width - padding, 90);
			g.drawString("Bottom_wall : " + bottom_wall, width - padding, 110);
			g.drawString("Enemies : " + enemies.size(), width - padding, 130);
		}
		
		// Drawing HUD
		// Text
		for(int i = 0 ; i < texts.size() ; i++) {
			if(texts.get(i).getType() == "game")
				text_font.drawString(texts.get(i).getX(), texts.get(i).getY(), texts.get(i).getText());
			else
				HUD_font.drawString(texts.get(i).getX(), texts.get(i).getY(), texts.get(i).getText());
		}
		
		// Player
		HUD_font.drawString(20, 20, "Stage " + stage);
		HUD_font.drawString(20, 70, "Level " + player.getLevel());
		life.draw(20, 115, 32, 32);
		HUD_font.drawString(60, 120, ": " + level_life[player.getLevel() - 1]);
		bullet.draw(20, 165, 32, 32);
		HUD_font.drawString(60, 170, ": " + level_damage[player.getLevel() - 1]);
		
		// Info_bar
		if(player.getLast_tp() < player.getTp_reload())
			player.getInfo_bar().draw(player.getX(), player.getY() + player.getHeight(), player.getWidth() * player.getLast_tp() / player.getTp_reload(), info_height, new Color(0, 191, 255));
		else
			player.getInfo_bar().draw(player.getX(), player.getY() + player.getHeight(), player.getWidth(), info_height, new Color(0, 255, 191));
		player.getInfo_bar().draw(player.getX(), player.getY() + player.getHeight() + info_height, player.getWidth() * player.getExp() / player_exp[player.getLevel() - 1], info_height, new Color(250, 250, 250));
		
		// Score
		HUD_font.drawString(20, height - 126, "Score : " + score);
		
		// Bullets
		bullet.draw(20, height - 90, 32, 32);
		HUD_font.drawString(70, height - 83, ": " + player.getAmmo());
		
		// Life
		HUD_font.drawString(20, height - 40, "Life :");
		for(int i = 0 ; i < player.getLife() ; i++) {
			life.draw(170 + 42 * i, height - 45, 32, 32);
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

		// Score logic
		score_count ++;
		if(score_count >= score_reduction) {
			score ++;
			score_count = 0;
		}
		
		// Level logic
		if(player.getExp() > player_exp[player.getLevel() - 1]) {
			player.setExp(0);
			if(player.getLevel() + 1 <= max_level)
				player.setLevel(player.getLevel() + 1);
			animes.add(new Anime(player.getX() + player.getWidth() / 2 - LU_width / 2, player.getY() + player.getHeight() / 2 - LU_height / 2, LU_frame_duration, LU_width, LU_height, "LU"));
		}
		
		// Text logic
		for(int i = 0 ; i < texts.size() ; i++) {
			texts.get(i).setTimer(texts.get(i).getTimer() + 1);
			texts.get(i).setY(texts.get(i).getY() - (int)Math.round(Math.pow(texts.get(i).getTimer(), 2) / Math.pow(text_duration, 2) * text_delta));
			if(texts.get(i).getTimer() >= text_duration) {
				texts.remove(i);
			}
		}
		
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
		if(player.getLast_tp() + 1 <= player.getTp_reload())
			player.setLast_tp(player.getLast_tp() + 1);
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
		if(tp && player.getLast_tp() >= player.getTp_reload()) {  
			player.setX(x + tp_range);
			player.setLast_tp(0);
			animes.add(new Anime(x + player.getWidth() / 2 - tp_width / 2, y + player.getHeight() / 2 - tp_height / 2, tp_frame_duration, tp_width, tp_height, "tp"));
			animes.add(new Anime(x + tp_range + player.getWidth() / 2 - tp_width / 2, y + player.getHeight() / 2 - tp_height / 2, tp_frame_duration, tp_width, tp_height, "tp"));
		}
		
		// Shooting logic
		if(this.shoot && player.getLastShoot() >= player.getReloadTime() && player.getAmmo() > 0) {
			logger.info("Player shot");
			switch(player.getLevel()) {
				case 1:
					this.shoots.add(new Shoot(x, y, "ally"));
					break;
				case 2:
					this.shoots.add(new Shoot(x, y, "ally"));
					this.shoots.add(new Shoot(x, y + 10, "ally"));
					break;
				default:
					this.shoots.add(new Shoot(x, y, "ally"));
					break;
			}
			player.setLastShoot(0);
			player.setAmmo(player.getAmmo() - 1);
		}
		player.setLastShoot(player.getLastShoot() + 1);
		for(int i = 0 ; i < this.shoots.size() ; i++) {
			Shoot s = this.shoots.get(i);
			if(s.getType() == "ally")
				s.setX(s.getX() + s.getSpeed());
			else
				s.setX(s.getX() - s.getSpeed());
			if(s.getX() > width) {
				this.shoots.remove(i);
			}
		}
		
		// Animation logic
		for(int i = 0 ; i < animes.size() ; i++) {
			Anime a = animes.get(i);
			a.setX(a.getX() - gridSpeed);
			a.setTimer(a.getTimer() + update_int);
			if(a.getType() == "explosion" && a.getTimer() > explosion_frame_duration * 11)
				animes.remove(i);
			else if(a.getType() == "tp" && a.getTimer() > tp_frame_duration * 9)
				animes.remove(i);
			else if(a.getType() == "LU" && a.getTimer() > LU_frame_duration * 21)
				animes.remove(i);
		}
		
		// Enemy logic
		for(int i = 0 ; i < enemies.size() ; i++) {
			Enemy e = enemies.get(i);
			
			// If enemy dies
			if(e.getLife() <= 0) {
				texts.add(new Text(200, height - 169, "+" + e.getScore(), new Color(255, 255, 255), "HUD"));
				score += e.getScore();
				player.setExp(player.getExp() + e.getScore());
				enemies.remove(i);
			}
			
			// Incrementing last shoot
			e.setLastShoot(e.getLastShoot() + 1);
			
			// Shoot
			if(e.getLastShoot() >= e.getReloadTime()) {
				switch(e.getType()) {
					case 0:
						lasers.add(new Laser(e.getX() + e.getWidth() / 2, e.getY(), 3, height, laser_duration));
						break;
					case 1:
						shoots.add(new Shoot(e.getX(), e.getY(), "enemy"));
						break;
					default:
						break;
				}
				e.setLastShoot(0);
			}
			
			// Move to the left
			e.setX(e.getX() - gridSpeed);
			
			// Delete if it goes out of the map
			if(e.getX() < 0)
				enemies.remove(i);
		}
		
		// Laser logic
		for(int i = 0 ; i < lasers.size() ; i ++) {
			Laser l = lasers.get(i);
			
			// Moving to left
			l.setX(l.getX() - gridSpeed);
			
			// Timer management
			l.setTimer(l.getTimer() + 1);
			if(l.getTimer() >= l.getDuration()) {
				lasers.remove(i);
			}
			
		}
		
		// Wall logic
		wallMoved += gridSpeed;
		boolean moved = false;
		for(int i = 0 ; i < grid.length ; i++) {
			for(int j = 0 ; j < grid[0].length ; j++) {
				
				// Check if wall exists
				if(grid[i][j] != null) {
					// Updating grid sprites
					// -1 is last column 
					// -2 is before last but last column is not generated yet (below)
					// So we check at the -3 column
					if(i == grid.length - 3 && wallMoved == gridSpeed) {
						checkSprite(grid[i][j], i, j);
					}
					
					// Scrolling wall to the left
					grid[i][j].setX(grid[i][j].getX() - gridSpeed);
					if(wallMoved >= mesh_width) {
						moved = true;
						if(i > 0) {
							grid[i - 1][j] = grid[i][j];
						}
						grid[i][j] = null;
					}
				}
			}
			// Generate new objects
			if(i == grid.length - 1 && moved) {
				// Generate new grid
				
				// For bottom wall
				int next = rand.nextInt(2 * max_wall_delta + 1) - max_wall_delta;
				if(bottom_wall + next < top_wall + minimum_wall_space || bottom_wall + next > wall_max_y) {
					next = 0;
				}
				bottom_wall += next;
				grid[i][bottom_wall] = new Cell(i * mesh_width, bottom_wall * mesh_height, "wall", mesh_width, mesh_height);
				
				// Fill all the grid under bottom wall
				for(int j = bottom_wall + 1 ; j < grid[0].length ; j++) {
					grid[i][j] = new Cell(i * mesh_width, j * mesh_height, "wall", mesh_width, mesh_height);
				}
				
				
				// For top wall
				next = rand.nextInt(2* max_wall_delta + 1) - max_wall_delta;
				if(top_wall + next < 0 || top_wall + next > bottom_wall - minimum_wall_space) {
					next = 0;
				}
				top_wall += next;
				grid[i][top_wall] = new Cell(i * mesh_width, top_wall * mesh_height, "wall", mesh_width, mesh_height);
				
				// Fill all the grid over top wall
				for(int j = top_wall - 1 ; j >= 0 ; j--) {
					grid[i][j] = new Cell(i * mesh_width, j * mesh_height, "wall", mesh_width, mesh_height);
				}
			
				// Generate Ammo and life
				for(int j = 0 ; j < grid[0].length ; j++) {
					if(grid[i][j] == null) {
						next = rand.nextInt(Ammo_rate);
						if(next == 0) {
							grid[i][j] = new Cell(i * mesh_width, j * mesh_height, "ammo", rand.nextInt(6) + 5);
						}
						else{
							next = rand.nextInt(life_rate);
							if(next == 0) {
								grid[i][j] = new Cell(i * mesh_width, j * mesh_height, "life");
							}
						}
						
					}
				}
				int j;
				int width;
				int height;
				
				// Generate enemies
				for(int n = 0 ; n <= 1 ; n++) {
					next = rand.nextInt(enemy_rate[n]);
					if(next == 0) {
						switch(n) {
							case 0:
								j = top_wall + 1;
								width = 32;
								height = 64;
								break;
							case 1:
								j = rand.nextInt(bottom_wall - top_wall - 2) + top_wall + 1;
								width = 40;
								height = 17;
								break;
							default:
								j = 0;
								width = 0;
								height = 0;
								break;
						}
						enemies.add(new Enemy(i * mesh_width - width / 2, j * mesh_height - height / 2, width, height, n, rand.nextInt(enemy_reload[n][1] - enemy_reload[n][0]) + enemy_reload[n][0], rand.nextInt(enemy_reload[n][0])));
					}
				}			
			}
		}
		if(wallMoved >= mesh_width) {
			wallMoved = 0;
		}
		
		checkCollision();
	}
	
	
	/*************************************************************************/
	/******************************** Methods ********************************/
	/*************************************************************************/
	
	public void checkCollision() throws SlickException {
		// Player collision
		checkPlayerCollision();
		// Player shoot collision
		checkPlayerShootCollision();	
	}
	
	public void checkPlayerCollision() throws SlickException {
		// For the grid
		for(int i = 0 ; i < grid.length ; i++) {
			for(int j = 0 ; j < grid[0].length ; j++) {
				if(grid[i][j] != null) {
					boolean col = intersect(player.getX(), player.getY(), player.getWidth(), player.getHeight(), grid[i][j].getX(), grid[i][j].getY(), grid[i][j].getWidth(), grid[i][j].getHeight());
					if(col) {
						switch(grid[i][j].getType()) {
						case "ammo":
							if(player.getAmmo() + grid[i][j].getValue() < max_ammo) 
								player.setAmmo(player.getAmmo() + grid[i][j].getValue());
							else
								player.setAmmo(max_ammo);
							texts.add(new Text(i * mesh_width, j * mesh_height, "+" + grid[i][j].getValue(), new Color(255, 255, 255), "game"));
							texts.add(new Text(200, height - 169, "+" + ammo_score, new Color(255, 255, 255), "HUD"));
							score += ammo_score;
							grid[i][j] = null;
							break;
						case "life":
							int max_life = level_life[player.getLevel() - 1];
							if(player.getLife() + 1 <= max_life) 
								player.setLife(player.getLife() + 1);
							texts.add(new Text(200, height - 169, "+" + life_score, new Color(255, 255, 255), "HUD"));
							score += life_score;
							grid[i][j] = null;
							break;
						case "wall":
							if(player.getLife() > 0) {
								player.setLife(player.getLife() - 1);
							}
							explode(i, j, explosion_range);
							break;
						default:
							break;
						}
					}
				}
			}
		}
		// For lasers
		for(int i = 0 ; i < lasers.size() ; i++) {
			Laser l = lasers.get(i);
			boolean col = false;
			col = intersect(player.getX(), player.getY(), player.getWidth(), player.getHeight(), l.getX(), l.getY(), l.getWidth(), l.getHeight());
			if(col) {
				if(player.getLife() > 0) {
					player.setLife(player.getLife() - 1);
				}
				lasers.remove(i);
				explode(player.getX() / mesh_width, player.getY() / mesh_height, explosion_range);
			}
		}
	}
	
	public void checkPlayerShootCollision() throws SlickException {
		for(int k = 0 ; k < shoots.size() ; k ++) {
			Shoot s = shoots.get(k);
			if(s.getType() == "ally") {
				boolean removed = false;
				// For the grid
				for(int i = 0 ; i < grid.length ; i++) {
					for(int j = 0 ; j < grid[0].length ; j++) {
						if(grid[i][j] != null) {
							boolean col = intersect(s.getX(), s.getY(), s.getWidth(), s.getHeight(), grid[i][j].getX(), grid[i][j].getY(), grid[i][j].getWidth(), grid[i][j].getHeight());
							if(col && removed == false) {
								switch(grid[i][j].getType()) {
								case "wall":
									explode(s.getX() / mesh_width, s.getY() / mesh_height, shoot_explosion_range);
									break;
								default:
									break;
								}
								shoots.remove(k);
								removed = true;
							}
						}
					}
				}
				
				// For enemies
				if(removed == false) {
					for(int n = 0 ; n < enemies.size() ; n++) {
						Enemy e = enemies.get(n);
						boolean col = intersect(s.getX(), s.getY(), s.getWidth(), s.getHeight(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
						if(col && removed == false) {
							e.setLife(e.getLife() - player.getDamage());
							shoots.remove(k);
							removed = true;
						}
					}
				}
			}
		}
	}
	
	// Check if two entities collide
	public boolean intersect(int px, int py, int pw, int ph, int cx, int cy, int cw, int ch) {
		boolean bool = false;
		boolean int_X = intersect_segments(cx, cx + cw, px, px + pw);
		boolean int_Y = intersect_segments(cy, cy + ch, py, py + ph);
		if(int_X && int_Y)
			bool = true;
		return bool;
	}
	
	// Check if two segment intersect
	public boolean intersect_segments(int a, int b, int c, int d) {
		boolean bool = false;
		if(c > a && c < b || d > a && d < b || c <= a && d >= b)
			bool = true;
		return bool;
	}
	
	// Explosion around after collision
	public void explode(int x, int y, int range) throws SlickException {
		animes.add(new Anime(x * mesh_width - range * mesh_width, y * mesh_height - range * mesh_height, explosion_frame_duration, range * mesh_width * 2, range * mesh_height * 2, "explosion"));
		for(int i = x - range ; i <= x + range ; i++) {
			for(int j = y - range ; j <= y + range ; j++) {
				if(i >= 0 && i <= wall_max_x && j >= 0 && j <= wall_max_y && (Math.pow(i - x, 2) + Math.pow(j - y, 2)) <= Math.pow(range, 2)) {
					grid[i][j] = null;
				}
			}
		}
		// Check the wall sprites to update it if necessary
		for(int i = x - range - 1 ; i <= x + range + 1 ; i++) {
			for(int j = y - range - 1 ; j <= y + range + 1 ; j++) {
				if(i > 0 && i <= grid.length - 2 && j >= 0 && j < grid[0].length && grid[i][j] != null) {
					checkSprite(grid[i][j], i, j);
				}
			}
		}
		
	}
	
	// Pressing keys handle
	@Override
	public void keyPressed(int key, char c) {
	    switch (key) {
	        case Input.KEY_UP:     	this.moving[0] = true; break;
	        case Input.KEY_RIGHT:  	this.moving[1] = true; break;
	        case Input.KEY_DOWN:	this.moving[2] = true; break;
	        case Input.KEY_LEFT:	this.moving[3] = true; break;
	        case Input.KEY_SPACE:	this.shoot = true; break;
	        case Input.KEY_B:	tp = true; break;
	        case Input.KEY_D:		this.debug = !this.debug; break;
	    }
	}
	
	// Release key handle
	@Override
    public void keyReleased(int key, char c) {
        switch (key) {
        		case Input.KEY_UP:		this.moving[0] = false; break;
        		case Input.KEY_RIGHT:   this.moving[1] = false; break;
        		case Input.KEY_DOWN:   	this.moving[2] = false; break;
        		case Input.KEY_LEFT:  	this.moving[3] = false; break;
        		case Input.KEY_SPACE:	this.shoot = false; break;
        		case Input.KEY_B:	tp = false; break;
        		case Input.KEY_ESCAPE: 	this.container.exit();
        }
    }
	
	// Method to check the grid around and update the sprites
	public void checkSprite(Cell w, int i, int j) throws SlickException {
		if(j > 0 && j < wall_max_y) {
			/*
			-##
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H01.bmp", w.getFilter()));
			}
			
			/*
			#-#
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] == null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H02.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H03.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				//grid[i][j].setImage(new Image("img/H04.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				//grid[i][j].setImage(new Image("img/H05.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	-##
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H06.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	#-#
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H07.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	##-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H08.bmp", w.getFilter()));
			}
			
			/*
			--#
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H09.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H10.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H11.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	-##
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H11.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W-
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				//grid[i][j].setImage(new Image("img/H12.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W#
		 	-##
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				//grid[i][j].setImage(new Image("img/H13.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W#
		 	#-#
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				//grid[i][j].setImage(new Image("img/H14.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W#
		 	##-
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				//grid[i][j].setImage(new Image("img/H15.bmp", w.getFilter()));
			}
			
			/*
			#--
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H16.bmp", w.getFilter()));
			}
			
			/*
			---
		 	#W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H17.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H18.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W#
		 	-##
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H18.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	-#-
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	-##
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	##-
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	---
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H20.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W-
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H21.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	##-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H21.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	-W-
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H22.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W-
		 	-#-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H22.bmp", w.getFilter()));
			}
			
			/*
			#--
		 	#W-
		 	###
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H23.bmp", w.getFilter()));
			}
			
			/*
			#--
		 	#W-
		 	##-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H23.bmp", w.getFilter()));
			}
			
			/*
			---
		 	#W-
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H24.bmp", w.getFilter()));
			}
			
			/*
			---
		 	#W-
		 	##-
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H24.bmp", w.getFilter()));
			}
			
			/*
			--#
		 	-W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H25.bmp", w.getFilter()));
			}
			
			/*
			--#
		 	-W#
		 	-##
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H25.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	#W-
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H26.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	-W#
		 	###
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H27.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W#
		 	-##
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] == null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] != null	
			) {
				//grid[i][j].setImage(new Image("img/H28.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	--#
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H29.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	---
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H30.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	---
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H30.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	--#
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H31.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	--#
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H31.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	-#-
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H32.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	-#-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H32.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W-
		 	---
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W-
		 	---
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	-W-
		 	---
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	-W-
		 	---
			 */
			if(grid[i - 1][j - 1] == null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	---
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H34.bmp", w.getFilter()));
			}

			/*
			##-
		 	#W-
		 	---
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H34.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	#--
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H35.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	#--
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H36.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W-
		 	#--
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H36.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	-#-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] != null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H37.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W-
		 	-#-
			 */
			if(grid[i - 1][j - 1] != null && grid[i][j - 1] != null && grid[i + 1][j - 1] == null
			&& grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] == null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] != null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H37.bmp", w.getFilter()));
			}
			
			
		}
		else if(j == 0) {
			/*
		 	#W#
		 	#-#
			 */
			if(grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H07.bmp", w.getFilter()));
			}
			
			/*
		 	#W#
		 	---
			 */
			if(grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H20.bmp", w.getFilter()));
			}
			
			/*
		 	#W#
		 	--#
			 */
			if(grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H29.bmp", w.getFilter()));
			}
			
			/*
		 	-W#
		 	---
			 */
			if(grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H30.bmp", w.getFilter()));
			}
			
			/*
		 	-W#
		 	--#
			 */
			if(grid[i - 1][j    ] == null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] == null && grid[i][j + 1] == null && grid[i + 1][j + 1] != null	
			) {
				grid[i][j].setImage(new Image("img/H31.bmp", w.getFilter()));
			}
			
			/*
		 	#W#
		 	#--
			 */
			if(grid[i - 1][j    ] != null  						  && grid[i + 1][j    ] != null
			&& grid[i - 1][j + 1] != null && grid[i][j + 1] == null && grid[i + 1][j + 1] == null	
			) {
				grid[i][j].setImage(new Image("img/H35.bmp", w.getFilter()));
			}
		}
	}
}
























