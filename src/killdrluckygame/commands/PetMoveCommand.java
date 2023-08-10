package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;


/**
 * The PetMoveCommand class represents a command to move the pet in the game world.
 */
public class PetMoveCommand implements GameOperationCommand {
  private final World world;
  private final String movePetToSpace;
  private final Appendable out;

  /**
   * Constructs a PetMoveCommand object.
   *
   * @param world          The game world.
   * @param out            The Appendable object to output information.
   * @param movePetToSpace The space to which the pet should move.
   */

  public PetMoveCommand(World world, Appendable out, String movePetToSpace) {
    this.world = world;
    this.out = out;
    this.movePetToSpace = movePetToSpace;
  }

  @Override
  public void execute() {
  }
}
