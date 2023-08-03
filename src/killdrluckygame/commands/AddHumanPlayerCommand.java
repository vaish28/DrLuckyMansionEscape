package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;


/**
 * The {@code AddHumanPlayerCommand} class implements the {@code GameOperationCommand} interface
 * and represents a command to add a human player to the game.
 * It allows the addition of a human-controlled player to the specified world with the
 * given parameters.
 */
public class AddHumanPlayerCommand implements GameOperationCommand {

  private final World world;
  private final String playerName;
  private final int maxItems;
  private final String spaceName;

  private final Appendable out;


  /**
   * Constructs an {@code AddHumanPlayerCommand} object with the specified parameters.
   *
   * @param world      the game world to which the human player will be added.
   * @param playerName the name of the human player.
   * @param maxItems   the maximum number of items the human player can carry.
   * @param spaceName  the name of the space where the human player will be placed.
   * @param out        the appendable object for the output.
   */
  public AddHumanPlayerCommand(World world, String playerName, int maxItems,
                               String spaceName, Appendable out) {
    this.world = world;
    this.maxItems = maxItems;
    this.playerName = playerName;
    this.spaceName = spaceName;
    this.out = out;
  }

  @Override
  public void execute() {

    try {
      world.addHumanPlayer(playerName, maxItems, spaceName);
      out.append("Player has been added successfully").append("\n");
    } catch (IOException ex) {
      ex.getMessage();
    }
  }
}
