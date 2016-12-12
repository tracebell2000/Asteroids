package javagame;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Game - Slick game container for state based game
 * @author Salvatore Bellassai
 * @version 4-15-15
 */

public class Game extends StateBasedGame{
	
	private static final String GAME_NAME = "Asteroids";
	private static final int MENU = 0;
	private static final int PLAY = 1;
	private static final int END = 2;
	private final static int xDimension = 1500;
	private final static int yDimension = 900;
	
	public Game(String GAME_NAME){
		super(GAME_NAME);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
		this.addState(new End(END));
	}// end constructor
	
	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(MENU).init(gc, this);
		this.getState(PLAY).init(gc, this);
		this.getState(END).init(gc, this);
		this.enterState(MENU);
	}//end initStateList method

	public static void main(String[] args) {
		AppGameContainer appgc;
		try{
			appgc = new AppGameContainer(new Game(GAME_NAME));
			appgc.setDisplayMode(xDimension, yDimension, false);
			appgc.start();
		}//end try
		catch(SlickException e){
			e.printStackTrace();
		}//end catch

	}//end main method
	/**
	 * return games x dimensions
	 * @return
	 */
	public static int getX(){
		return xDimension;
	}//end getX
	/**
	 * returns games y dimensions
	 * @return
	 */
	public static int getY(){
		return yDimension;
	}//end getY

}//end Game class
