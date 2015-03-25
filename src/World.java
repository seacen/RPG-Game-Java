/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	public final int NUM_MONSTERS=127, NUM_ITEMS=4,NUM_VILLAGERS=3,SYS_MSG_SEC=2000;
	public final int PLAYER_INIT_XPOS=756, PLAYER_INIT_YPOS=684;
	private TiledMap map;
	private Camera camera;
	private Player player;
	private Monster[] monsters = new Monster[NUM_MONSTERS];
	private Villager[] villagers = new Villager[NUM_VILLAGERS];
	private Item[] items = new Item[NUM_ITEMS];
	private boolean restart=false,end=false,setDeadTimer=false;
	private String message;
	private int deadTimer=SYS_MSG_SEC+1;	//timer for player dead message displaying
	private Font msgFont1;
	private boolean cheat=false,firstSet=false,initPlayer=false,go=false;
	private double storedPxPos,storedPyPos;
	
    /** Create a new World object. 
     * @throws IOException */
    public World()
    throws SlickException, IOException
    {
    	map = new TiledMap("assets/map.tmx", "assets");
    	player = new Player(PLAYER_INIT_XPOS,PLAYER_INIT_YPOS);
    	camera= new Camera(RPG.screenwidth,RPG.screenheight);
    	camera.followUnit(player);
    	initialiseMonsters(monsters);
    	villagers[0]= new Requester(467,679);
    	villagers[1]= new Healer(738,549);
    	villagers[2]= new Guide(756,870);
    	items[0] = new Amulet(965,3563);
    	items[1] = new Sword(4791,1253);
    	items[2] = new Tome(546,6707);
    	items[3] = new Elixir(1976,402);
    	msgFont1 = FontLoader.loadFont("assets/TrajanPro-Bold.otf",30);
    }
    
    
    // getter and setters
    public boolean isRestart() {
		return restart;
	}
	public boolean isEnd() {
		return end;
	}
	public Camera getCamera() {
		return camera;
	}
    public TiledMap getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}
	public Monster[] getMonsters() {
		return monsters;
	}
	public Villager[] getVillagers() {
		return villagers;
	}
	public Item[] getItems() {
		return items;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isCheat() {
		return cheat;
	}
	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}
	public boolean isFirstSet() {
		return firstSet;
	}
	public void setFirstSet(boolean firstSet) {
		this.firstSet = firstSet;
	}
	
	public boolean isInitPlayer() {
		return initPlayer;
	}
	public void setInitPlayer(boolean initPlayer) {
		this.initPlayer = initPlayer;
	}
	public void setGo(boolean go) {
		this.go = go;
	}


	/** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds)
     * @param action action triggered by user for player.
     */
    public void update(double dir_x, double dir_y, int delta, char action)
    throws SlickException
    {	
    	//a cheat option for quickly moving player to the starting position
    	if (initPlayer) {
    		storedPxPos=player.getxPos();
    		storedPyPos=player.getyPos();
    		player.setxPos(PLAYER_INIT_XPOS);
    		player.setyPos(PLAYER_INIT_YPOS);
    		initPlayer=false;
    	}
    	//trigger cheating mode
    	if (cheat) {
    		if (firstSet) {
        		player.setStats(1,100+player.getStats()[1]);
        		player.setStats(2,player.getStats()[2]-200);
        		player.setSpeed(0.5);
        		firstSet=false;
    		}
    		player.setHP(player.getStats()[0]);
    	}
    	// return the player to where he were before moving to the starting position
    	if (go) {
    		player.setxPos(storedPxPos);
    		player.setyPos(storedPyPos);
    		go=false;
    	}
    	
    	player.update(dir_x, dir_y, delta,map,action,this);
    	
    	//dead timer used for displaying dead message
    	if (player.isDead() && deadTimer>SYS_MSG_SEC) {
    		restart=true;
    		return;
    	}
    	else if (player.isWin()) {
    		end=true;
    	}
    	
    	camera.update();
    	
    	for (int i=0;i<monsters.length;i++) {
    		if (monsters[i].isDead()) {
    			continue;
    		}
    		monsters[i].update(delta,this);
    	}
    	for (int i=0;i<villagers.length;i++) {
    		villagers[i].update(delta,this);
    	}
    	if (player.isDead() && !setDeadTimer) {
    		deadTimer=0;
    		setDeadTimer=true;
    	}
    	else if (player.isDead() && setDeadTimer) {
    		deadTimer+=delta;
    	}
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
    	
    	//sx,sy calculate the tile number for rendering starting position
    	int sx=(int)(camera.getMinX()/map.getTileWidth());
    	int sy=(int)(camera.getMinY()/map.getTileHeight());
    	//x_adjust,y_adjust captures the left out remainder from an integer division, this helps the accuracy of rendering
    	int x_adjust=-(int) (map.getTileWidth()*((camera.getMinX()/map.getTileWidth())-sx));
    	int y_adjust=-(int) (map.getTileHeight()*((camera.getMinY()/map.getTileHeight())-sy));
    	
    	map.render(x_adjust,y_adjust,sx,sy,13,10);
    	
    	//Display player x and y position
    	Color VALUE = new Color(1.0f, 1.0f, 1.0f);
    	g.setColor(VALUE);
    	g.drawString(Double.toString(player.getxPos())+", "+Double.toString(player.getyPos()), 0, 0);
    	
    	
    	//translate the graphics into the coordinate system
    	g.translate(-(float)camera.getMinX(), -(float)camera.getMinY());
    	
    	//render items if not found yet
    	for (int i=0;i<items.length;i++) {
    		if (items[i].isFound()) {
    			continue;
    		}
    		items[i].render(g,this);
    	}
    	//render monsters if not dead yet
    	for (int i=0;i<monsters.length;i++) {
    		if (monsters[i].isDead()) {
    			continue;
    		}
    		monsters[i].render(g,this);
    	}
    	//render villagers
    	for (int i=0;i<villagers.length;i++) {
    		villagers[i].render(g,this);
    	}
    	
    	player.render(g,this);
    	
    	if (player.isDead() && deadTimer<=SYS_MSG_SEC) {
    		String msg="You lose!! Game will restart immediately";
    		renderMessage(g,msg,msgFont1);
    		
    	}
    	else if (player.isWin()) {
    		String msg="You win!!";
    		renderMessage(g,msg,msgFont1);
    	}
    	
    }
    
    /** draw messages to screen
	 * @param g graphics object
	 * @param message the message to be rendered in screen
	 * @param font  the font used for the dialogue*/
    public void renderMessage(Graphics g,String message,Font font) throws SlickException {
    	
        double text_x, text_y;         // Coordinates to draw text
        double bar_x,bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);
        
        g.setFont(font);
        bar_width = font.getWidth(message)+6;
        bar_height = font.getHeight(message)+3;
        bar_x = (RPG.screenwidth/2)-(bar_width/2);
        bar_y=(RPG.screenheight/2)-(font.getHeight(message)+3);
        text_x = bar_x + (bar_width - font.getWidth(message)) / 2;
        text_y = bar_y + (bar_height - font.getHeight(message)) / 2;
        
        g.setColor(BAR_BG);
        g.fillRect((float)bar_x, (float)bar_y, (float)bar_width, (float)bar_height);
        g.setColor(VALUE);
        g.drawString(message, (float)text_x, (float)text_y);
    }
	
	/** method for checking if a tile is blocked
	 * @param x  the x position about to go
	 * @param y  the y position about to go */
	public boolean terrainBlocks(double x, double y)
    {
        // Check we are within the bounds of the map
        if (x < 0 || y < 0 || x > map.getWidth() * map.getTileWidth() || y > map.getHeight() * map.getTileHeight()) {
            return true;
        }
        
        // Check the tile properties
        int tile_x = (int) x / map.getTileWidth();
        int tile_y = (int) y / map.getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String block = map.getTileProperty(tileid, "block", "0");
        return !block.equals("0");
    }
	
	
	
	/** health-bar rendering
	 * @param the graphics object
	 * @param the unit for the health bar */
	public void renderHealthBar(Graphics g,Unit unit) {
		
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.2f);   // Black, transp
        Color BAR = new Color(0.1f, 0.1f, 0.2f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        double text_x, text_y;         // Coordinates to draw text
        double bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        double hp_bar_width;           // Size of red (HP) rectangle
        
        double health_percent;       // Player's health, as a percentage
        
        text = unit.getName();
        if (g.getFont().getWidth(text)<70) {
        	bar_width = 70;
        }
        else {
        	bar_width = g.getFont().getWidth(text)+6;
        }
        bar_height = g.getFont().getHeight(text)+3;
        bar_x = unit.getxPos()-(bar_width/2);
        bar_y = unit.getyPos()-(unit.getImage().getHeight()/2)-bar_height;
        health_percent = unit.getHP()/(double)unit.getStats()[0];                         // TODO: HP / Max-HP
        hp_bar_width = bar_width * health_percent;
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        text_y = bar_y + (bar_height - g.getFont().getHeight(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect((float)bar_x, (float)bar_y, (float)bar_width, (float)bar_height);
        g.setColor(BAR);
        g.fillRect((float)bar_x, (float)bar_y, (float)hp_bar_width, (float)bar_height);
        g.setColor(VALUE);
        g.drawString(text, (float)text_x, (float)text_y);
	}
    
    /** panel rendering
     * @param g the graphics object
     * @param panel the panel image */
    public void renderPanel(Graphics g,Image panel)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.1f, 0.1f, 0.17f, 0.8f);      // blue, transp

        // Variables for layout
        String text;                // Text to display
        double text_x, text_y;         // Coordinates to draw text
        double bar_x, bar_y;           // Coordinates to draw rectangles
        double bar_width, bar_height;  // Size of rectangle to draw
        double hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        double health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.screenheight - RPG.PANELHEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.screenheight - RPG.PANELHEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", (float)text_x, (float)text_y);
        text = player.getHP()+"/"+player.getStats()[0];                                 // TODO: HP / Max-HP

        bar_x = 90;
        bar_y = RPG.screenheight - RPG.PANELHEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        
        health_percent = player.getHP()/(double)player.getStats()[0];                         // TODO: HP / Max-HP
        hp_bar_width = (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect((float)bar_x, (float)bar_y, (float)bar_width, (float)bar_height);
        g.setColor(BAR);
        g.fillRect((float)bar_x, (float)bar_y, (float)hp_bar_width, (float)bar_height);
        g.setColor(VALUE);
        g.drawString(text, (float)text_x, (float)text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", (float)text_x, (float)text_y);
        text_x += 80;
        text = player.getStats()[1]+"";                                    // TODO: Damage
        g.setColor(VALUE);
        g.drawString(text, (float)text_x, (float)text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", (float)text_x, (float)text_y);
        text_x += 55;
        text = player.getStats()[2]+"";                                    // TODO: Cooldown
        g.setColor(VALUE);
        g.drawString(text, (float)text_x, (float)text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, (float)text_y);
        bar_x = 490;
        bar_y = RPG.screenheight - RPG.PANELHEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect((float)bar_x, (float)bar_y, (float)bar_width, (float)bar_height);

        inv_x = 490;
        inv_y = RPG.screenheight - RPG.PANELHEIGHT
            + ((RPG.PANELHEIGHT - 72) / 2);
        for (Item item:player.getInventory())                // TODO
        {
            item.getImage().draw(inv_x, inv_y);
            inv_x += 72;
        }
    }
    
    /** Initializing monsters from file data
     * @param monsters an array of monsters to be initialized */
    public void initialiseMonsters(Monster[] monsters) throws IOException, SlickException {
    	File f = new File("data/units.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
    	
    	for (int i=0;i<49;i++) {
    		br.readLine();
    	}
		
    	String line;
    	String name;
    	double xPos,yPos;
    	for (int i=0;i<30;i++) {
    		line=br.readLine();
    		StringTokenizer tokens = new StringTokenizer(line," ,");
    		name= tokens.nextToken()+" "+tokens.nextToken();
    		xPos = Double.parseDouble(tokens.nextToken());
    		yPos = Double.parseDouble(tokens.nextToken());

    		
    		monsters[i]= new PasMonster(xPos,yPos);
    	}
    	
    	for (int i=30;i<monsters.length;i++) {
    		while ((line=br.readLine()).isEmpty()) {
    		}
    		StringTokenizer tokens = new StringTokenizer(line," ,");
    		name= tokens.nextToken();
    		xPos = Double.parseDouble(tokens.nextToken());
    		yPos = Double.parseDouble(tokens.nextToken());
    		
    		if (name.equals("Zombie")) {
    			monsters[i]= new Zombie(xPos,yPos);
    		}
    		else if (name.equals("Bandit")) {
    		    monsters[i]= new Bandit(xPos,yPos);
    		}
    		else if (name.equals("Skeleton")) {
    			monsters[i]= new Skeleton(xPos,yPos);
    		}
    		else if (name.equals("Draelic")){
    			monsters[i]= new Draelic(xPos,yPos);
    		}
    	}
    	br.close();
	}

}
