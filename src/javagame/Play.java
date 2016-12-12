package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.geom.*;
import java.util.*;

/**
 * Play.java - Play state for the asteroids game
 * @author Salvatore Bellassai
 * @version 4-28-15
 */
public class Play extends BasicGameState{
	
	private Image ship, whiteDot, smallAsteroid, medAsteroid, largeAsteroid, background;
	private final double bulletSpeed = 1.3;//larger number is slower
	private final double asteroidSpeed = 11;//larger number is slower
	private final double shipSpeed = 3;
	private final double rotationSpeed = 2;
	private float shipX = (Game.getX() / 2);
	private float shipY = (Game.getY() / 2);
	private int shipRotation =360;
	private List<XYObject> bullets = new LinkedList<XYObject>();
	private List<XYObject> smallAst = new LinkedList<XYObject>();
	private List<XYObject> medAst = new LinkedList<XYObject>();
	private List<XYObject> largeAst = new LinkedList<XYObject>();
	private int lives, livesFromPoints, levelCounter;
	private int points = 0;
	private Random rand = new Random();
	private Sound shootSound, engineSound, pauseSound;
	private Circle shipCircle;
	private final int bulletRadius = 5;
	private final int bulletOffset = 6;
	private final int smallAstRadius = 14;//xy offset +18,+14
	private final int smallAstXOffset = 18;
	private final int smallAstYOffset = 14;
	private final int medAstRadius = 28;//xy offset +32,+25
	private final int medAstXOffset = 32;
	private final int medAstYOffset = 25;
	private final int largeAstRadius = 70;//xy offset +78,+68
	private final int largeAstXOffset = 78;
	private final int largeAstYOffset = 68;
	private final int MAX_ASTEROIDS = 12;
	private boolean paused = false;
	private static int score;
	private String pauseString = "";
	
