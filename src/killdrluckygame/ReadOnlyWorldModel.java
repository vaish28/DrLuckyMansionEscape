package killdrluckygame;

import java.util.List;
import java.util.Map;

/**
 * This a read only verison of the model for the view.
 */
public interface ReadOnlyWorldModel {

  /**
   * Returns the  name of the world.
   *
   * @return string The name of the world as a string.
   */
  String getName();

  /**
   * Returns the total number of rows in the world.
   *
   * @return int The number of rows in the world.
   */
  int getRows();

  /**
   * Returns the total number of columns in the world.
   *
   * @return int The number of columns in the world.
   */
  int getColumns();

  /**
   * Returns the total number of spaces in the world.
   *
   * @return integer value indicating the number of spaces.
   */
  int getTotalSpaces();


  /**
   * Returns the spaces in the world as list of spaces.
   *
   * @return spaces as a list of spaces.
   */
  List<Space> getSpaces();

  /**
   * Returns the current space the target player is in.
   *
   * @return a space interface object.
   */
  Space getCurrentSpaceTargetIsIn();

  /**
   * A function to return the target health.
   *
   * @return an integer values indicating the health of the target character.
   */
  int getTargetHealth();


  /**
   * Returns the details about the target character.
   *
   * @return target details as string.
   */
  String targetCharacterDetails();

  /**
   * Display th potential list of spaces a player can move into.
   *
   * @return string value indicating the list od spaces.
   */
  String displayPotentialListOfSpaces();

  /**
   * Returns the list of items in the specified room using the room name.
   *
   * @param roomName name of the room.
   * @return the item in the room.
   */
  List<Item> getItemsInRoom(String roomName);

  /**
   * This will return a space from the space name.
   *
   * @param spaceName a string indicating the name of the space.
   * @return a space object corresponding to the space name.
   */
  Space getSpaceFromSpaceName(String spaceName);


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
   * Returns the information about the specified space along with the players present.
   *
   * @param spaceName the name of the space.
   * @return the space information as a string.
   */
  String getSpaceInfoWithPlayer(String spaceName);


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

  /**
   * Returns the current information about the pet.
   *
   * @return The current pet information as a string.
   */
  String getCurrentPetInfo();

  /**
   * Returns the map between players and spaces.
   * @return the map between players and spaces.
   */
  Map<Space, List<Player>> getMappingOfSpaceAndPlayer();

  /**
   * Displays the information of the current player.
   *
   * @return The current player information as a string.
   */
  String displayCurrentPlayerInfo();



  /**
   * Returns a list of string representations of the neighbors of the current space.
   *
   * @return a list of string representations of the neighbors.
   */
  List<String> getNeighborsStrings();
}
