/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: <Your name> <Your login>
 */

import org.newdawn.slick.SlickException;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{

    /** The unit this camera is following */
    private Player unitFollow;
    
    /** The width and height of the screen */
    /** Screen width, in pixels. */
    public final int screenwidth;
    /** Screen height, in pixels. */
    public final int screenheight;

    
    /** The camera's position in the world, in x and y coordinates. */
    private double xPos;
    private double yPos;

    
    public double getxPos() {
        // TO DO: Fill In
    	return xPos;
    }

    public double getyPos() {
        // TO DO: Fill In
    	return yPos;
    }

    
    /** Create a new World object. */
    public Camera(int screenwidth, int screenheight)
    {   
        // TO DO: Fill In
    	this.screenwidth=screenwidth;
    	this.screenheight=screenheight;
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     */
    public void update()
    throws SlickException
    {
        // TO DO: Fill In
    	xPos=unitFollow.getxPos();
    	yPos=unitFollow.getyPos();
    	
    }
    
    /** Returns the minimum x value on screen 
     */
    public double getMinX(){
        //since xPos is the centred point, the minX should be calculated as below
    	return xPos-(screenwidth/2);
    }
    
    /** Returns the maximum x value on screen 
     */
    public double getMaxX(){
        // TO DO: Fill In
    	return xPos+(screenwidth/2);
    }
    
    /** Returns the minimum y value on screen 
     */
    public double getMinY(){
        // TO DO: Fill In
    	return yPos-((screenheight-RPG.PANELHEIGHT)/2);
    }
    
    /** Returns the maximum y value on screen 
     */
    public double getMaxY(){
        // TO DO: Fill In
    	return yPos+((screenheight-RPG.PANELHEIGHT)/2);
    }

    /** Tells the camera to follow a given unit. 
     */
    public void followUnit(Object unit)
    throws SlickException
    {
        unitFollow=(Player) unit;
    	xPos=unitFollow.getxPos();
    	yPos=unitFollow.getyPos();
    	
    }
    
}