	public Play(int state){
		
	}//end constructor
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		ship = new Image("res/ship.png");
		//background = new Image("res/back.jpg");
		whiteDot = new Image("res/whitedot.png");
		shootSound = new Sound("res/shoot.wav");
		engineSound = new Sound("res/engine.wav");
		pauseSound = new Sound("res/pause.wav");
		smallAsteroid = new Image("res/small.png");
		medAsteroid = new Image("res/med.png");
		largeAsteroid = new Image("res/large.png");
		lives = 3;
		addLargeAst();
		livesFromPoints = 0;
		levelCounter = 3;
	}//end init method
	/**
	 * Method to draw objects on the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//g.drawImage(background,0,0);
		for (XYObject bullet: bullets){
			if(bullet == null)
				return;
			else
				g.drawImage(whiteDot, bullet.getX(), bullet.getY());
			
		}//end bullets for
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
		g.drawImage(ship, shipX, shipY);
		g.drawString("Score: " + points, 150, 10);
		g.drawString("Lives: " + lives,300,10);
		g.drawString(pauseString,500,10);
	}//end render method
	/**
	 * Tasks to be performed each time the game updates (delta)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		Input kbInput = gc.getInput();//get keyboard input
		if(kbInput.isKeyDown(Input.KEY_UP)){//detect if up key is pressed and moves up
			shipY -= (delta/shipSpeed)*Math.cos(Math.toRadians(shipRotation));
			shipX += (delta/shipSpeed)*Math.sin(Math.toRadians(shipRotation));//delta/x determines speed
			if(shipY<-20){//wrap ship from top to bottom of screen
				shipY = (Game.getY() - 7);
			}//end inner if
			if(shipY>(Game.getY() - 5)){//wrap ship from bottom to top of screen
				shipY = -10;
			}//end inner if
			if(shipX<-20){//wrap ship from left to right of screen
				shipX = (Game.getX() - 7);
			}//end inner if
			if(shipX>(Game.getX() - 5)){//wrap ship from right to left of screen
				shipX = -12;
			}//end inner if
			ship = new Image("res/ship_fire.png");
			ship.setRotation(shipRotation);
			if(!engineSound.playing())
				engineSound.play();
		}//end if
		else{
			ship = new Image("res/ship.png");
			ship.setRotation(shipRotation);
			engineSound.stop();
		}//end else
		if(kbInput.isKeyDown(Input.KEY_LEFT)){//detect if left key is pressed and rotates left;
			shipRotation -= delta/rotationSpeed;
			ship.setRotation(shipRotation);
			//System.out.println(shipRotation);
		}//end if
		if(kbInput.isKeyDown(Input.KEY_RIGHT)){//detect if right key is pressed and rotates right
			shipRotation += delta/rotationSpeed;//delta/x determines rotation speed
			ship.setRotation(shipRotation);
			//System.out.println(shipRotation);
		}//end if
		if(kbInput.isKeyPressed(Input.KEY_SPACE)){//detect if space key is pressed and fires dot
			XYObject tempBullet = new XYObject(shipX + 16, shipY, shipRotation, 
					bulletSpeed,bulletOffset,bulletOffset,bulletRadius);
			bullets.add(tempBullet);
			shootSound.play();
		}//end if
		if(kbInput.isKeyPressed(Input.KEY_LCONTROL)|| kbInput.isKeyPressed(Input.KEY_RCONTROL)){//detect if control is pressed, teleports
			shipX = rand.nextInt(Game.getX());
			shipY = rand.nextInt(Game.getY());
			shipRotation = rand.nextInt(360);
			ship.setRotation(shipRotation);  
		}//end if
		if(kbInput.isKeyPressed(Input.KEY_P)){ //pause/unpause game if p key is pressed
			pauseSound.play();
			paused = !paused;
			gc.setPaused(paused);
			if(paused == true)
				pauseString = "GAME PAUSED";
			else
				pauseString = "";
		}//end if
		
		shipCircle = new Circle(shipX+22,shipY+18,18);
		
		if(shipRotation >= 360) //keep shipRotation from 0-360
			shipRotation = 0;
		else if(shipRotation < 0)
			shipRotation =  shipRotation + 360;
		
		try{
			checkCollision(delta);
			updateAsteroids(delta); 
			updateBullets(delta);
		}
		catch(Exception e){
			
		}
		
		score = points;
		if(lives < 1)//end game if no lives remaining
			sbg.enterState(2);
		if(smallAst.isEmpty() && medAst.isEmpty() && largeAst.isEmpty()){//add one more asteroid than the last time every time screen is cleared up to MAX_ASTEROIDS
			for(int i = 0; i < levelCounter; ++i){
				addLargeAst();
			}
			if(levelCounter == MAX_ASTEROIDS)
				return;
			else
				++levelCounter;
		}//end if
	}//end update method
	
	public int getID(){
		return 1;
	}//end getID method
	/**
	 * generate a new large asteroid in random position
	 */
	private void addLargeAst(){
		largeAst.add(new XYObject(rand.nextInt(Game.getX()), rand.nextInt(Game.getY()), rand.nextInt(360),
				asteroidSpeed,largeAstXOffset,largeAstYOffset,largeAstRadius));
	}//end addLargeAst
	/**
	 * generate a new medium asteroid at XY of destroyed large asteroid
	 * @param temp
	 */
	private void addMedAst(XYObject temp){
		medAst.add(new XYObject(temp.getX(), temp.getY(), rand.nextInt(360),
				asteroidSpeed,medAstXOffset,medAstYOffset,medAstRadius));
	}//end addMedAst
	/**
	 * generate a new small asteroid at XY of destroyed medium asteroid
	 * @param temp
	 */
	private void addSmallAst(XYObject temp){
		smallAst.add(new XYObject(temp.getX(), temp.getY(), rand.nextInt(360),
				asteroidSpeed,smallAstXOffset,smallAstYOffset,smallAstRadius));
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
				if(largeTemp.intersects(shipCircle)){
					largeAstIter.remove();
					addMedAst(largeTemp);
					addMedAst(largeTemp);
					--lives;
					shipX = (Game.getX() / 2);
					shipY = (Game.getY() / 2);
					shipRotation = 0;
					ship.setRotation(shipRotation);
					points = points + 20;
					checkPoints();
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
				if(medTemp.intersects(shipCircle)){
					medAstIter.remove();
					addSmallAst(medTemp);
					addSmallAst(medTemp);
					--lives;
					shipX = (Game.getX() / 2);
					shipY = (Game.getY() / 2);
					shipRotation = 0;
					ship.setRotation(shipRotation);
					points = points + 50;
					checkPoints();
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
				if(smallTemp.intersects(shipCircle)){
					smallAstIter.remove();
					--lives;
					shipX = (Game.getX() / 2);
					shipY = (Game.getY() / 2);
					shipRotation = 0;
					ship.setRotation(shipRotation);
					points = points + 100;
					checkPoints();
				}//end if 
			}//end else
		}//end medAst while
		
	}//end updateAsteroids
	/**
	 * Update bullets positions
	 * @param delta
	 */
	private void updateBullets(int delta){
		Iterator<XYObject> bulletIter = bullets.iterator();
		while(bulletIter.hasNext()){ //update all bullets in list
			XYObject temp = bulletIter.next();
			if(temp == null)
				return;
			else{
				temp.update(delta);
				if(bulletIter == null)
					return;
				else{ 
					if (temp.getX() > (Game.getX() + 20) || temp.getX() < -20){ //set bullet to null if out of x bound
						bulletIter.remove();
					}//end if
					else if (temp.getY() > (Game.getY() + 20) || temp.getY() < -20){//set bullet to null if out of y bound  
						bulletIter.remove();
					}//end else if
				}//end else
			}//end else
		}//end bulletIter while
	}//end updateBullets
	/**
	 * Check if bullets collide with asteroids
	 * @param delta
	 */
	private void checkCollision(int delta){
		Iterator<XYObject> largeAstIter = largeAst.iterator();
		Iterator<XYObject> medAstIter = medAst.iterator();
		Iterator<XYObject> smallAstIter = smallAst.iterator();
		Iterator<XYObject> bulletIter = bullets.iterator();
		
		while (bulletIter.hasNext()){
			XYObject tempBullet = bulletIter.next();
			if(tempBullet == null)
				return;
			else{
				while(smallAstIter.hasNext()){
					XYObject smallTemp = smallAstIter.next();
					if(smallTemp == null)
						return;
					else if(smallTemp.intersects(tempBullet)){
						smallAstIter.remove();
						bulletIter.remove();
						points = points + 100;
						checkPoints();
					}//end else
				}//end smallAst while
				while(medAstIter.hasNext()){
					XYObject medTemp = medAstIter.next();
					if(medTemp == null)
						return;
					else if(medTemp.intersects(tempBullet)){
						addSmallAst(medTemp);
						addSmallAst(medTemp);
						medAstIter.remove();
						bulletIter.remove();
						points = points + 50;
						checkPoints();
					}//end else
				}//end medAst while
				while(largeAstIter.hasNext()){
					XYObject largeTemp = largeAstIter.next();
					if(largeTemp == null)
						return;
					else if(largeTemp.intersects(tempBullet)){
						addMedAst(largeTemp);
						addMedAst(largeTemp);
						largeAstIter.remove();
						bulletIter.remove();
						points = points + 20;
						checkPoints();
					}//end else
				}//end largeAst while
			}//end else
		}//end bulletIter while
	}//end checkCollision
	/**
	 * Add a life every 1000 points
	 */
	public void checkPoints(){
		
		while(points > (livesFromPoints + 1) * 1000){
			++livesFromPoints;
			++lives;
		}
	}//end checkPoints
	/**
	 * Returns the score for the game
	 * @return score
	 */
	public static int getScore(){
		return score;
	}//end getScore
	
}//end Play class
