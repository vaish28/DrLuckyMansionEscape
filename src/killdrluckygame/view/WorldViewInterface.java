package killdrluckygame.view;

import killdrluckygame.ControllerGuiImpl;
import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.World;

import java.awt.*;
import java.awt.event.KeyListener;

public interface WorldViewInterface {
  void addListener(ControllerGuiInterface listener);

  void startNewGameWithNewWorld();

  void setWorld(ReadOnlyWorldModel world);

  void moveToGame();

  void startNewGameWithSameWorld();

  void refresh(String... args);

  void gameEnd();

  void setVisibleMain();

  void setVisibleAboutDialog();

  void displayMessageDialog(String title, String message);

  void displayErrorDialog(String title, String message);
}
