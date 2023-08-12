package killdrluckygame;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of player interface. It implements the common methods required
 * by the human controlled player and computer controlled player. Allows a player to move, look
 * for neighbors and pick an item.
 */

abstract class AbstractPlayer implements Player {

  private String playerName;
  private int maxItemsCarry;
  private List<Item> itemList;


  /**
   * This is a constructor is for abstract player in a game.
   * It takes in parameters for the player with a name,
   * maximum number of turns, and maximum number of items they can carry.
   *
   * @param playerName    the name of the player.
   * @param maxItemsCarry the maximum number of items the player can carry.
   */

  protected AbstractPlayer(String playerName, int maxItemsCarry) {

    if ("".equals(playerName) || maxItemsCarry < 0) {
      throw new IllegalArgumentException();
    }
    this.playerName = playerName;
    this.maxItemsCarry = maxItemsCarry;
    this.itemList = new ArrayList<>();

  }

  @Override
  public String getName() {
    return this.playerName;
  }

  @Override
  public void pickItem(Item item) {
    itemList.add(item);
  }

  @Override
  public List<Item> getItems() {
    return this.itemList;
  }


  @Override
  public boolean removeItem(Item item) {
    // check if exists and remove if exists
    itemList.remove(item);
    return false;
  }

  @Override
  public boolean isMaxCapacityPickItemExhausted() {
    if (this.itemList.size() == this.maxItemsCarry) {
      return true;
    }
    return false;
  }

  @Override
  public int getMaxItemsCarry() {
    return maxItemsCarry;
  }


  /**
   * Returns whether the player associated with this object is human-controlled.
   *
   * @return {@code true} if the player is human-controlled, {@code false} otherwise.
   */
  @Override
  public boolean isHumanControlled() {
    return false;
  }

  /**
   * Returns whether the player associated with this object is computer-controlled.
   *
   * @return {@code true} if the player is computer-controlled, {@code false} otherwise.
   */
  @Override
  public boolean isComputerControlled() {
    return false;
  }


  /**
   * Returns a description of the player associated with this object.
   * The description provides information about the player.
   *
   * @return a string describing the player.
   */
  @Override
  public abstract String getPlayerDescription();


}
