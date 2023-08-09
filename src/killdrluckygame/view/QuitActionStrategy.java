package killdrluckygame.view;

import javax.swing.JOptionPane;
import killdrluckygame.view.WorldViewImpl;

public class QuitActionStrategy implements MenuItemActionStrategy {

  private WorldViewImpl view;

  public QuitActionStrategy(WorldViewImpl view) {
    this.view = view;
  }

  @Override
  public void executeAction() {
    int result = JOptionPane.showConfirmDialog(
            view,
            "Are you sure you want to quit?",
            "Quit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

    if (result == JOptionPane.YES_OPTION) {
      System.exit(0);
    }
  }
}
