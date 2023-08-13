package killdrluckygame.view;

/**
 * The NewGameSameWorldActionStrategy class implements the MenuItemActionStrategy interface
 * and represents a strategy for executing the action of starting a new game with the current
 * world specification (same world).
 */
public class NewGameSameWorldActionStrategy implements MenuItemActionStrategy {

  private WorldViewInterface view;

  /**
   * Constructs a NewGameSameWorldActionStrategy instance.
   *
   * @param view The WorldViewInterface instance associated with the strategy.
   */
  public NewGameSameWorldActionStrategy(WorldViewInterface view) {
    this.view = view;
  }

  @Override
  public void executeAction() {
    // Implement logic to start a new game with the current world specification
    view.startNewGameWithSameWorld();
  }
}
