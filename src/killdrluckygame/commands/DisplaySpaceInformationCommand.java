package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;

/**
 * The DisplaySpaceInformationCommand class represents a command to display information about
 * a specific space in the game.
 * It implements the GameOperationCommand interface.
 */
public class DisplaySpaceInformationCommand implements GameOperationCommand {

  private final World world;
  private final String spaceName;
  private final Appendable out;


  /**
   * Constructs a new DisplaySpaceInformationCommand object with the specified world and space name.
   *
   * @param world     The world object representing the game world.
   * @param spaceName The name of the space for which the information will be displayed.
   * @param out       The appendable object for printing.
   */
  public DisplaySpaceInformationCommand(World world, String spaceName, Appendable out) {
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null");
    }
    if (spaceName == null || spaceName.isEmpty()) {
      throw new IllegalArgumentException("Space name cannot be null or empty");
    }
    if (out == null) {
      throw new IllegalArgumentException("Appendable (out) cannot be null");
    }
    this.world = world;
    this.spaceName = spaceName;
    this.out = out;
  }


  @Override
  public String execute() throws IllegalArgumentException {

    StringBuilder sb = new StringBuilder();
    String info = world.getSpaceInfoWithPlayer(this.spaceName);
    if (info != null) {
      try {
        out.append(spaceName).append("\n");
        sb.append(spaceName).append("\n");
        out.append("The Space information ");
        sb.append("\nThe Space information\n ");
        out.append(info).append("\n");
        sb.append(info).append("\n");
        out.append("\n");
      } catch (IOException ex) {
        ex.getMessage();
      }
    } else {
      throw new IllegalArgumentException();
    }
    return sb.toString();
  }
}
