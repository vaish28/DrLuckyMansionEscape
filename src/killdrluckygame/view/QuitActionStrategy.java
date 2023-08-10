package killdrluckygame.view;

import javax.swing.JOptionPane;
import killdrluckygame.view.WorldViewImpl;

import java.awt.*;

public class QuitActionStrategy implements MenuItemActionStrategy {

  private WorldViewInterface view;

  public QuitActionStrategy(WorldViewInterface view) {
    this.view = view;
  }

  @Override
  public void executeAction() {
    int result = JOptionPane.showConfirmDialog(
            (Component) view,
            "Are you sure you want to quit?",
            "Quit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

    if (result == JOptionPane.YES_OPTION) {
      System.exit(0);
    }
  }
}
