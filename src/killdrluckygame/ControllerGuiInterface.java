package killdrluckygame;

/**
 * The ControllerGuiInterface interface represents the controller component of the
 * Dr. Lucky game's GUI.
 * It defines methods for controlling and managing the game logic and interactions between
 * the GUI and the game model.
 */
public interface ControllerGuiInterface {

  /**
   * Initiates the gameplay sequence.
   */
  void playGame();

  /**
   * Generates a random maximum capacity for a space.
   *
   * @return A randomly generated maximum capacity value.
   */
  int generateRandomMaxCapacity();

  /**
   * Generates a random first space for a player.
   *
   * @return A randomly generated index representing the first space.
   */
  int generateRandomFirstSpace();

  /**
   * Loads a new game using the specified world file and maximum number of turns.
   *
   * @param worldFileName The name of the world file to load.
   * @param maxTurns      The maximum number of turns for the game.
   */
  void loadNewGame(String worldFileName, int maxTurns);

  /**
   * Resets the game state and prepares for a new game.
   */
  void resetGame();

  /**
   * Advances the target character to the next space.
   */
  void advanceTargetCharacter();

  /**
   * Simulates an action for the given player.
   *
   * @param currentPlayer The player performing the action.
   * @return A message describing the outcome of the action.
   */
  String simulateAction(Player currentPlayer);

  /**
   * Performs a computer player's turn and returns a message describing the action.
   *
   * @return A message describing the action taken by the computer player.
   */
  String computerPlayerTurn();

  /**
   * Processes the input action and its parameters, updating the game state accordingly.
   *
   * @param action     The action to be processed.
   * @param parameters Additional parameters related to the action.
   * @return A message describing the outcome of the action.
   */
  String processInput(String action, String[] parameters);

  /**
   * Checks if a move by the current player to the clicked room is valid.
   *
   * @param currentPlayer The current player.
   * @param clickedRoom   The room the player is attempting to move to.
   * @return True if the move is valid, false otherwise.
   */
  boolean isValidMove(Player currentPlayer, Space clickedRoom);
}
