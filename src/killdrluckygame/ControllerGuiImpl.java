package killdrluckygame;

import killdrluckygame.view.WorldViewInterface;
import java.io.FileReader;
import java.io.IOException;


public class ControllerGuiImpl implements ControllerGuiInterface {
  private World worldModel;
  private WorldViewInterface worldView;
  private final int maxTurns;
  private CustomRandomInterface random;
  private String filePath;

  public ControllerGuiImpl(CustomRandomInterface random,
                           World worldModel, WorldViewInterface worldView,
                           int maxTurns, String filePath) {
    this.worldModel = worldModel;
    this.worldView = worldView;
    this.random = random;
    this.maxTurns = maxTurns;
    this.filePath = filePath;
    initializeListeners();
    worldView.addListener(this);
  }


  @Override
  public void playGame() {

  }

  private void initializeListeners() {

  }

  // Implement methods to handle different user actions based on the GUI interactions
  // For example:
  private void moveButtonClicked() {
    // Logic to handle moving the player in the world
    // Call methods from the game model to update the player's position
    // Update the GUI to reflect the changes in the world

  }

  private void pickUpButtonClicked() {
    // Logic to handle picking up an item in the world
    // Call methods from the game model to pick up the item
    // Update the GUI to reflect the changes in the world
  }

  private void handleMouseClick(int x, int y) {
    // Logic to handle mouse click events
    // Determine the action based on the clicked position in the world
    // Call methods from the game model to update the game state
    // Update the GUI to reflect the changes in the world
  }

  private void handleKeyPress(int keyCode) {
    // Logic to handle key press events
    // Determine the action based on the pressed key
    // Call methods from the game model to update the game state
    // Update the GUI to reflect the changes in the world
  }


  @Override
  public void loadNewGame(String worldFileName, int numberOfTurns) {
    try {
      this.worldModel = new DrLuckyWorld.Input().readInput(new FileReader(worldFileName));
    } catch (IOException e) {
      // Handle file reading or parsing errors
      String.format("An error occurred while reading the file: " + e.getMessage());
      e.printStackTrace();
    }
    worldView.setWorld(worldModel);
  }

  public void resetGame() {

    try {
      this.worldModel = new DrLuckyWorld.Input().readInput(new FileReader(filePath));
    } catch (IOException e) {
      String.format(e.getMessage());
    }
    worldView.setWorld(worldModel);
  }


  @Override
  public void advanceTargetCharacter() {
    worldModel.moveTargetCharacter();
    worldModel.nextTurn();
  }


  @Override
  public int generateRandomMaxCapacity() {
    return random.nextInt(1, 4);
  }

  @Override
  public int generateRandomFirstSpace() {
    return random.nextInt(worldModel.getSpaces().size());
  }

  @Override
  public String simulateAction(Player currentPlayer) {

    worldModel.increaseNumberOfTurns();
    double moveProbability = 0.4;
    double pickUpProbability = 0.3;
    double lookAroundProbability = 0.5;
    double randomValue = random.nextDouble();
    StringBuilder sb = new StringBuilder();
    String result = "";

    if (worldModel.checkIfTargetCharacterInSameSpace(currentPlayer)) {

      worldModel.changePrevAction(ActionType.ATTACK);
      sb.append("Performing attack");
      worldModel.performComputerAttack();
      result = sb.toString();

    } else {
      if (randomValue < moveProbability && (worldModel.getPrevActionOfComputer() != ActionType.MOVE)) {

        worldModel.changePrevAction(ActionType.MOVE);
        sb.append("Performing move");
        result = performRandomAction(ActionType.MOVE, currentPlayer, worldModel);
        sb.append(result);
        result = sb.toString();

      } else if (randomValue < moveProbability + pickUpProbability
              && (worldModel.getPrevActionOfComputer() != ActionType.PICKUP_ITEM)) {

        sb.append("Performing pick");
        worldModel.changePrevAction(ActionType.PICKUP_ITEM);
        result = performRandomAction(ActionType.PICKUP_ITEM, currentPlayer, worldModel);
        sb.append(result);
        result = sb.toString();

      } else if (randomValue < (moveProbability + pickUpProbability + lookAroundProbability)
              && (worldModel.getPrevActionOfComputer() != ActionType.LOOK_AROUND)) {

        sb.append("Performing lookaround");
        worldModel.changePrevAction(ActionType.LOOK_AROUND);
        result = performRandomAction(ActionType.LOOK_AROUND, currentPlayer, worldModel);
        sb.append(result);
        result = sb.toString();

      }
    }

    return result;
  }

  public String computerPlayerTurn() {
    String result = "";
    if (worldModel.getCurrentPlayer().isComputerControlled()) {
      result = simulateAction(worldModel.getCurrentPlayer());
      System.out.println(result);
    }
    advanceTargetCharacter();
    return result;
  }

  @Override
  public String processInput(String action, String[] parameters) {
    return null;
  }

  @Override
  public boolean isValidMove(Player currentPlayer, Space clickedRoom) {
    return false;
  }

  @Override
  public boolean checkIfPlayerDescription(Space clickedRoom) {
    return false;
  }


  private String performRandomAction(ActionType action, Player player, World game) {
    String result = "";
    switch (action) {
      case MOVE:
        // Evaluate the desirability of moving to a neighboring space
        result = game.evaluateNeighboringScores(game.getCurrentPlayerSpace(player), player);

        break;
      case PICKUP_ITEM:
        // Evaluate the desirability of picking up an item
        result = game.evaluatePickItem(game.getCurrentPlayerSpace(player).getItems(), player);

        break;
      case LOOK_AROUND:
        // Evaluate the desirability of looking around
        result = game.evaluateLookAround(game.getCurrentPlayerSpace(player), player);
        break;
      default:
        break;
    }

    return result;
  }


}

