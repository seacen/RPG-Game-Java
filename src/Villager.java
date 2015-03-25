import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** Villager in game */
public abstract class Villager extends Unit {
	
	public final int SPEED=0,HP_F=1,MAX=1,DAMAGE=0,COOLDOWN=0;
	private String actualMSG;
	public final int MSG_DISPLAY_SEC=5000;
	private int timer=MSG_DISPLAY_SEC+1;		//timer for dialogue displaying
	private Font msgFont2;
	
	public Villager(double xPos, double yPos)
	throws SlickException
	{
		super(xPos, yPos);
		setSpeed(SPEED);
		setHP(HP_F);
		setStats(0,MAX);
		setStats(1,DAMAGE);
		setStats(2,COOLDOWN);
		msgFont2 = FontLoader.loadFont("assets/PRISTINA.TTF",25);
	}
	
	public String getActualMSG() {
		return actualMSG;
	}

	public void setActualMSG(String actualMSG) {
		this.actualMSG = actualMSG;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Font getMsgFont2() {
		return msgFont2;
	}

	public void setMsgFont2(Font msgFont2) {
		this.msgFont2 = msgFont2;
	}
	
    
	/** choosing which message to draw and the corresponding effect
	 * @param player  the player controlled by user in game */
	public abstract void effect(Player player);
	
	/** update method designed for dialogue appearing
	 * @param delta time passed since last frame
	 * @param world the world object representing the game */
	public void update(int delta,World world) {
    	if (timer<=MSG_DISPLAY_SEC) {
    		timer+=delta;
    	}
	}
	
	/** render the villager
	 * @param g graphics object
	 * @param world the world object representing the world */
	public void render(Graphics g,World world) throws SlickException {
		getImage().drawCentered((float)getxPos(),(float)getyPos());
		world.renderHealthBar(g, this);
    	if (timer<=MSG_DISPLAY_SEC) {
    	    renderMessage(g,actualMSG,msgFont2);
    	}
		
	}
	
	/** render the dialogue message for villagers
	 * @param g graphics object
	 * @param message the message to be rendered in screen
	 * @param font  the font used for the dialogue */
    public void renderMessage(Graphics g,String message,Font font) throws SlickException {
    	
        double text_x, text_y;         // Coordinates to draw text
        double bar_x,bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);
        
        bar_width = font.getWidth(message)+6;
        bar_height = font.getHeight(message)+3;
        bar_x = getxPos()-(bar_width/2);
        bar_y = getyPos()-(getImage().getHeight()/2)-25-bar_height;
        text_x = bar_x + (bar_width - font.getWidth(message)) / 2;
        text_y = bar_y + (bar_height - font.getHeight(message)) / 2;
        
        g.setColor(BAR_BG);
        g.fillRect((float)bar_x, (float)bar_y, (float)bar_width, (float)bar_height);
        g.setColor(VALUE);
        font.drawString((float)text_x, (float)text_y, message);
    }
}
