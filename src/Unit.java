import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;

/** Basic unit in game */
public abstract class Unit extends GameObject{
	
	private double speed;
	private int[] stats = new int[3];
	public final int TRIGRANGE=50;		//range for actions can be triggered in this game
	private int HP;
	private int coolDownTimer=0;
	private boolean dead=false;
	private double loopMoveDir=1;
	
	public Unit(double xPos, double yPos)
	throws SlickException
	{
		super(xPos,yPos);
	}
	
	//setter and getters
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public double getSpeed() {
		return speed;
	}

	public int[] getStats() {
		return stats;
	}
	public void setStats(int i,int value) {
		stats[i]=value;
	}
	
	public int getCoolDownTimer() {
		return coolDownTimer;
	}

	public void setCoolDownTimer(int coolDownTimer) {
		this.coolDownTimer = coolDownTimer;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}


	
	/** attack method suited for all units
	 * @param rch_units  an array list containing reachable units within the range
	 * @param unit       an array of all same type units in game */
	public void attack(ArrayList<Integer> rch_units,Unit[] unit) {
		
		if (getCoolDownTimer()==0) {
			for (int i : rch_units) {
				//random a damage to apply
		    	Random rand = new Random();
		    	int damage = rand.nextInt((this.stats[1] - 0) + 1) + 0;
		    	
		    	int newHP = unit[i].getHP()-damage;
		    	if (newHP<0) {
		    		newHP=0;
		    		unit[i].setDead(true);
		    	}
		    	if (unit[i].getName().equals("Giant Bat")) {
		    		PasMonster bat = (PasMonster)unit[i];
		    		bat.setStartingSec(0);
		    		bat.setAttacked(true);
		    	}
		    	unit[i].setHP(newHP);
		    }
			setCoolDownTimer(getStats()[2]);
		}
		
	}
	
	/** move check and execution for all units
	 * @param xMove the amount of move in x direction
	 * @param yMove the amount of move in y direction
	 * @param world the world object representing the game */
	public void basicMoveCheck(double xMove, double yMove, World world) {
		
		double xPossibleM=getxPos()+xMove;
		double yPossibleM=getyPos()+yMove;
		
		//Adjust the boundary for better block testing
		if (xMove<0) {
			xPossibleM-=19;
		}
		else if (xMove>0) {
			xPossibleM+=19;
		}
		if (yMove<0) {
			yPossibleM-=3;
		}
		else {
			yPossibleM+=18;
		}
		
		//update unit position if not blocked
		if (!world.terrainBlocks(xPossibleM,yPossibleM)) {
			setxPos(getxPos() + xMove);
			setyPos(getyPos() + yMove);	
		}
		
		// Sticky wall algorithm
		else {
			if (yMove==0) {
				if (!world.terrainBlocks(getxPos(),yPossibleM-yMove+Math.abs(xMove))) {
					setyPos(getyPos() + Math.abs(xMove));
				}
			}
			else if (xMove==0) {
				if (!world.terrainBlocks(getxPos()+loopMoveDir*(Math.abs(yMove)+19),getyPos()+18)) {
					setxPos(getxPos() + loopMoveDir*Math.abs(yMove));
				}
				else if (!world.terrainBlocks(getxPos()-loopMoveDir*(Math.abs(yMove)+19),getyPos()+18)) {
					loopMoveDir*=-1;
					setxPos(getxPos() + loopMoveDir*Math.abs(yMove));
				}
			}
			else {
				if (!world.terrainBlocks(getxPos(),yPossibleM)) {
					setyPos(getyPos() + yMove);	
				}
				else if (!world.terrainBlocks(xPossibleM,getyPos()+18)) {
					setxPos(getxPos() + xMove);
				}
			}
		}
	}

}
