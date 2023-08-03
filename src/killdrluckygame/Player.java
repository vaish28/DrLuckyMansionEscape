package killdrluckygame;

import java.util.List;

/**
 * The Player interface represents a player in the game.
 * It defines methods to retrieve and manipulate player information and actions.
 */
public interface Player {

  /**
   * Returns the name of the player.
   *
   * @return name of the player.
   */
  String getName();


  /**
   * Picks an item for the player.
   *
   * @param item takes in an item object as parameter.
   */
  void pickItem(Item item);

  /**
   * Returns a list of item objects.
   *
   * @return a list of item objects.
   */
  List<Item> getItems();

  /**
   * removes an item from the players list.
   *
   * @param item takes in the item to be removed.
   * @return an boolean value indicating removal of item from item list.
   */
  boolean removeItem(Item item);

  /**
   * Checks if max capacity is exhausted.
   *
   * @return returns true or false depending on whether max capacity is exhausted.
   */

  boolean isMaxCapacityPickItemExhausted();

  /**
   * Returns the description of the player.
   *
   * @return String indicating the description of the player.
   */
  String getPlayerDescription();

  /**
   * Checks if the player is computer controlled.
   *
   * @return true if the player is computer controlled.
   */
  boolean isComputerControlled();

  /**
   * Checks if the player is human controlled.
   *
   * @return true if the player is human controlled.
   */
  boolean isHumanControlled();

  /**
   * returns the maximum amount of items a player can carry.
   *
   * @return maximum value of the items.
   */
  int getMaxItemsCarry();

}
