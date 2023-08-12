package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;


/**
 * The MovePlayerCommand class represents a game operation command to move the player to a
 * specified space in the game world.
 * It allows the player to change their current position within the game world.
 */
public class MovePlayerCommand implements GameOperationCommand {

  private final World world;
  private final String spaceName;

  private final Appendable out;

  /**
   * Constructs a MovePlayerCommand object with the specified game world and space name.
   *
   * @param world the game world in which the player will be moved.
   * @param space the name of the space to which the player will be moved.
   * @param out   the appendable object to print the output.
   */

  public MovePlayerCommand(World world, String space, Appendable out) {
    this.world = world;
    this.spaceName = space;
    this.out = out;
  }

  /**
   * Executes the move player operation by attempting to move the player to the specified space.
   * If the space name is invalid or the move is not possible, an IllegalArgumentException is
   * thrown.
   */
  @Override
  public String execute() {
    StringBuilder sb = new StringBuilder();
    if (!world.move(this.spaceName)) {
      throw new IllegalArgumentException("Invalid space name entered!");
    } else {
      try {
        out.append("Player moved successfully");
        sb.append("Player moved successfully");
        if (world.getPlayers().size() != 0) {
          out.append(world.getCurrentPlayer().getPlayerDescription());
          sb.append(world.getCurrentPlayer().getPlayerDescription());
          world.moveTargetCharacter();
          world.nextTurn();
          out.append(world.displayCurrentPlayerInfo());
          sb.append(world.displayCurrentPlayerInfo());
        }

      } catch (IOException ex) {
        ex.getMessage();

      }

    }
    return sb.toString();
  }
}
