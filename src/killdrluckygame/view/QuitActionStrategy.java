package killdrluckygame.view;

import javax.swing.JOptionPane;

/**
 * The QuitActionStrategy class implements the MenuItemActionStrategy interface
 * and represents a strategy for executing the action of quitting the game.
 */
public class QuitActionStrategy implements MenuItemActionStrategy {

  private WorldViewInterface view;

  /**
   * Constructs a QuitActionStrategy instance.
   *
   * @param view The WorldViewInterface instance associated with the strategy.
   */
  public QuitActionStrategy(WorldViewInterface view) {
    this.view = view;
  }

  @Override
  public void executeAction() {

    String messageTitle = "Quit Game";
    String message = "Quitting the game now";
    view.displayErrorDialog(messageTitle, message);
    System.exit(0);
  }
}


