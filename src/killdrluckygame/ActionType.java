package killdrluckygame;

/**
 * The ActionType enum represents the types of actions that can be performed in the game.
 * It is used to specify the action to be taken by the player.
 */
public enum ActionType {

  /**
   * Represents the action of moving to a different location in the game.
   */
  MOVE,
  /**
   * Represents the action of picking up an item in the game.
   */
  PICKUP_ITEM,
  /**
   * Represents the action of looking around the current location in the game.
   */
  LOOK_AROUND,

  MOVE_PET,

  /**
   * Represents the action of attacking the target character in the game.
   */
  ATTACK
}
