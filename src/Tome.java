import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** tome type item */
public class Tome extends Item {

	public Tome(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setEffect(-300);
		setImage(new Image("assets/items/book.png"));
		setName("Tome of Agility");
	}

	@Override
	public void applyEffect(Player player) {
		player.setStats(2,player.getStats()[2]+getEffect());

	}

}
