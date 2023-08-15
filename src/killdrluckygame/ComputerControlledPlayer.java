package killdrluckygame;

import java.util.Objects;

/**
 * The {@code ComputerControlledPlayer} class extends the {@code AbstractPlayer} class and
 * represents a computer-controlled player in the game.
 * It provides methods to retrieve player information and determine if the player is
 * computer-controlled.
 */

public class ComputerControlledPlayer extends AbstractPlayer {

  private String playerDescription;

  /**
   * Constructs a {@code ComputerControlledPlayer} object with the specified parameters.
   *
   * @param playerName       the name of the computer-controlled player.
   * @param maxCarryCapacity the maximum number of items the player can carry.
   */
  public ComputerControlledPlayer(String playerName, int maxCarryCapacity) {
    super(playerName, maxCarryCapacity);
    this.playerDescription = "";
  }

  /**
   * Checks if the player is computer-controlled.
   *
   * @return {@code true} since the player is computer-controlled.
   */

  @Override
  public boolean isComputerControlled() {
    return true;
  }

  @Override
  public boolean isHumanControlled() {
    return false;
  }

  /**
   * Returns a description of the computer-controlled player.
   *
   * @return a string containing the player information, including name, turns exhausted, turns
   *         taken, items in hand, and space information.
   */

  @Override
  public String getPlayerDescription() {
    playerDescription = String.format("Computer Controlled Player Information: (Player Name = %s, "
                    + "Items in hand = %s)",
            this.getName(),
            this.getItems());
    return playerDescription;
  }

  /**
   * Checks if this ComputerControlledPlayer is equal to the specified object.
   * Two players are considered equal if they have the same name and maximum carry capacity.
   *
   * @param o the object to compare against.
   * @return true if the objects are equal, false otherwise.
   */


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Player)) {
      return false;
    }
    Player that = (Player) o;

    return this.getMaxItemsCarry() == that.getMaxItemsCarry()
            && (this.getName().equals(that.getName()));
  }

  /**
   * Generates a hash code for this ComputerControlledPlayer.
   * The hash code is computed based on the player's name and maximum carry capacity.
   *
   * @return the hash code value for this HumanControlledPlayer.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getName(), this.getMaxItemsCarry());
  }

}
