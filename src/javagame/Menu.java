package javagame;

import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.input.*;

/**
 * Menu - Slick Game State for game menu
 * @author Salvatore Bellassai
 * @version 4-28-15
 */

public class Menu extends BasicGameState{
	
	private final String instructions = ("Arrow up: Move Forward\nArrow left/right: Rotate Ship\nP: Pause Game\nCTRL: Teleport\nSpace: Fire Missile");
	private Sound background;
	private List<XYObject> bullets = new LinkedList<XYObject>();
	private List<XYObject> smallAst = new LinkedList<XYObject>();
	private List<XYObject> medAst = new LinkedList<XYObject>();
	private List<XYObject> largeAst = new LinkedList<XYObject>();
	private Random rand = new Random();
	private Image smallAsteroid, medAsteroid, largeAsteroid;
	
	public Menu(int state){
	}//end constructor
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		background = new Sound("res/menu.wav");
		smallAsteroid = new Image("res/small.png");
		medAsteroid = new Image("res/med.png");
		largeAsteroid = new Image("res/large.png");
		addLargeAst();
		addLargeAst();
		addMedAst();
		addMedAst();
		addMedAst();
		addMedAst();
		addSmallAst();
		addSmallAst();
		addSmallAst();
		addSmallAst();
		addSmallAst();
		addSmallAst();
	}//end init method
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		for (XYObject asteroid: smallAst){
			if(asteroid == null)
				return;
			else
				g.drawImage(smallAsteroid, asteroid.getX(), asteroid.getY());
		}//end smallAst for
		for (XYObject asteroid: medAst){
			if(asteroid == null)
				return;
			else
				g.drawImage(medAsteroid, asteroid.getX(), asteroid.getY());
		}//end medAst for
		for (XYObject asteroid: largeAst){
			if(asteroid == null)
				return;
			else
				g.drawImage(largeAsteroid, asteroid.getX(), asteroid.getY());
		}//end largeAst for
		g.drawRect((Game.getX() / 2) - 75, Game.getY() -100, 150, 35);
		g.drawString("Start Game", (Game.getX() / 2) - 45 ,Game.getY() -92);
		g.drawString(instructions,100,100);
		
	}//end render method
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{

		if(!background.playing())
			background.play();
		Input input = gc.getInput();
		int xpos = Mouse.getX();//get mouse x coordinate
		int ypos = Mouse.getY();//get mouse y coordinate
		if((xpos > ((Game.getX() / 2) - 75) &&xpos<((Game.getX() / 2) + 75))&&(ypos>(Game.getY() - (Game.getY() - 60)) &&ypos<(Game.getY() - (Game.getY() - 100)))){ //run if mouse is within oval
			if(input.isMouseButtonDown(0)){//run if mouse is left clicked (0= left click)
				sbg.enterState(1); //enter play state
				background.stop();
			}//end inner if
			
		}//end outer if
		updateAsteroids(delta);
	}//end update method
	
	public int getID(){
		return 0;
	}//end getID method
	/**
	 * generate a new large asteroid in random position
	 */
	private void addLargeAst(){
		largeAst.add(new XYObject(rand.nextInt(Game.getX()), rand.nextInt(Game.getY()), rand.nextInt(360),
				11,0,0,0));
	}//end addLargeAst
	/**
	 * generate a new medium asteroid at XY of destroyed large asteroid
	 * @param temp
	 */
	private void addMedAst(){
		medAst.add(new XYObject(rand.nextInt(Game.getX()), rand.nextInt(Game.getY()), rand.nextInt(360),
				11,0,0,0));
	}//end addMedAst
	/**
	 * generate a new small asteroid at XY of destroyed medium asteroid
	 * @param temp
	 */
	private void addSmallAst(){
		smallAst.add(new XYObject(rand.nextInt(Game.getX()), rand.nextInt(Game.getY()), rand.nextInt(360),
				11,0,0,0));
	}//end addSmallAst
	/**
	 * update positions of asteroids
	 * @param delta
	 */
	private void updateAsteroids(int delta){
		
		Iterator<XYObject> largeAstIter = largeAst.iterator();
		while(largeAstIter.hasNext()){
			XYObject largeTemp = largeAstIter.next();
			if(largeTemp == null)
				return;
			else{
				largeTemp.update(delta);
				if (largeTemp.getY() < (-90)){ //wrap asteroid around screen
					largeTemp.setY(Game.getY() - 7);
				}//end if 
				if (largeTemp.getY() > (Game.getY() - 5)){ //wrap asteroid around screen
					largeTemp.setY(-85);
				}//end if 
				if (largeTemp.getX() < (-90)){ //wrap asteroid around screen
					largeTemp.setX(Game.getX() - 7);
				}//end if
				if (largeTemp.getX() > (Game.getX() - 5)){ //wrap asteroid around screen
					largeTemp.setX(-85);
				}//end if 
			}//end else
		}//end largAst while
		
		Iterator<XYObject> medAstIter = medAst.iterator();
		while(medAstIter.hasNext()){
			XYObject medTemp = medAstIter.next();
			if(medTemp == null)
				return;
			else{
				medTemp.update(delta);
				if (medTemp.getY() < (-90)){ //wrap asteroid around screen
					medTemp.setY(Game.getY() - 7);
				}//end if 
				if (medTemp.getY() > (Game.getY() - 5)){ //wrap asteroid around screen
					medTemp.setY(-85);
				}//end if 
				if (medTemp.getX() < (-90)){ //wrap asteroid around screen
					medTemp.setX(Game.getX() - 7);
				}//end if
				if (medTemp.getX() > (Game.getX() - 5)){ //wrap asteroid around screen
					medTemp.setX(-85);
				}//end if 
			}//end else
		}//end medAst while
		
		Iterator<XYObject> smallAstIter = smallAst.iterator();
		while(smallAstIter.hasNext()){
			XYObject smallTemp = smallAstIter.next();
			if(smallTemp == null)
				return;
			else{
				smallTemp.update(delta);
				if (smallTemp.getY() < (-90)){ //wrap asteroid around screen
					smallTemp.setY(Game.getY() - 7);
				}//end if 
				if (smallTemp.getY() > (Game.getY() - 5)){ //wrap asteroid around screen
					smallTemp.setY(-85);
				}//end if 
				if (smallTemp.getX() < (-90)){ //wrap asteroid around screen
					smallTemp.setX(Game.getX() - 7);
				}//end if
				if (smallTemp.getX() > (Game.getX() - 5)){ //wrap asteroid around screen
					smallTemp.setX(-85);
				}//end if 
			}//end else
		}//end medAst while
		
	}//end updateAsteroids
	
}// end Menu class
