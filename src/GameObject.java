import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** all objects in game */
public abstract class GameObject  {
	private double xPos,yPos;
	private Image image;
	private String name;
	
	
	public GameObject(double xPos, double yPos) throws SlickException {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	/** render the object
	 * @param g graphics object
	 * @param world world object representing the game */
	public abstract void render(Graphics g,World world) throws SlickException;
	
	/** calculate the distance between the argument object and the object calling the function */
	public double distanceTo(GameObject object) {
		double xDiff=xPos-object.getxPos();
		double yDiff=yPos-object.getyPos();
		
		return Math.sqrt(Math.pow(xDiff, 2)+Math.pow(yDiff, 2));
	}
}
