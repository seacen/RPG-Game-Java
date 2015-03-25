import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Passive monster in game wander around the map and escaping from player under attack */
public class PasMonster extends Monster {
	
	public final int HP_F=40,MAX=40,DAMAGE=0,COOLDOWN=0;
	public final double SPEED=0.2;
	private int[][] PasMoveStrategies = new int[9][2];
	private int startingSec = 0;                           // milliseconds passed from the most recent attack by player
	private boolean attacked = false;                      // 
	private double xMove=0,yMove=0;
	private int sec_sum=0;                                 // milliseconds passed from the last random moving choice made
	
	public PasMonster(double xPos, double yPos) throws SlickException {
		super(xPos, yPos);
		setSpeed(SPEED);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		setImage(new Image("assets/units/dreadbat.png"));
		setName("Giant Bat");
		
	    PasMoveStrategies[0][0]=0;
	    PasMoveStrategies[0][1]=-1;		//Up 0
	    
	    PasMoveStrategies[1][0]=0;		
	    PasMoveStrategies[1][1]=1;		//Down 1
	    
	    PasMoveStrategies[2][0]=-1;
	    PasMoveStrategies[2][1]=0;		//Left 2
	    
	    PasMoveStrategies[3][0]=1;
	    PasMoveStrategies[3][1]=0;		//Right 3
	    
	    PasMoveStrategies[4][0]=-1;
	    PasMoveStrategies[4][1]=-1;		//Up Left 4
	    
	    PasMoveStrategies[5][0]=-1;
	    PasMoveStrategies[5][1]=1;		//Down Left 5
	    
	    PasMoveStrategies[6][0]=1;
	    PasMoveStrategies[6][1]=-1;		//Up Right 6
	    
	    PasMoveStrategies[7][0]=1;
	    PasMoveStrategies[7][1]=1;		//Down Right 7
	    
	    PasMoveStrategies[8][0]=0;
	    PasMoveStrategies[8][1]=0;		//Stationary 8
	}
	
	public boolean isAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	public int getStartingSec() {
		return startingSec;
	}

	public void setStartingSec(int startingSec) {
		this.startingSec = startingSec;
	}
	
	
	/** update the passive monster state for a frame */
	public void update(int delta,World world)
	throws SlickException
	{
		//regular cooldown timer alter process
		if ((getCoolDownTimer()-delta)<0) {
			setCoolDownTimer(0);
		}
		else {
			setCoolDownTimer(getCoolDownTimer()-delta);
		}
		
		double xPos=getxPos(), yPos=getyPos();
		double P_xPos=world.getPlayer().getxPos(), P_yPos=world.getPlayer().getyPos();
		double xDiff=P_xPos-xPos, yDiff=P_yPos-yPos;
		
		
		if (isAttacked()) {
			move(-xDiff,-yDiff,delta,world);
			startingSec+=delta;
		}
		else {
			pasMove(delta,world);
		}
		
		if (startingSec>=5000) {
			attacked=false;
		}
		
	}
	
	/** AI movement for passive monster */
	public void pasMove(int delta,World world) {
		//randomly choose an int from 0 to 9, represent the new moving choice chosen
    	if (sec_sum==0) {
    		Random rand = new Random();
        	int dir = rand.nextInt((8 - 0) + 1) + 0;
    	    xMove=PasMoveStrategies[dir][0]*delta*getSpeed();
    	    yMove=PasMoveStrategies[dir][1]*delta*getSpeed();
    	    sec_sum+=delta;
    	}
		else if (sec_sum>=3000) {
			sec_sum=0;
		}
		else {
			sec_sum+=delta;
		}
    	
    	basicMoveCheck(xMove,yMove,world);
    	
    	
	}

}
