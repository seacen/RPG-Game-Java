import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** Monster in game */
public abstract class Monster extends Unit {
	
	public Monster(double xPos, double yPos)
	throws SlickException
	{
		super(xPos, yPos);
	}
	
	public abstract void update(int delta,World world) throws SlickException;
	
	/** render monster and its health bar */
	public void render(Graphics g,World world)
	throws SlickException
	{
		getImage().drawCentered((float)getxPos(),(float)getyPos());
		world.renderHealthBar(g, this);
	}
	
	/** the moving algorithm
	 * @param xdiff x difference between the monster and player
	 * @param ydiff y difference between the monster and player
	 * @param delta second passed since last frame
	 * @param world the world object representing the game*/
	public void move(double xDiff, double yDiff,int delta,World world) {
		double totalDis = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		double xDis=xDiff/totalDis*getSpeed()*delta;
		double yDis=yDiff/totalDis*getSpeed()*delta;
		
		basicMoveCheck(xDis,yDis,world);

	}
}
