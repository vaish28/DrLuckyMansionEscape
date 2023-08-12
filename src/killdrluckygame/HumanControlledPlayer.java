package killdrluckygame;

import java.util.Objects;

/**
 * The HumanControlledPlayer class represents a player controlled by a human in the game.
 * It extends the AbstractPlayer class and provides specific implementations for
 * human-controlled player behavior.
 */
public class HumanControlledPlayer extends AbstractPlayer {

  private String playerDescription;

  /**
   * Constructs a new HumanControlledPlayer object with the specified player name, maximum turns,
   * and maximum carry capacity.
   *
   * @param playerName       the name of the human-controlled player.
   * @param maxCarryCapacity the maximum number of items the player can carry.
   */
  public HumanControlledPlayer(String playerName, int maxCarryCapacity) {
    super(playerName, maxCarryCapacity);
    this.playerDescription = "";
  }


  @Override
  public boolean isHumanControlled() {
    return true;
  }

  @Override
  public boolean isComputerControlled() {
    return false;
  }


  @Override
  public String getPlayerDescription() {
    playerDescription = String.format("Human Player Information: (Player Name = %s Items in hand "
                    + "= %s)\n", this.getName(),
            this.getItems());
    return playerDescription;
  }

  /**
   * Checks if this HumanControlledPlayer is equal to the specified object.
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
   * Generates a hash code for this HumanControlledPlayer.
   * The hash code is computed based on the player's name and maximum carry capacity.
   *
   * @return the hash code value for this HumanControlledPlayer.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getName(), this.getMaxItemsCarry());
  }

}


