package killdrluckygame;

import killdrluckygame.view.WorldViewInterface;

public interface ControllerGuiInterface {

 // void playGame();

  void playGame();

  void processHumanUserInfoClick(String name, int maxCapacity, String nameOfRoom);

  int generateRandomMaxCapacity();
  

  int generateRandomFirstSpace();

  void processComputerClick();
}
