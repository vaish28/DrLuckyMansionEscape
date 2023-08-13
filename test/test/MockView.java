package test;

import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.view.WorldViewInterface;

public class MockView implements WorldViewInterface {

  private StringBuilder viewLog;


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

  /**
   * Sets the main view as visible.
   */
  @Override
  public void setVisibleMain() {

  }

  /**
   * Sets the about dialog as visible.
   */
  @Override
  public void setVisibleAboutDialog() {

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
}