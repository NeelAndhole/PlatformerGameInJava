/**
 * in Game character that will either be the players character or not
 */
public class GenericGameObject {

  protected static processing.core.PApplet processing; // the PApplet that is used to draw these
                                                       // characters
  private float[] coordinates; // the coordinates that the center of the drawing will be drawn at
  protected processing.core.PImage image; // the image of the character that is drawn
  private Hitbox hitbox; // the hitbox of the character
  private float[] velocity; // the speed in each direction of the character


  /**
   * initializes and constructs a game-character
   * 
   * @param xcoordinate
   * @param ycoordinate
   * @param imgPath
   */
  public GenericGameObject(float xcoordinate, float ycoordinate, String imgPath) {
    if (processing == null) {
      throw new IllegalStateException("Processing must be initialized");
    }
    coordinates = new float[] {xcoordinate, ycoordinate};
    this.image = processing.loadImage(imgPath);
    hitbox = new Hitbox(xcoordinate, ycoordinate, image.width, image.height);
    velocity = new float[] {0, 0};
  }

  /**
   * sets the processing of the characters to the parameter
   * 
   * @param applet - the applet that is being set as the processor
   */
  public static void setProcessing(processing.core.PApplet applet) {
    processing = applet;
  }

  /**
   * Getter for the x coordinate of the character
   * 
   * @return the x coordinate
   */
  public float getX() {
    return coordinates[0];
  }

  /**
   * getter for the y coordinate of the character
   * 
   * @return the y coordinate
   */
  public float getY() {
    return coordinates[1];
  }

  /**
   * getter for the x velocity
   * 
   * @return the x velocity
   */
  public float getXvelocity() {
    return velocity[0];
  }

  /**
   * getter for the y velocity
   * 
   * @return the y velocity
   */
  public float getYvelocity() {
    return velocity[1];
  }

  /**
   * setter for the x position
   */
  public void setX(float x) {
    this.coordinates[0] = x;
  }

  /**
   * setter for the Y position
   */
  public void setY(float y) {
    this.coordinates[1] = y;
  }

  /**
   * setter for the x velocity
   */
  public void setXVelocity(float xvel) {
    this.velocity[0] = xvel;
  }

  /**
   * setter for the Y velocity
   */
  public void setYVelocity(float yvel) {
    this.velocity[1] = yvel;
  }

  /**
   * getter for the hitbox variable
   * 
   * @return - the hitbox of the character
   */
  public Hitbox getHitBox() {
    return this.hitbox;
  }

  /**
   * getter for the width of the gameObject
   * 
   * @return - the width of the image
   */
  public int getWidth() {
    return image.width;
  }

  /**
   * getter for the height of the gameObject
   * 
   * @return - the height of the image
   */
  public int getHeight() {
    return image.height;
  }

  /**
   * Moves this GenericGameCharacters Hitbox to the provided x,y-coordinates
   * 
   * @param x - , the new x-coordinate for the center of the GameActor's hitbox
   * @param y - , the new y-coordinate for the center of the GameActor's hitbox
   */
  public void moveHitbox(float x, float y) {
    hitbox.setPosition(x, y);
  }

  /**
   * Draws the image of the GameActor to the screen. (OPTIONAL)Visualize the Hitbox to help with
   * debugging.
   */
  public void draw() {
    processing.image(image, getX(), getY());
    // hitbox.visualizeHitbox(); // TODO remove
  }

  /**
   * updates the position of the object using the current velocities
   */
  public void move() {
    this.setX(this.getX() + this.getXvelocity());
    this.setY(this.getY() + this.getYvelocity());
    moveHitbox(this.getX(), this.getY());
  }


}
