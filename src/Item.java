import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** Item in game that apply effect to player */
public abstract class Item extends GameObject{
	
	private int effect;
	private boolean found=false;
	
	public Item(double xPos, double yPos) throws SlickException {
		super(xPos,yPos);
	}
	
	//getter and setters

	public int getEffect() {
		return effect;
	}
	
	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
	
	public void setEffect(int effect) {
		this.effect = effect;
	}
	
	/** apply the item effect to player
	 * @param player  the player controlled by user */
	public abstract void applyEffect(Player player);
	
	/** render item
	 * @param g     graphics object
	 * @param world world object representing the game */
	public void render(Graphics g,World world)
	throws SlickException
	{
		getImage().drawCentered((float)getxPos(),(float)getyPos());
	}


}
