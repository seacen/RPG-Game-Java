import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Guider type villager */
public class Guide extends Villager {
	
	private String[] messages = new String[4];
	
	public Guide(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setImage(new Image("assets/units/peasant.png"));
		setName("Garth");
		messages[0]="Find the Amulet of Vitality, across the river to the west.";
		messages[1]="Find the Sword of Strength - cross the river and back, on the eastside.";
		messages[2]="Find the Tome of Agility, in the Land of Shadows.";
		messages[3]="You have found all the treasure I know of.";
	}

	@Override
	public void effect(Player player) {
		
		boolean hasAmulet=false,hasSword=false,hasTome=false;
		
		for (Item item:player.getInventory()) {
			if (item.getName().equals("Amulet of Vitality")) {
				hasAmulet=true;
			}
			else if (item.getName().equals("Sword of Strength")) {
				hasSword=true;
			}
			else if (item.getName().equals("Tome of Agility")) {
				hasTome=true;
			}
		}
		
		if (!hasAmulet) {
			setActualMSG(messages[0]);
		}
		else if (!hasSword) {
			setActualMSG(messages[1]);
		}
		else if (!hasTome) {
			setActualMSG(messages[2]);
		}
		else {
			setActualMSG(messages[3]);
		}

	}

}
