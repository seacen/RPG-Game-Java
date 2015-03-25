import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** skeleton type monster */
public class Skeleton extends AggMonster {

	public final int HP_F=100,MAX=100,DAMAGE=16,COOLDOWN=500;
	
	public Skeleton(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		setImage(new Image("assets/units/skeleton.png"));
		setName("Skeleton");
	}

}
