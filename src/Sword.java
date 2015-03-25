import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** sword type item */
public class Sword extends Item {

	public Sword(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setEffect(30);
		setImage(new Image("assets/items/sword.png"));
		setName("Sword of Strength");
	}

	@Override
	public void applyEffect(Player player) {
		player.setStats(1,player.getStats()[1]+getEffect());

	}

}
