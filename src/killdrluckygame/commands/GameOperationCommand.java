package killdrluckygame.commands;

/**
 * The GameOperationCommand interface represents a command that can be executed in the game.
 * Implementations of this interface define specific game operations and provide the logic for
 * executing those operations. Commands encapsulate a specific action or behavior that can be
 * performed within the game.
 */
public interface GameOperationCommand {

  /**
   * Executes the command.
   * This method is responsible for executing the specific game operation defined by the
   * implementation. The execution may involve modifying the game state, interacting with players
   * or spaces,or performing other game-related actions. Implementations should provide the
   * necessary logic to execute the command and handle any required input or output.
   * It is expected that the execution of the command will have an effect on the game or its
   * components.
   */
  String execute();
}
