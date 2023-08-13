package killdrluckygame.view;


import javax.swing.JMenuItem;
/**
 * The MenuItemFactory interface represents a factory for creating menu items with specific labels
 * and action commands.
 */

public interface MenuItemFactory {

  /**
   * Creates a new JMenuItem with the provided label and action command.
   *
   * @param label         The label to be displayed on the menu item.
   * @param actionCommand The action command associated with the menu item.
   * @return A new JMenuItem instance.
   */
  JMenuItem createMenuItem(String label, String actionCommand);
}
