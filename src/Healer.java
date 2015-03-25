import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Healer type villager */
public class Healer extends Villager {
	
	private String[] messages = new String[2];
	
	public Healer(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setImage(new Image("assets/units/shaman.png"));
		setName("Elvira");
		messages[0]="Return to me if you ever need healing.";
		messages[1]="You're looking much healthier now.";
	}

	@Override
	public void effect(Player player) {
		
		if (player.getHP()==player.getStats()[0]) {
			setActualMSG(messages[0]);
		}
		else {
			player.setHP(player.getStats()[0]);
			setActualMSG(messages[1]);
		}

	}

}
