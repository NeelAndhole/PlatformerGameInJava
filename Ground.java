import java.io.File;

/**
 * this models the ground in the game and provides a stopping for when the character "lands" after
 * jumping
 */

public class Ground extends GenericGameObject {
  private static final int GROUND_X_VELOCITY = -5; // the speed that the ground moves at

  private static final int GROUND_Y_VELOCITY = 0; // the speed the ground moves in the Y direction
  private static final String GROUND_IMAGE_PATH = "ImageSprites" + File.separator + "Ground.png";
  private final int[] DIMENSIONS; // array with 2 elements, the width and the height ratio should be
                                  // 4 : 1

  /**
   * Constructs the ground objects that move across the screen
   * 
   * @param xpos - the initial X position
   * @param ypos - the initial Y position
   */
  public Ground(int xpos, int ypos) {
    super(xpos, ypos, GROUND_IMAGE_PATH);
    this.setXVelocity(GROUND_X_VELOCITY);
    this.setYVelocity(GROUND_Y_VELOCITY);
    DIMENSIONS = new int[] {40, 10};
  }


  /**
   * Constructs the ground object that is at the bottom of the screen, but more important allows for
   * more versatility in creating ground objects
   * 
   * @param x     - the x position
   * @param y     - the initial y position
   * @param xsize - the width
   * @param ysize - the length
   */
  public Ground(int x, int y, int xsize, int ysize) {
    super(x, y, GROUND_IMAGE_PATH);
    DIMENSIONS = new int[] {xsize, ysize};
  }


  public void draw() {
    this.image = processing.loadImage(GROUND_IMAGE_PATH);
    this.image.resize(DIMENSIONS[0], DIMENSIONS[1]);
    this.getHitBox().changeDimensions(this.image.width, this.image.height);
    super.draw();
  }
}

