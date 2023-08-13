package killdrluckygame.commands;

import java.io.IOException;

import killdrluckygame.World;


/**
 * The LookAroundCommand class represents a game operation command to look around the game world.
 * It allows the player to observe the neighboring spaces and obtain information about them.
 */
public class LookAroundCommand implements GameOperationCommand {

  private final World world;
  private final Appendable out;


  /**
   * Constructs a LookAroundCommand object with the specified game world.
   *
   * @param world the game world to perform the look around operation.
   * @param out   the Appendable object to print the output.
   */
  public LookAroundCommand(World world, Appendable out) {
    this.world = world;
    this.out = out;

  }

  /**
   * Executes the look around operation by obtaining information about neighboring spaces.
   * The information is printed to the console.
   */
  @Override
  public String execute() {
    String lookAroundString = world.lookAround();
    StringBuilder sb = new StringBuilder();
    if (lookAroundString != null) {
      try {
        out.append("Looking around neighbours").append("\n");
        sb.append("Looking around neighbours").append("\n");
        out.append(lookAroundString);
        sb.append(lookAroundString);
        world.increaseNumberOfTurns();
        world.moveTargetCharacter();
        world.nextTurn();

      } catch (IOException ex) {
        ex.getMessage();
      }
    } else {
      sb.append("Look around not successful");
//      world.moveTargetCharacter();
//      world.nextTurn();
    }
    return sb.toString();
  }
}
