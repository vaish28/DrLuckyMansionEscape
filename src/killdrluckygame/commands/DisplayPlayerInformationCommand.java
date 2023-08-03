package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;


/**
 * Command class to display information about a specific player in the game.
 */
public class DisplayPlayerInformationCommand implements GameOperationCommand {

  private final World world;
  private final String playerName;
  private final Appendable out;

  /**
   * Constructs a DisplayPlayerInformationCommand.
   *
   * @param world      the game world object.
   * @param playerName the name of the player to display information about.
   * @param out        An appendable object for saving the output.
   */
  public DisplayPlayerInformationCommand(World world, String playerName, Appendable out) {
    this.world = world;
    this.playerName = playerName;
    this.out = out;
  }

  /**
   * Executes the command to display information about the specified player.
   */
  @Override
  public void execute() {

    if (world.getPlayerByPlayerName(playerName) != null) {
      try {
        StringBuilder sb = new StringBuilder("Current player information ");
        sb.append(world.getPlayerDescriptionFromUsername(playerName));
        sb.append("\n");
        out.append(sb.toString());
      } catch (IOException ex) {
        ex.getMessage();
      }
    } else {
      try {
        out.append("Enter correct player name");
      } catch (IOException ex) {
        ex.getMessage();
      }

    }
  }
}
