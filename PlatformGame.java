import java.io.File;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * this class runs the platform game using the PApplet tool
 * 
 * @author neelandhole
 */
public class PlatformGame extends PApplet {

  private PImage backgroundImg; // backround image of the game
  private boolean isGameOver; // updated to end the game
  private ArrayList<GenericGameObject> characters; // the list of the objects in the game
  private static final double GRAVITY_CONSTANT = 0.3; // the gravitational constant that determines
                                                      // how fast gravitational acceleration
                                                      // functions
  private static final float TERMINAL_VELOCITY = 8; // the fastest the character is allowed to move
                                                    // down in the game. Important to reduce
                                                    // clipping IMPORTANT, must be Strictly less
                                                    // than the error from collisions in the
                                                    // Playable characters subclas
  private static final float X_SPEED = 5;
  private static final int MAX_PLATFORMS = 18;
  private static final int PLATFORM_MINIMUM_DISTANCE = 40; // gives the minimum time between
                                                           // platform creation
  private int score;
  private int highScore;
  private int ticksSinceLastPlatform;


  /**
   * Main method of the class, initializes and launches the game
   * 
   * @param args
   */
  public static void main(String[] args) {
    PApplet.main("PlatformGame");
  }

  /**
   * this changes the settings of the window
   * 
   */
  @Override
  public void settings() {
    this.size(800, 600); // sets the height to 600 and the width to 800
  }

  /**
   * ran once at the beginning of the program, initializes variables and constructs objects mostly
   */
  public void setup() {
    this.getSurface().setTitle("Platformer Game"); // set title of window
    this.imageMode(PApplet.CENTER); // images when drawn will use x,y as their center
    this.rectMode(PApplet.CENTER); // rectangles when drawn will use x,y as their center
    this.focused = true; // window is "active" upon start up
    this.textAlign(PApplet.CENTER); // text written to screen will have center alignment
    this.textSize(30); // text is 30 pt



    // sets the processing for each relevant class
    GenericGameObject.setProcessing(this);
    Hitbox.setProcessing(this);


    // initialize variables
    score = 0;
    highScore = 0;
    characters = new ArrayList<GenericGameObject>();
    characters.add(new PlayableCharacter(400, 300));
    characters.add(new Ground(400, 550, 900, 100));

    // sets up the background image of the window
    this.backgroundImg =
        this.loadImage("ImageSprites" + File.separator + "BackgroundImageWithClouds.jpg");
  }

  /**
   * is ran on every tick so it provides all the animations and logic checks
   */
  @Override
  public void draw() {

    if (!isGameOver) {
      // call the image method to set the background, this is done first for the order to make sense
      this.image(backgroundImg, this.width / 2, this.height / 2);

      // call the draw method of each game character
      for (int i = 0; i < characters.size(); i++) {
        characters.get(i).move();// moves them
        characters.get(i).draw();// draws them
      }

      // randomly generate the ground objects coming at the guy
      int randInt = (int) (Math.random() * 100);

      if (randInt < 4 && characters.size() < 2 + MAX_PLATFORMS
          && ticksSinceLastPlatform > PLATFORM_MINIMUM_DISTANCE) {
        characters.add(new Ground(800, 300 + (int) (Math.random() * 100)));
        ticksSinceLastPlatform = 0;
      }

      if (randInt > 97 && characters.size() < 2 + MAX_PLATFORMS
          && ticksSinceLastPlatform > PLATFORM_MINIMUM_DISTANCE) {
        characters.add(new Ground(800, 100 + (int) (Math.random() * 200)));
        ticksSinceLastPlatform = 0;
      }



      // runs the logic
      this.runLogicChecks();
      // increments score and ticks
      score++;
      ticksSinceLastPlatform++;
      // sets highscore to score if score is bigger
      if (this.score > this.highScore) {
        this.highScore = this.score;
      }
      // draw the text in the top left corner

      this.text("Score : " + score, 80, 40);
      this.fill(0, 0, 0);
      this.text("High Score : " + this.highScore, 250, 40);

    } else {
      this.text("GAME OVER\nPress R to play again", this.width / 2, this.height / 2);
      score = 0;
    }
  }

