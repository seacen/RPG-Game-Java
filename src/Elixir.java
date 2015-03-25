import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Elixir extends Item {

	public Elixir(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setEffect(0);
		setImage(new Image("assets/items/elixir.png"));
		setName("Elixir of Life");
	}

	@Override
	public void applyEffect(Player player) {
		player.setHasElixir(true);

	}

}
