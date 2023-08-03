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
    this.world = world;
    this.out = out;
    this.maxCapacity = maxCapacity;
    this.spaceIndex = spaceIndex;
  }

  @Override
  public void execute() {

    try {
      out.append("Now adding a computer controlled player!").append("\n");
      world.addComputerPlayer(maxCapacity,spaceIndex);
      out.append("Player has been added successfully").append("\n");
    } catch (IOException ex) {
      ex.getMessage();
    }

  }
}
