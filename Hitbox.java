import processing.core.PApplet;

/**
 * An instantiable class maintains data about Hitboxes used for different objects in the platformer
 * game.
 * 
 * 
 */
public class Hitbox {
  /** the 2D coordinates of the center of this hitbox [x,y] */
  private float[] coordinates;
  /** the width of the box */
  private float width;
  /** the height of the box */
  private float height;
  /** the PApplet that the hitbox can draw on */
  private static PApplet processing;

  /**
   * Creates a new Hitbox object based on the given parameters.
   * 
   * @param x,      the x-coordinate of the center of the hitbox
   * @param y,      the y-coordinate of the center of the hitbox
   * @param width,  the width of the hitbox
   * @param height, the height of the hitbox
   * @throws IllegalStateException if processing is null
   * 
   */
  public Hitbox(float x, float y, float width, float height) {
    if (Hitbox.processing == null)
      throw new IllegalStateException("Processing is null. setProcessing() must be called before "
          + "creating any Hitbox objects.");
    this.coordinates = new float[] {x, y};
    this.width = width;
    this.height = height;
  }

  /**
   * Changes the coordinates of the center of this Hitbox
   * 
   * @param x, the new x-coordinate of the Hitbox's center
   * @param y, the new y-coordinate of the Hitbox's center
   *
   */
  public void setPosition(float x, float y) {
    this.coordinates[0] = x;
    this.coordinates[1] = y;
  }

  /**
   * Detects if this Hitbox and another collide by overlapping.
   * 
   * @param other, the Hitbox to check for if it collides with this one
   * @return true if the 2 Hitboxes overlap, false otherwise
   * 
   */
  public boolean doesCollide(Hitbox other) {
    // implement this method
    float rect1Right = this.coordinates[0] + this.width / 2;
    float rect1Left = this.coordinates[0] - this.width / 2;
    float rect1Top = this.coordinates[1] - this.height / 2;
    float rect1Bottom = this.coordinates[1] + this.height / 2;
    float rect2Right = other.coordinates[0] + other.width / 2;
    float rect2Left = other.coordinates[0] - other.width / 2;
    float rect2Top = other.coordinates[1] - other.height / 2;
    float rect2Bottom = other.coordinates[1] + other.height / 2;

    return (rect1Right > rect2Left && rect1Left < rect2Right && rect1Bottom > rect2Top
        && rect1Top < rect2Bottom); // default return statement to compile, feel free to change
  }

  /**
   * checks whether the collision of two objects is because of the right side of this hitting the
   * left side of other
   * 
   * @param other
   * @return true if the collision meets the specifications
   */
  public boolean doesSideCollideWithSide(Hitbox other) {
    float right = this.width / 2 + this.coordinates[0];
    float left = other.coordinates[0] - other.width / 2;
    return this.doesCollide(other) && right >= left;
  }



  /**
   * Sets the processing for all Hitboxes
   * 
   * @param processing, the instance of a PApplet to draw onto
   * 
   */
  public static void setProcessing(PApplet processing) {
    Hitbox.processing = processing;
  }

  /**
   * Change both the width and height of this Hitbox
   * 
   * @param width,  the new width for the Hitbox
   * @param height, the new height for the Hitbox
   */
  public void changeDimensions(float width, float height) {
    this.height = height;
    this.width = width;
  }

  /**
   * Draws the Hitbox to the screen. Solely for visualization and debugging purposes.
   * 
   */
  public void visualizeHitbox() {
    processing.noFill(); // make the inside of the rectangle clear
    processing.strokeWeight(3); // make the lines thicker
    // draw a rectangle to the screen
    processing.rect(coordinates[0], coordinates[1], width, height);
  }
}