  /*
   * handles the event of when a key is pressed
   */
  public void keyPressed() {
    // moves character to the left
    if (key == 'a' || keyCode == PApplet.LEFT) {
      for (int i = 0; i < characters.size(); i++) {
        if (characters.get(i) instanceof PlayableCharacter
            && (characters.get(i).getX() - characters.get(i).getWidth() / 2 > 0)) {
          characters.get(i).setXVelocity(-1 * X_SPEED);

        }
      }
    }
    // moves character to the right
    else if (key == 'd' || keyCode == PApplet.RIGHT) {
      for (int i = 0; i < characters.size(); i++) {
        if (characters.get(i) instanceof PlayableCharacter
            && (characters.get(i).getX() + characters.get(i).getWidth() / 2 < this.width)) {
          characters.get(i).setXVelocity(X_SPEED);
        }
      }
    }
    // character jumps
    else if (key == 'w' || keyCode == PApplet.UP) {
      for (int i = 0; i < characters.size(); i++) {
        if (characters.get(i) instanceof PlayableCharacter
            && ((PlayableCharacter) characters.get(i)).wasOnGround()) {
          ((PlayableCharacter) characters.get(i)).jump();
        }
      }
    } else if (key == 'r' && isGameOver) {
      isGameOver = false;
      for (int i = characters.size() - 1; i > 1; i--) {
        characters.remove(i);
      }
    }

  }

  /**
   * handles the event of the key being released
   */
  public void keyReleased() {
    if (key == 'a' || key == 'd' || keyCode == PApplet.LEFT || keyCode == PApplet.RIGHT) {
      for (int i = 0; i < characters.size(); i++) {
        if (characters.get(i) instanceof PlayableCharacter) {
          characters.get(0).setXVelocity(0);
        }
      }
    }

  }

  /**
   * runs the in game logic checks
   */
  public void runLogicChecks() {
    // TODO set x velocity of character to zero when the hit the side of the screen
    for (int i = 0; i < characters.size(); i++) {
      if (characters.get(i) instanceof PlayableCharacter
          && (characters.get(i).getX() - characters.get(i).getWidth() / 2 <= 0)) {
        characters.get(i).setXVelocity(0);
        characters.get(i).setX(0 + characters.get(i).getWidth() / 2);
      }

      if (characters.get(i) instanceof PlayableCharacter
          && characters.get(i).getX() + characters.get(i).getWidth() / 2 >= this.width) {
        characters.get(i).setXVelocity(0);
        characters.get(i).setXVelocity(0);
        characters.get(i).setX(this.width - characters.get(i).getWidth() / 2);
      }

    }


    // TODO check if the character has hit the side of one of the ground objects or the bottom of
    // one of the ground objects

    // TODO check if character has hit the top of the ground when moving down
    for (int i = 0; i < characters.size(); i++) {
      if (characters.get(i) instanceof PlayableCharacter) {
        for (int j = 0; j < characters.size(); j++) {
          if (characters.get(j) instanceof Ground && ((PlayableCharacter) characters.get(i))
              .hasLandedOn(((Ground) characters.get(j)))) {


            ((PlayableCharacter) characters.get(i)).land();

          }
        }
      }
    }


    // TODO set y velocity of character to decrement when the character is not touching ground
    {// block to keep local variables
      boolean isTouchingGround = false;
      for (int i = 0; i < characters.size(); i++) {
        if (characters.get(i) instanceof PlayableCharacter) {
          for (int j = 0; j < characters.size(); j++) {
            if (characters.get(j) instanceof Ground && ((PlayableCharacter) characters.get(i))
                .hasLandedOn(((Ground) characters.get(j)))) {
              isTouchingGround = true;
              characters.get(i).setY(characters.get(j).getY() - characters.get(j).getHeight() / 2
                  - characters.get(i).getHeight() / 2);
            }
          }
          if (!isTouchingGround && characters.get(i).getYvelocity() < TERMINAL_VELOCITY) {
            characters.get(i)
                .setYVelocity((float) (characters.get(i).getYvelocity() + GRAVITY_CONSTANT));
          }
        }
      }
    }

    // TODO remove ground from the list of characters when they leave the screen
    for (int i = 0; i < characters.size(); i++) {
      if (characters.get(i) instanceof Ground
          && characters.get(i).getX() + characters.get(i).getWidth() / 2 <= 0) {
        characters.remove(i);
      }
    }

    // call gameOver if the player hits the bottom ground

    for (int i = 0; i < characters.size(); i++) {
      if (characters.get(i) instanceof PlayableCharacter && score > 100
          && characters.get(i).getY() >= 480) {
        this.isGameOver = true;
      }
    }


  }

}
