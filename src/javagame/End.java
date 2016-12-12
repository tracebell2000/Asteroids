package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.geom.*;
import org.lwjgl.input.*;

/**
 * End - Slick Game State for end of game
 * @author Salvatore Bellassai
 * @version 4-28-15
 */
public class End extends BasicGameState{
	
	private final String gameOver = "GAME OVER!";
	private int score;
	
	public End(int state){
	}//end constructor
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		score = Play.getScore();
	}//end init method
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawString(gameOver, (Game.getX() / 2) - 45 ,Game.getY() / 2);
		g.drawString("Your Score: " + score, (Game.getX() / 2) - 45, (Game.getY() / 2) + 20);
		
	}//end render method
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		score = Play.getScore();
	}//end update method
	
	public int getID(){
		return 2;
	}//end getID method

}// end Menu class
