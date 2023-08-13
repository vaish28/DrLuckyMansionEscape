package killdrluckygame.commands;

import java.io.IOException;

import killdrluckygame.World;


/**
 * The GraphicalRepresentationWorldCommand class represents a command to display the graphical
 * representation of the game world.
 * It implements the GameOperationCommand interface and provides an execute() method to execute
 * the command.
 */
public class GraphicalRepresentationWorldCommand implements GameOperationCommand {


  private final World world;
  private final Appendable out;


  /**
   * Constructs a new GraphicalRepresentationWorldCommand object with the specified world.
   *
   * @param world the game world for which the graphical representation will be displayed.
   * @param out   the Appendable object to print the output.
   */
  public GraphicalRepresentationWorldCommand(World world, Appendable out) {
    this.world = world;
    this.out = out;
  }

  /**
   * Executes the command to display the graphical representation of the game world.
   */
  @Override
  public String execute() {
    StringBuilder sb = new StringBuilder();
    try {
      out.append("Displaying the world now!!").append("\n");
      sb.append("Displaying the world now!!").append("\n");
      out.append("Follow the graphical representation of the world of space information");
      sb.append("Follow the graphical representation of the world of space information")
              .append("\n");
      world.createImage();
    } catch (IOException ex) {
      ex.getMessage();
    }
    return sb.toString();

  }
}
