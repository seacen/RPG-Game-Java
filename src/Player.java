import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** The player controlled by user in the game */
public class Player extends Unit{
	
	public final int HP_F=100,MAX=100,DAMAGE=26,COOLDOWN=600;
	public final double SPEED=0.25;
	private double prevDir_x=1;
	private boolean flipped=false,win=false,hasElixir=false;
	private Image panel;
	private ArrayList<Item> inventory = new ArrayList<Item>(4);
	
	//Creating a new player object
	public Player(double xPos,double yPos)
	throws SlickException
	{
		super(xPos, yPos);
		setSpeed(SPEED);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		setImage(new Image("assets/units/player.png"));
		setName("Seacen");
		panel = new Image("assets/panel.png");
	}
	

	public ArrayList<Item> getInventory() {
		return inventory;
	}
    public boolean isWin() {
		return win;
	}
	public boolean isHasElixir() {
		return hasElixir;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public void setHasElixir(boolean hasElixir) {
		this.hasElixir = hasElixir;
	}


	/** Update the player state for a frame
     * @param dir_x  direction to move in x
     * @param dir_y  direction to move in y
     * @param delta  time passed since last frame
     * @param map    map in the world
     * @param action action triggered by the user*/
	public void update(double dir_x, double dir_y, int delta,TiledMap map, char action,World world)
	throws SlickException
	{
		
		if ((getCoolDownTimer()-delta)<0) {
			setCoolDownTimer(0);
		}
		else {
			setCoolDownTimer(getCoolDownTimer()-delta);
		}
		
		//xMove and yMove calculate the pixels movement in a frame for player
		double xMove=(dir_x)*delta*getSpeed();
		double yMove=(dir_y)*delta*getSpeed();
		
		basicMoveCheck(xMove,yMove,world);
		
		//set flipped to true if direction of left-right movement has changed
		if ((dir_x!=prevDir_x) && (dir_x!=0)) {
			flipped=true;
		}
		
		//always update prev direction if movement is not stationary
		if (dir_x!=0) {
			prevDir_x=dir_x;
		}
		
		
		Vector<ArrayList<Integer>> reachables;
		reachables=playerExplore(action,world);
		
		ArrayList<Integer> rch_items = reachables.get(0);
		ArrayList<Integer> rch_units = reachables.get(1);
		
		for (int i:rch_items) {
			itemAdded(world.getItems()[i]);
			
		}
		if (action=='A') {
			attack(rch_units,world.getMonsters());
		}
		if (action=='T') {
			for (int i:rch_units) {
				interactVillager(world.getVillagers()[i]);
			}
		}
		
		
		//System.out.println(getxPos()+", "+getyPos());
		
	}
	
	/** adding item to player's inventory, applying effects */
	public void itemAdded(Item item) {
		inventory.add(item);
		
		item.applyEffect(this);
		
		item.setFound(true);
	}
	
	/** triggering dialogue, apply effects from villagers */
	public void interactVillager(Villager villager) throws SlickException {

		villager.setTimer(0);
		
		villager.effect(this);

	}
	
	/**render the player, always at the centre of the camera(screen)*/
	public void render(Graphics g,World world)
	throws SlickException
	{
		//Translate the graphics so that player could be rendered dependent on his positions
		//flip the image if asked so
		if (flipped) {
			setImage(getImage().getFlippedCopy(true, false));
			flipped=false;
		}
		getImage().drawCentered((float)getxPos(),(float)getyPos());
		
		//Translate the graphics so that player could be rendered dependent on his positions
		g.translate((float)world.getCamera().getMinX(), (float)world.getCamera().getMinY());
		world.renderPanel(g,panel);
		
	}
	
    /** player exploring the world for finding executable targets */
	public Vector<ArrayList<Integer>> playerExplore(char action,World world) {
		
		// A vector that stored reachable item list and unit list
    	Vector<ArrayList<Integer>> reachables = new Vector<ArrayList<Integer>>(2);
    	
    	ArrayList<Integer> rch_units = new ArrayList<Integer>(world.NUM_MONSTERS);
    	ArrayList<Integer> rch_items = new ArrayList<Integer>(world.NUM_ITEMS);
    	
    	// check every not-found-yet item to see if it is reachable, regardless of the action
    	for (int i=0;i<world.getItems().length;i++) {
    		if (world.getItems()[i].isFound()) {
    			continue;
    		}
    		
    		if(distanceTo(world.getItems()[i])<TRIGRANGE){
    			rch_items.add(i);
    		}
		}
		rch_items.trimToSize();
    	
		// if action is attack, check monsters only, else if it is talk, check villager only
    	if (action=='A') {
    		for (int i=0;i<world.getMonsters().length;i++) {
    			if(distanceTo(world.getMonsters()[i])<TRIGRANGE){
    				rch_units.add(i);
    			}
    		}
    		rch_units.trimToSize();
    	}
    	else if (action=='T') {
    		for (int i=0;i<world.getVillagers().length;i++) {
    			if(distanceTo(world.getVillagers()[i])<TRIGRANGE){
    				rch_units.add(i);
    			}
    		}
    		rch_units.trimToSize();
    	}
    	
    	reachables.addElement(rch_items);
    	reachables.addElement(rch_units);
    	return reachables;
    }
	
	
	

}
