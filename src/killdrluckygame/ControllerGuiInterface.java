package killdrluckygame;

public interface ControllerGuiInterface {

 // void playGame();

  void playGame();

  int generateRandomMaxCapacity();
  

  int generateRandomFirstSpace();


  String lookAround();

  String pickItem(String pickedItem);

  String attemptOnTargetCharacter(String itemName);

  void loadNewGame(String worldFileName);

  void resetGame();

  String movePlayerToRoom(String clickedRoom);

  String getPlayerDescription(String currentPlayerName);

  void advanceTargetCharacter();

  String simulateAction(Player currentPlayer);

  String computerPlayerTurn();


  String processInput(String action, String[] parameters);
}
