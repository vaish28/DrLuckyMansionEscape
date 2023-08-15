package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;

/**
 * The {@code AddComputerPlayerCommand} class implements the {@code GameOperationCommand} interface
 * and represents a command to add a computer player to the game.
 * It allows the addition of a computer-controlled player to the specified world.
 */

public class AddComputerPlayerCommand implements GameOperationCommand {

  private final World world;
  private final Appendable out;
  private final int maxCapacity;
  private final int spaceIndex;


  /**
   * Constructs an {@code AddComputerPlayerCommand} object with the specified world.
   *
   * @param world the game world to which the computer player will be added.
   * @param out   the appendable object for the output.
   */
  public AddComputerPlayerCommand(World world, Appendable out, int maxCapacity, int spaceIndex) {
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null");
    }
    if (out == null) {
      throw new IllegalArgumentException("Appendable (out) cannot be null");
    }
    if (maxCapacity <= 0) {
      throw new IllegalArgumentException("Max capacity must be a positive value");
    }
    if (spaceIndex < 0) {
      throw new IllegalArgumentException("Space index cannot be negative");
    }
    this.world = world;
    this.out = out;
    this.maxCapacity = maxCapacity;
    this.spaceIndex = spaceIndex;
  }

  @Override
  public String execute() {

    StringBuilder sb = new StringBuilder();
    try {
      out.append("Now adding a computer controlled player!").append("\n");
      sb.append("\nNow adding a computer controlled player!").append("\n");
      world.addComputerPlayer(maxCapacity, spaceIndex);
      out.append("Player has been added successfully").append("\n");
      sb.append("\nPlayer has been added successfully").append("\n");
    } catch (IOException ex) {
      ex.getMessage();
    }
    return sb.toString();
  }
}
