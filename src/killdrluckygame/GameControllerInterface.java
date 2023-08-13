package killdrluckygame;

/**
 * This is an interface for the controller. This will communicate between the model and view.
 */
public interface GameControllerInterface {
  /**
   * This method controls the flow and execution of the game and does the game play.
   *
   * @param game     world object to play the game
   * @param maxTurns the max turns allotted to the game
   */
  void gamePlay(World game, int maxTurns);

  /**
   * Simulates the computer player turn.
   *
   * @param currentPlayer indicates the current computer player
   * @param game          the world object
   * @return a string value indicating the action computer took
   */
  String simulateAction(Player currentPlayer, World game);

  /**
   * Returns the random generated capacity.
   *
   * @return an integer value indicating the random capacity
   */
  int generateRandomMaxCapacity();

  /**
   * Generates the random first space.
   *
   * @param game the world object
   * @return random integer corresponding to the random first space.
   */
  int generateRandomFirstSpace(World game);
}
