package killdrluckygame.view;

import javax.swing.JMenuItem;
import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.ReadOnlyWorldModel;


/**
 * The WorldViewInterface interface represents the view component of the Dr. Lucky game.
 * It defines methods for interacting with the game's graphical user interface (GUI)
 * and displaying information to the player.
 */

public interface WorldViewInterface {

  /**
   * Adds a listener to the view to handle GUI events and interactions.
   *
   * @param listener The ControllerGuiInterface listener to be added.
   */
  void addListener(ControllerGuiInterface listener);

  /**
   * Starts a new game with a new world specification.
   */
  void startNewGameWithNewWorld();

  /**
   * Sets the world model to be displayed in the view.
   *
   * @param world The ReadOnlyWorldModel representing the game world.
   */
  void setWorld(ReadOnlyWorldModel world);

  /**
   * Navigates to the new game screen.
   */
  void moveToGame();

  /**
   * Starts a new game with the same world specification as the current game.
   */
  void startNewGameWithSameWorld();

  /**
   * Refreshes the view with updated information.
   *
   * @param args Additional arguments for refreshing the view.
   */
  void refresh(String... args);

  /**
   * Handles the end of the game and performs necessary actions.
   */
  void gameEnd();

  /**
   * Creates the menu for display
   * @param label label to execute
   * @param actionCommand action command to perform
   * @return return the jmenu object
   */
  JMenuItem createMenuItem(String label, String actionCommand);

  /**
   * Listen to keys pressed.
   */
  void initializeKeyListeners();

  /**
   * Sets the main view as visible.
   */
  void setVisibleMain();


  /**
   * Sets the about dialog as visible.
   */
  void setVisibleAboutDialog();

  /**
   * Displays a message dialog with the specified title and message.
   *
   * @param title   The title of the message dialog.
   * @param message The message to be displayed.
   */
  void displayMessageDialog(String title, String message);


  /**
   * Displays an error dialog with the specified title and message.
   *
   * @param title   The title of the error dialog.
   * @param message The error message to be displayed.
   */
  void displayErrorDialog(String title, String message);
}
