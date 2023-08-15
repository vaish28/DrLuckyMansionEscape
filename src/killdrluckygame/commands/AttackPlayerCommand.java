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
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null");
    }
    if (option == null) {
      throw new IllegalArgumentException("Attack option cannot be null");
    }
    if (out == null) {
      throw new IllegalArgumentException("Appendable (out) cannot be null");
    }
    this.option = option;
    this.out = out;
    this.world = world;
  }

  @Override
  public String execute() {
    boolean attackSuccessful = false;
    StringBuilder sb = new StringBuilder();
    try {
      if (("poke").equals(option)) {
        out.append("No item to pick up hence poking!");
        sb.append("No item to pick up hence poking!");
        attackSuccessful = world.attackHuman(option);
      } else {
        if (world.getCurrentPlayer().isHumanControlled()) {
          attackSuccessful = world.attackHuman(option);
        }
      }

      if (attackSuccessful) {
        out.append("Attack successful!").append("\n");
        sb.append("Attack successful!").append("\n");
        world.moveTargetCharacter();
        world.nextTurn();

      } else if ((world.isPlayerSeen())
              && (!(world.getCurrentPlayerSpace(world.getCurrentPlayer())
              .equals(world.getCurrentSpaceTargetIsIn())))) {
        out.append("Attack not successful! Target Character not in same room").append("\n");
        sb.append("Attack not successful!").append("\n");
        sb.append("Target Character not in same room").append("\n");
        sb.append("Player is also seen by other players!").append("\n");
        world.moveTargetCharacter();
        world.nextTurn();
      } else if ((world.isPlayerSeen())) {
        sb.append("Player is seen by other players!").append("\n");
        world.moveTargetCharacter();
        world.nextTurn();
      } else {
        sb.append("Attack not successful!").append("\n");
        sb.append("Target Character not in same room").append("\n");
        world.moveTargetCharacter();
        world.nextTurn();
      }

    } catch (IOException ex) {
      ex.getMessage();
    }
    return sb.toString();
  }
}

