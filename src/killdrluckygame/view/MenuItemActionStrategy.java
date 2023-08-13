package killdrluckygame.view;

/**
 * The MenuItemActionStrategy interface represents a strategy for executing an action associated
 * with a menu item.
 * Implementing classes define the specific action to be taken when the menu item is triggered.
 */
public interface MenuItemActionStrategy {

  /**
   * Executes the action associated with the menu item.
   * Implementing classes should define the specific behavior to be performed.
   */
  void executeAction();
}
