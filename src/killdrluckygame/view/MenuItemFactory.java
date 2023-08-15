package killdrluckygame.view;


import javax.swing.JMenuItem;
/**
 * The MenuItemFactory interface represents a factory for creating menu items with specific labels
 * and action commands.
 */

public interface MenuItemFactory {

  /**
   * Handles the action associated with the provided command.
   *
   * @param command The action command associated with the menu item.
   */
  void handleMenuItemAction(String command);
}
