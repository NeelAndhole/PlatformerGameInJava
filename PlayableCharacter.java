import java.io.File;

/**
 * defines the behavior of the playable characters in the game
 */
public class PlayableCharacter extends GenericGameObject {

  private static final String[] IMG_PATHS =
      new String[] {"ImageSprites" + File.separator + "CharacterWalkLeft.png",
          "ImageSprites" + File.separator + "CharacterWalkRight.png",
          "ImageSprites" + File.separator + "CharacterJumpLeft.png",
          "ImageSprites" + File.separator + "CharacterJumpRight.png"};
  // the 4 different poses of the character, left walk, right walk, left jump, right jump

  private int currentPose;// index of the current pose

  private boolean isOnGround; // should not be used to check collisions, it really is just so that
                              // there is a record of when the character is on the ground and not in
                              // the air

  private static final int[] CHARACTER_DIMENSIONS = new int[] {20, 40}; // dimensions of the
                                                                        // character
  private static final float ERROR_FROM_COLLISIONS = 10;
  private static final float JUMP_LIFT_OFF = -12; // the initial velocity of the jump

  /**
   * constructs the playableCharacter
   * 
   * @param x - initial x coordinate
   * @param y - initial y coordinate
   */
  public PlayableCharacter(float x, float y) {
    super(x, y, IMG_PATHS[0]);
    currentPose = 0;
  }



  /**
   * getter for the is on Ground variable
   * 
   * @return true if the character has landed on the ground and hasn't jumped
   */
  public boolean wasOnGround() {
    return isOnGround;
  }

  /**
   * functionality for making the character jump in game
   * 
   */
  public void jump() {
    this.isOnGround = false;
    this.setYVelocity(JUMP_LIFT_OFF);
  }

  /**
   * setter for the isOnGround field
   * 
   * @param isOnGround
   */
  public void setIsOnGround(boolean isOnGround) {
    this.isOnGround = isOnGround;
  }

  /**
   * functionality for when a jump ends
   */
  public void land() {
    this.isOnGround = true;
    this.setYVelocity(0);
  }

  /**
   * updates the pose to one of the 4 preset options 0-3
   * 
   * @param x- the integer between 0 and 3
   */
  public void updatePose(int x) {
    if (x > 3 || x < 0) {
      throw new IllegalArgumentException("Not legal pose");
    }
    currentPose = x;
  }

  /**
   * this function checks whether this character has landed on the ground parameter and updates the
   * on ground parameter
   * 
   * @param ground - the ground object that needs to be checked
   * @return - true if the hitboxes collide, the character is moving down or peaking in y velocity
   *         and the top of the ground is below the character
   */
  public boolean hasLandedOn(Ground ground) {
    if (this.getHitBox().doesCollide(ground.getHitBox()) && this.getYvelocity() >= 0 && this.getY()
        + this.getHeight() / 2 <= ground.getY() - ground.getHeight() / 2 + ERROR_FROM_COLLISIONS) {
      return true;
    }
    return false;
    // the error is added to account for the chance that the character lands weird
  }


  /**
   * sets the correct image and then draws it on screen
   */
  @Override
  public void draw() {
    this.image = processing.loadImage(IMG_PATHS[currentPose]);
    this.image.resize(PlayableCharacter.CHARACTER_DIMENSIONS[0],
        PlayableCharacter.CHARACTER_DIMENSIONS[1]);
    this.getHitBox().changeDimensions(this.image.width, this.image.height);
    super.draw();
  }

}
