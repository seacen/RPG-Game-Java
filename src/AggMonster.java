import java.util.ArrayList;

import org.newdawn.slick.SlickException;

/** Aggressive monsters that attack player and move toward to him when near */
public abstract class AggMonster extends Monster {
	
	public final int MOVERANGE=150;
	public final double SPEED=0.25;

	public AggMonster(double xPos, double yPos) 
	throws SlickException
	{
		super(xPos, yPos);
		setSpeed(SPEED);
	}
	
	/** update the aggressive monster state for a frame
	 * @param delta     second passed since last frame
	 * @param world     world object representing the game */
	public void update(int delta,World world)
	throws SlickException
	{
		//regular cooldowntimer alter process
		if ((getCoolDownTimer()-delta)<0) {
			setCoolDownTimer(0);
		}
		else {
			setCoolDownTimer(getCoolDownTimer()-delta);
		}
		
		double xPos=getxPos(), yPos=getyPos();
		double P_xPos=world.getPlayer().getxPos(), P_yPos=world.getPlayer().getyPos();
		double xDiff=P_xPos-xPos, yDiff=P_yPos-yPos;
		
		if (this.distanceTo(world.getPlayer())<MOVERANGE) {
			//if in the action-triggering range, create variables to suit the attack method, call attack()
			if (this.distanceTo(world.getPlayer())<TRIGRANGE) {
				ArrayList<Integer> rch_units = new ArrayList<Integer>(1);
				rch_units.add(0);
				Player[] bufferPlayer = new Player[1];
				bufferPlayer[0]=world.getPlayer();
				attack(rch_units,bufferPlayer);
			}
			//else in the range of moving toward to player, call move()
			else {
				move(xDiff, yDiff, delta, world);
			}
		}
	}
}
