package killdrluckygame.view;

import killdrluckygame.view.WorldViewImpl;

public class NewGameActionStrategy implements MenuItemActionStrategy {

  private WorldViewImpl view;

  public NewGameActionStrategy(WorldViewImpl view) {
    this.view = view;
  }

  @Override
  public void executeAction() {
    // Implement logic to start a new game with a new world specification
    view.startNewGameWithNewWorld();
  }
}
