import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** the big boss */
public class Draelic extends AggMonster {

	public final int HP_F=140,MAX=140,DAMAGE=30,COOLDOWN=400;
	
	public Draelic(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		setImage(new Image("assets/units/necromancer.png"));
		setName("Draelic");
	}

}
