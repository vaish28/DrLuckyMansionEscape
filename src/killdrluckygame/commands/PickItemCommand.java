package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;

/**
 * The PickItemCommand class represents a game operation command to pick an item from the game
 * world.
 * It allows the player to pick up an item and add it to their inventory.
 */
public class PickItemCommand implements GameOperationCommand {

  private final World world;
  private final String itemName;
  private final Appendable out;
  private final boolean hasItems;

  /**
   * Constructs a PickItemCommand object with the specified game world and item name.
   *
   * @param world    the game world from which the item will be picked.
   * @param itemName the name of the item to be picked.
   * @param out      the appendable object to print the output.
   */
  public PickItemCommand(World world, String itemName, Appendable out, boolean hasItems) {
    this.world = world;
    this.itemName = itemName;
    this.out = out;
    this.hasItems = hasItems;
  }

  /**
   * Executes the pick item operation by attempting to pick the specified item from the game world.
   * If the item name is invalid or the maximum capacity for carrying items is exhausted, an
   * IllegalArgumentException is thrown.
   */

  @Override
  public String execute() {
    StringBuilder sb = new StringBuilder();
    if (hasItems) {
      if (!world.pickItem(this.itemName)) {
        throw new IllegalArgumentException("Invalid item name entered or max capacity exhausted!");
      } else {
        try {
          out.append("Item picked successfully");
          sb.append("Item picked successfully");
          if (world.getPlayers().size() != 0) {
            out.append(world.getCurrentPlayer().getPlayerDescription());
            sb.append(world.getCurrentPlayer().getPlayerDescription());
            world.moveTargetCharacter();
            world.nextTurn();
          }

        } catch (IOException ex) {
          ex.getMessage();

        }
      }
    } else {
      try {
        out.append("No items to pick in this space!");
        sb.append("Item not picked successfully");
        world.moveTargetCharacter();
        world.nextTurn();
      } catch (IOException ex) {
        ex.getMessage();
      }
    }
  return sb.toString();
  }
}
