import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Requester type villager */
public class Requester extends Villager {
	
	private String[] messages = new String[2];
	
	public Requester(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setImage(new Image("assets/units/prince.png"));
		setName("Prince Aldric");
		messages[0]="Please seek out the Elixir of Life to cure the king.";
		messages[1]="The elixir! My father is cured! Thankyou!";
	}
	
	@Override
	public void effect(Player player) {
		
		if (player.isHasElixir()) {
			setActualMSG(messages[1]);
			for (Item item:player.getInventory()) {
				if (item.getName().equals("Elixir of Life")) {
					player.getInventory().remove(item);
					break;
				}
			}
			player.setWin(true);
		}
		else {
			setActualMSG(messages[0]);
		}

	}

}
