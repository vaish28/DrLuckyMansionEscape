package killdrluckygame.view;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class GameMenuItemFactory implements MenuItemFactory {

  private WorldViewImpl parentView;

  public GameMenuItemFactory(WorldViewImpl parentView) {
    this.parentView = parentView;
  }

  @Override
  public JMenuItem createMenuItem(String label, String actionCommand) {
    JMenuItem menuItem = new JMenuItem(label);
    menuItem.setActionCommand(actionCommand);
    menuItem.addActionListener(e -> handleMenuItemAction(e));
    return menuItem;
  }

  private void handleMenuItemAction(ActionEvent event) {
    String command = event.getActionCommand();
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
