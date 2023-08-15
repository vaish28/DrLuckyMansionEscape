package killdrluckygame.view;

/**
 * The GameMenuItemFactory class is responsible for handling menu item actions in the
 * Dr. Lucky Game.
 * It creates and executes the appropriate strategies based on the selected menu item command.
 */
public class GameMenuItemFactory implements MenuItemFactory {

  private WorldViewInterface parentView;

  /**
   * Constructs a GameMenuItemFactory object with the specified parent view.
   *
   * @param parentView The parent view associated with the menu items.
   */
  public GameMenuItemFactory(WorldViewInterface parentView) {
    this.parentView = parentView;
  }


  @Override
  public void handleMenuItemAction(String command) {
    String commandAction = command;
    MenuItemActionStrategy strategy;

    switch (command) {
      case "NewGame":
        strategy = new NewGameActionStrategy(parentView);
        break;
      case "NewGameSameWorld":
        strategy = new NewGameSameWorldActionStrategy(parentView);
        break;
      case "Quit":
        strategy = new QuitActionStrategy(parentView);
        break;
      default:
        return; // No strategy for this command
    }

    strategy.executeAction();
  }
}
