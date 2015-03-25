import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Zombie extends AggMonster {

	public final int HP_F=60,MAX=60,DAMAGE=10,COOLDOWN=800;
	
	public Zombie(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		setImage(new Image("assets/units/zombie.png"));
		setName("Zombie");
	}

}
