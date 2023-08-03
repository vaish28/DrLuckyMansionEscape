package killdrluckygame.commands;

import java.io.IOException;
import killdrluckygame.World;

/**
 * The AttackPlayerCommand class represents a command to attack a player in the game.
 */
public class AttackPlayerCommand implements GameOperationCommand {

  private final World world;
  private final String option;
  private final Appendable out;


  /**
   * Constructs an AttackPlayerCommand object.
   *
   * @param world  The game world.
   * @param option The attack option chosen by the player.
   * @param out    The Appendable object to output information.
   */
  public AttackPlayerCommand(World world, String option, Appendable out) {
    this.option = option;
    this.out = out;
    this.world = world;
  }

  @Override
  public void execute() {
    boolean attackSuccessful = false;
    try {
      if (("").equals(option)) {
        out.append("No item to pick up hence poking!");
        attackSuccessful = world.attackHuman(option);
      } else {
        if (world.getCurrentPlayer().isHumanControlled()) {
          attackSuccessful = world.attackHuman(option);
        }
      }

      if (attackSuccessful) {
        out.append("Attack successful!").append("\n");
        world.moveTargetCharacter();
        world.nextTurn();

      } else {
        out.append("Attack unsuccessful!").append("\n");
      }

    } catch (IOException ex) {
      ex.getMessage();
    }

  }
}

