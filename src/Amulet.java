import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Amulet Item */
public class Amulet extends Item {

	public Amulet(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setEffect(80);
		setImage(new Image("assets/items/amulet.png"));
		setName("Amulet of Vitality");
	}
	
	public void applyEffect(Player player) {
		player.setStats(0,player.getStats()[0]+getEffect());
	}

}
