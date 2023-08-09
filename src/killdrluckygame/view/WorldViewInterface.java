package killdrluckygame.view;

import killdrluckygame.ControllerGuiImpl;
import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.World;

import java.awt.event.KeyListener;

public interface WorldViewInterface  {
  void addListener(ControllerGuiInterface listener);

  void startNewGameWithNewWorld();

  void setWorld(ReadOnlyWorldModel world);

  void moveToGame();

  void startNewGameWithSameWorld();

  void refresh(String... args);
}
