import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Bandit monster */
public class Bandit extends AggMonster {

	public final int HP_F=40,MAX=40,DAMAGE=8,COOLDOWN=200;
	
	public Bandit(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		setImage(new Image("assets/units/bandit.png"));
		setName("Bandit");
	}

}
