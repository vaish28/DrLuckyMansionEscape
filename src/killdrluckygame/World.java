package killdrluckygame;

import java.util.List;

/**
 * This represents the entire world template and provides the interface for the world
 * implementation.
 * It implements methods required for the game play mechanics, getter methods for displaying the
 * world details.
 */
public interface World extends ReadOnlyWorldModel{

  /**
   * Adds a space to the space list of the world.
   *
   * @param space the space to be added.
   */
  void addSpaceToSpaceList(Space space);


  /**
   * Returns the list of items in the specified room using the room name.
   *
   * @param roomName name of the room.
   * @return the item in the room.
   */
  List<Item> getItemsInRoom(String roomName);

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
   * This will return a space from the space name.
   *
   * @param spaceName a string indicating the name of the space.
   * @return a space object corresponding to the space name.
   */
  Space getSpaceFromSpaceName(String spaceName);

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
   * Returns a list of neighboring spaces for the specified space.
   *
   * @param space the space to get neighbors for.
   * @return a list of neighboring spaces.
   */
  List<Space> getNeighbors(Space space);


  /**
   * Returns the current player playing the game.
   *
   * @return the current player playing the game.
   */
  Player getCurrentPlayer();

  /**
   * Returns a list of all players in the world.
   *
   * @return a list of players.
   */
  List<Player> getPlayers();

  /**
   * Returns a boolean value indicating if input is necessary from the user to take the next step.
   *
   * @return true if input is needed, false otherwise.
   */
  boolean isToPromptForInput();


  /**
   * Returns the information about the specified space along with the players present.
   *
   * @param spaceName the name of the space.
   * @return the space information as a string.
   */
  String getSpaceInfoWithPlayer(String spaceName);

  /**
   * Returns the number of turns left.
   *
   * @return the turns in the game.
   */
  int getNumberOfTurns();

  /**
   * Checks if the game has ended.
   *
   * @param maxTurns the maximum number of turns allowed for the game
   * @return true if the game has ended, false otherwise.
   */

  boolean hasGameEnded(int maxTurns);

  /**
   * Get the player description from the name of the player.
   *
   * @param playerName the name of the player.
   * @return a string value indicating the description of the player.
   */
  String getPlayerDescriptionFromUsername(String playerName);

  /**
   * Returns the player with the specified name.
   *
   * @param playerName the name of the player.
   * @return the player object.
   */
  Player getPlayerByPlayerName(String playerName);

  /**
   * Returns a list of string representations of the neighbors of a space.
   *
   * @param neigh the list of neighboring spaces.
   * @return a list of string representations of the neighbors.
   */

  List<String> printNeighbors(List<Space> neigh);

  /**
   * Returns a list of string representations of the neighbors of the current space.
   *
   * @return a list of string representations of the neighbors.
   */
  List<String> getNeighborsStrings();

  /**
   * Returns the current information about the pet.
   *
   * @return The current pet information as a string.
   */
  String getCurrentPetInfo();

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
   * Displays the information of the current player.
   *
   * @return The current player information as a string.
   */
  String displayCurrentPlayerInfo();



  void increaseNumberOfTurns();

  boolean checkIfTargetCharacterInSameSpace(Player player);

  void changePrevAction(ActionType newAction);


  /**
   * Returns the space the specified player is in.
   *
   * @param player The player to get the space for.
   * @return The space the player is in.
   */
  Space getCurrentPlayerSpace(Player player);

  /**
   * Return the previous action performed by the computer player.
   * @return an actiontype object.
   */
  ActionType getPrevActionOfComputer();


}


