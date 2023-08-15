package test;

import javax.swing.JMenuItem;
import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.view.WorldViewInterface;

/**
 * A mock implementation of the WorldViewInterface for testing purposes.
 */
public class MockView implements WorldViewInterface {

  private StringBuilder viewLog;


  /**
   * Constructs a MockView with the specified view log.
   *
   * @param viewLog The StringBuilder to store log information.
   */
  public MockView(StringBuilder viewLog) {
    this.viewLog = viewLog;
  }
  /**
   * Adds a listener to the view to handle GUI events and interactions.
   *
   * @param listener The ControllerGuiInterface listener to be added.
   */

  @Override
  public void addListener(ControllerGuiInterface listener) {

  }

  /**
   * Starts a new game with a new world specification.
   */
  @Override
  public void startNewGameWithNewWorld() {

  }

  /**
   * Sets the world model to be displayed in the view.
   *
   * @param world The ReadOnlyWorldModel representing the game world.
   */
  @Override
  public void setWorld(ReadOnlyWorldModel world) {

  }

  /**
   * Navigates to the new game screen.
   */
  @Override
  public void moveToGame() {

  }

  /**
   * Starts a new game with the same world specification as the current game.
   */
  @Override
  public void startNewGameWithSameWorld() {

  }

  /**
   * Refreshes the view with updated information.
   *
   * @param args Additional arguments for refreshing the view.
   */
  @Override
  public void refresh(String... args) {

  }

  /**
   * Handles the end of the game and performs necessary actions.
   */
  @Override
  public void gameEnd() {

  }

  @Override
  public JMenuItem createMenuItem(String label, String actionCommand) {
    return null;
  }

  /**
   * Sets the main view as visible.
   */
  @Override
  public void setVisibleMain() {
    viewLog.append("setting the view main panel visible");
  }

  /**
   * Sets the about dialog as visible.
   */
  @Override
  public void setVisibleAboutDialog() {
    viewLog.append("setting the about dialog panel visible");
  }

  /**
   * Displays a message dialog with the specified title and message.
   *
   * @param title   The title of the message dialog.
   * @param message The message to be displayed.
   */
  @Override
  public void displayMessageDialog(String title, String message) {

  }

  /**
   * Displays an error dialog with the specified title and message.
   *
   * @param title   The title of the error dialog.
   * @param message The error message to be displayed.
   */
  @Override
  public void displayErrorDialog(String title, String message) {

  }

  @Override
  public void initializeKeyListeners() {

  }
}
