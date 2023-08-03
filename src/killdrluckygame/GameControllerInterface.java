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

  String simulateAction(Player currentPlayer, World game);

  int generateRandomMaxCapacity();

  int generateRandomFirstSpace(World game);
}
