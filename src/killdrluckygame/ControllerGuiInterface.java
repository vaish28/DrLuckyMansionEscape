package killdrluckygame;

public interface ControllerGuiInterface {

 // void playGame();

  void playGame();

  int generateRandomMaxCapacity();

  int generateRandomFirstSpace();

  void loadNewGame(String worldFileName);

  void resetGame();

  void advanceTargetCharacter();

  String simulateAction(Player currentPlayer);

  String computerPlayerTurn();


  String processInput(String action, String[] parameters);
}
