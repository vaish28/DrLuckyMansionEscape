package killdrluckygame.view;

/**
 * The NewGameActionStrategy class implements the MenuItemActionStrategy interface and represents
 * a strategy for executing the action of starting a new game with a new world specification.
 */
public class NewGameActionStrategy implements MenuItemActionStrategy {

  private WorldViewInterface view;

  /**
   * Constructs a NewGameActionStrategy instance.
   *
   * @param view The WorldViewInterface instance associated with the strategy.
   */

  public NewGameActionStrategy(WorldViewInterface view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
  }


  @Override
  public void executeAction() {
    // Implement logic to start a new game with a new world specification
    view.startNewGameWithNewWorld();
  }
}
