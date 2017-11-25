/**
 * Main class
 */
import Modele.Player;
import Modele.Shoot;
import Modele.Background;
import Modele.Wall;

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
	// 640 * 480 // 800 * 600 // 1024 * 768 // 1440 * 900 // 1920 * 1080 // 2560 * 1440
	private static int width = 1920; 
	private static int height = 1080;
	private static int bg_width = 1920;
	private static int bg_height = 1080;
	private static boolean fullscreen = false;
	private static boolean mouseGrabbed = false;
	// 1 * 2
	private static int mesh_width = 9;
	private static int mesh_height = 18;
	private static int max_wall_delta = 2;
	private static int minimum_wall_space = 10;
	private static final Logger logger = LogManager.getLogger("guru.springframework.blog.log4j2properties");
	
	
	private Image life;
	private Image bullet;
	
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
	private Font font;
	private UnicodeFont ufont;
	
	public static void main(String[] args) throws SlickException {
		try {
			logger.debug("msg de debogage");
		    logger.info("msg d'information");
		    logger.warn("msg d'avertissement");
		    logger.error("msg d'erreur");
		    logger.fatal("msg d'erreur fatale");
	        AppGameContainer app = new AppGameContainer(new WindowGame(), width, height, fullscreen);
	        //app.setTargetFrameRate(maxFPS);
	        app.setMinimumLogicUpdateInterval(20);
	        app.setMaximumLogicUpdateInterval(20);
	        app.setMouseGrabbed(mouseGrabbed);
	        app.setVSync(true);
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
		font = font.deriveFont(24f);
		ufont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		ufont.addAsciiGlyphs();
		ufont.addGlyphs(400, 600);
		ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		ufont.loadGlyphs();
		
		// Image initialization
		life = new Image("img/life.png");
		bullet = new Image("img/shoot.bmp", player.getFilter());
		
		// Bottom wall initialization
		walls = new Wall[width / mesh_width + 4][height / mesh_height + 1];
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
					//g.drawString("X", walls[i][j].getX(), walls[i][j].getY());
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
		
		// Drawing HUD
		// Bullets
		bullet.draw(20, height - 90, 32, 32);
		ufont.drawString(70, height - 88, ": " + player.getAmmo());
		
		// Life
		ufont.drawString(20, height - 40, "Life :");
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
		if(this.shoot && player.getLastShoot() >= player.getReloadTime() && player.getAmmo() > 0) {
			this.shoots.add(new Shoot(x, y));
			player.setLastShoot(0);
			player.setAmmo(player.getAmmo() - 1);
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
				
				// Check if wall exists
				if(walls[i][j] != null) {
					// Updating walls sprites
					// -1 is last column 
					// -2 is before last but last column is not generated yet (below)
					// So we check at the -3 column
					if(i == walls.length - 3 && wallMoved == wallSpeed) {
						checkSprite(walls[i][j], i, j);
					}
					
					// Scrolling wall to the left
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
	
	// Method to check the walls around and update the sprites
	public void checkSprite(Wall w, int i, int j) throws SlickException {
		if(j > 0 && j < wall_max_y) {
			/*
			-##
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H01.bmp", w.getFilter()));
			}
			
			/*
			#-#
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] == null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H02.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H03.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				//walls[i][j].setImage(new Image("img/H04.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				//walls[i][j].setImage(new Image("img/H05.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	-##
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H06.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	#-#
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H07.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	##-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H08.bmp", w.getFilter()));
			}
			
			/*
			--#
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H09.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H10.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H11.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	-##
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H11.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W-
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				//walls[i][j].setImage(new Image("img/H12.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W#
		 	-##
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				//walls[i][j].setImage(new Image("img/H13.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W#
		 	#-#
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				//walls[i][j].setImage(new Image("img/H14.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	#W#
		 	##-
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				//walls[i][j].setImage(new Image("img/H15.bmp", w.getFilter()));
			}
			
			/*
			#--
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H16.bmp", w.getFilter()));
			}
			
			/*
			---
		 	#W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H17.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H18.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W#
		 	-##
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H18.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	-#-
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	-##
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W-
		 	##-
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H19.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	---
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H20.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W-
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H21.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	##-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H21.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	-W-
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H22.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W-
		 	-#-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H22.bmp", w.getFilter()));
			}
			
			/*
			#--
		 	#W-
		 	###
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H23.bmp", w.getFilter()));
			}
			
			/*
			#--
		 	#W-
		 	##-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H23.bmp", w.getFilter()));
			}
			
			/*
			---
		 	#W-
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H24.bmp", w.getFilter()));
			}
			
			/*
			---
		 	#W-
		 	##-
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H24.bmp", w.getFilter()));
			}
			
			/*
			--#
		 	-W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H25.bmp", w.getFilter()));
			}
			
			/*
			--#
		 	-W#
		 	-##
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H25.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	#W-
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H26.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	-W#
		 	###
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H27.bmp", w.getFilter()));
			}
			
			/*
			---
		 	-W#
		 	-##
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] == null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] != null	
			) {
				//walls[i][j].setImage(new Image("img/H28.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	--#
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H29.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	---
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H30.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	---
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H30.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	--#
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H31.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	--#
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H31.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W#
		 	-#-
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H32.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W#
		 	-#-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H32.bmp", w.getFilter()));
			}
			
			/*
			###
		 	-W-
		 	---
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			-##
		 	-W-
		 	---
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	-W-
		 	---
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			-#-
		 	-W-
		 	---
			 */
			if(walls[i - 1][j - 1] == null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H33.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	---
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H34.bmp", w.getFilter()));
			}

			/*
			##-
		 	#W-
		 	---
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H34.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W#
		 	#--
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H35.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	#--
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H36.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W-
		 	#--
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H36.bmp", w.getFilter()));
			}
			
			/*
			###
		 	#W-
		 	-#-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] != null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H37.bmp", w.getFilter()));
			}
			
			/*
			##-
		 	#W-
		 	-#-
			 */
			if(walls[i - 1][j - 1] != null && walls[i][j - 1] != null && walls[i + 1][j - 1] == null
			&& walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] == null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] != null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H37.bmp", w.getFilter()));
			}
			
			
		}
		else if(j == 0) {
			/*
		 	#W#
		 	#-#
			 */
			if(walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H07.bmp", w.getFilter()));
			}
			
			/*
		 	#W#
		 	---
			 */
			if(walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H20.bmp", w.getFilter()));
			}
			
			/*
		 	#W#
		 	--#
			 */
			if(walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H29.bmp", w.getFilter()));
			}
			
			/*
		 	-W#
		 	---
			 */
			if(walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H30.bmp", w.getFilter()));
			}
			
			/*
		 	-W#
		 	--#
			 */
			if(walls[i - 1][j    ] == null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] == null && walls[i][j + 1] == null && walls[i + 1][j + 1] != null	
			) {
				walls[i][j].setImage(new Image("img/H31.bmp", w.getFilter()));
			}
			
			/*
		 	#W#
		 	#--
			 */
			if(walls[i - 1][j    ] != null  						  && walls[i + 1][j    ] != null
			&& walls[i - 1][j + 1] != null && walls[i][j + 1] == null && walls[i + 1][j + 1] == null	
			) {
				walls[i][j].setImage(new Image("img/H35.bmp", w.getFilter()));
			}
		}
	}
}
























