package killdrluckygame;

public interface ControllerGuiInterface {

 // void playGame();

  void playGame();

  void processHumanUserInfoClick(String name, int maxCapacity, String nameOfRoom);

  int generateRandomMaxCapacity();
  

  int generateRandomFirstSpace();

  void processComputerClick();

  String lookAround();

  String pickItem(String pickedItem);

  String attemptOnTargetCharacter(String itemName);

  void loadNewGame(String worldFileName);

  void resetGame();

  String movePlayerToRoom(Player currentPlayer, Space clickedRoom);

  String getPlayerDescription(String currentPlayerName);

  void advanceTargetCharacter();

  String simulateAction(Player currentPlayer);

  String computerPlayerTurn();


  String processInput(String action, String[] parameters);
}
