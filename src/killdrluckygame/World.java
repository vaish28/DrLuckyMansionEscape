package killdrluckygame;

import java.util.List;

/**
 * This represents the entire world template and provides the interface for the world
 * implementation.
 * It implements methods required for the game play mechanics, getter methods for displaying the
 * world details.
 */
public interface World extends ReadOnlyWorldModel {

  /**
   * Adds a space to the space list of the world.
   *
   * @param space the space to be added.
   */
  void addSpaceToSpaceList(Space space);


  void addComputerPlayer(int maxCapacityComputer, int spaceIndexRandom);

  /**
   * Returns true if the particular space exists in the neighbors list.
   *
   * @param spaceName  space name to be checked if existed in the neighbors list.
   * @param neighNames neighbors list containing names.
   * @return return either true or false.
   */
  boolean isContainsNeighbor(String spaceName, List<String> neighNames);


  /**
   * Returns a string containing the description of the player.
   *
   * @return a string value containing the description of the player
   */
  String playerDescription();

  String evaluateLookAround(Space currentSpace, Player player);

  String evaluatePickItem(List<Item> items, Player player);

  String evaluateNeighboringScores(Space currentSpace, Player player);

  /**
   * Creates a graphical representation of the world.
   * Uses buffered Image to create the graphical representation.
   */
  void createImage();

  /**
   * This function will move the target world character to the next indexed position.
   */
  void moveTargetCharacter();

  /**
   * Adding the mapping of the spaces and the players.
   *
   * @param space  the space to map to.
   * @param player the player to which the space is to be mapped.
   */
  void addMappingOfSpaceAndPlayer(Space space, Player player);

  /**
   * This will add a human player to the game.
   *
   * @param playerName    the name of the player
   * @param maxItemsCarry the max items the player can carry
   * @param spaceName     the space name to move in.
   */
  void addHumanPlayer(String playerName, int maxItemsCarry, String spaceName);

  /**
   * Moves the current player to the specified space.
   *
   * @param spaceName the name of the space to move to.
   * @return true if the move was successful, false otherwise.
   */
  boolean move(String spaceName);

  /**
   * Returns a list of spaces adjacent to the specified space.
   *
   * @return a list of neighboring spaces.
   */
  String lookAround();


  /**
   * Checks if the space has any items to pick.
   *
   * @param space the space for which the item is to be checked.
   * @return a true or false value indicating if item can be picked.
   */
  boolean checkSpaceContainsItemsToPick(Space space);

  /**
   * Picks up an item with the specified name from the current space.
   *
   * @param itemName the name of the item to pick up.
   * @return true if the item was picked up successfully, false otherwise.
   */
  boolean pickItem(String itemName);

  /**
   * Returns a string with information about the specified space.
   *
   * @param space the space to get information for.
   * @return a string with space information.
   */
  String printSpaceInfo(Space space);

  /**
   * Advances the turn to the next player int the player list.
   */
  void nextTurn();

  /**
   * Returns a boolean value indicating if input is necessary from the user to take the next step.
   *
   * @return true if input is needed, false otherwise.
   */
  boolean isToPromptForInput();

  /**
   * Returns a list of string representations of the neighbors of a space.
   *
   * @param neigh the list of neighboring spaces.
   * @return a list of string representations of the neighbors.
   */

  List<String> printNeighbors(List<Space> neigh);


  /**
   * Moves the pet to the specified space.
   *
   * @param petMoveToSpace The space to move the pet to.
   */
  void petMove(String petMoveToSpace);

  /**
   * return the space of the pet.
   *
   * @return a space object representing the location of the pet.
   */
  Space getPetSpace();

  /**
   * Performs a computer attack on the target character.
   */
  void performComputerAttack();

  /**
   * Returns a list of items which are evidence of attack on target character.
   *
   * @return a list of items
   */
  List<Item> getEvidenceList();

  /**
   * Checks if the player is seen by other player.
   *
   * @return boolean value indicating the visibility.
   */

  boolean isPlayerSeen();

  /**
   * Attacks the human player with the specified item.
   *
   * @param itemName The name of the item to attack with.
   * @return boolean value indicating if attack was successful.
   */
  boolean attackHuman(String itemName);

  /**
   * Increases the number of turns in the game.
   */
  void increaseNumberOfTurns();

  /**
   * Checks if the target character exists in the same room as that of the current player.
   *
   * @param player current player object
   * @return returns true or false depending on the condition
   */
  boolean checkIfTargetCharacterInSameSpace(Player player);

  /**
   * Changes the previous action to new Action.
   *
   * @param newAction sets the new action
   */
  void changePrevAction(ActionType newAction);


}


