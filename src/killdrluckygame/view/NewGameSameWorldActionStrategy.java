package killdrluckygame.view;

import killdrluckygame.view.WorldViewImpl;

public class NewGameSameWorldActionStrategy implements MenuItemActionStrategy {

  private WorldViewImpl view;

  public NewGameSameWorldActionStrategy(WorldViewImpl view) {
    this.view = view;
  }

  @Override
  public void executeAction() {
    // Implement logic to start a new game with the current world specification
    view.startNewGameWithSameWorld();
  }
}
