package killdrluckygame;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import killdrluckygame.commands.*;
import killdrluckygame.view.WorldViewInterface;

public class DummyController implements ControllerGuiInterface {
  private World worldModel;
  private WorldViewInterface worldView;
  private int maxTurns;
  private CustomRandomInterface random;
  private final Map<String, Function<Scanner, GameOperationCommand>> supportedOperations;
  private String filePath;

  public DummyController(CustomRandomInterface random,
                         World worldModel, WorldViewInterface worldView, int maxTurns, String filePath) {
    this.worldModel = worldModel;
    this.worldView = worldView;
    this.random = random;
    this.maxTurns = maxTurns;
    this.filePath = filePath;
    this.supportedOperations = new HashMap<>();
    worldView.addListener(this);
    initialise();
  }


  private GameOperationCommand processAddHumanPlayerAction(Scanner sc) {
    GameOperationCommand command = null;
    try {
      String name = sc.nextLine();
      int maxTurns = Integer.parseInt(sc.nextLine());
      String roomName = sc.nextLine();
      command = new AddHumanPlayerCommand(worldModel, name, maxTurns,
              roomName, System.out);
    } catch (NoSuchElementException ex) {
      throw new IllegalArgumentException("Enter valid values!");
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Enter a valid value for max capacity!");
    }
    return command;
  }

  private GameOperationCommand processAddComputerPlayerAction(Scanner sc) {
    int maxCapacity = generateRandomMaxCapacity();
    int spaceRandomIndex = generateRandomFirstSpace();
    GameOperationCommand command = new AddComputerPlayerCommand(worldModel, System.out,
            maxCapacity, spaceRandomIndex);
    return command;
  }

  private GameOperationCommand processDisplayPlayerInformation(Scanner sc) {
    GameOperationCommand command = new DisplayPlayerInformationCommand(worldModel, sc.nextLine(),
            System.out);
    return command;
  }

  private GameOperationCommand processMoveHumanPlayer(Scanner sc) {
    GameOperationCommand command = new MovePlayerCommand(worldModel, sc.nextLine(), System.out);
    return command;
  }

  private GameOperationCommand processPickItem(Scanner sc) {
    GameOperationCommand command = new PickItemCommand(worldModel, sc.nextLine(), System.out,
            true);
    return command;
  }

  private GameOperationCommand processAttack(Scanner sc) {
    GameOperationCommand command = new AttackPlayerCommand(worldModel, sc.nextLine(), System.out);
    return command;
  }

  private GameOperationCommand processLookAround(Scanner sc) {
    GameOperationCommand command = new LookAroundCommand(worldModel, System.out);
    return command;
  }


  private void initialise() {

    this.supportedOperations.put("human", (Scanner sc) -> processAddHumanPlayerAction(sc));

    this.supportedOperations.put("computer", (Scanner sc) -> processAddComputerPlayerAction(sc));

    this.supportedOperations.put("playerinfo", (Scanner sc) -> processDisplayPlayerInformation(sc));

    this.supportedOperations.put("move", (Scanner sc) -> processMoveHumanPlayer(sc));

    this.supportedOperations.put("pickitem", (Scanner sc) -> processPickItem(sc));

    this.supportedOperations.put("attack", (Scanner sc) -> processAttack(sc));

    this.supportedOperations.put("lookaround", (Scanner sc) -> processLookAround(sc));
  }

  @Override
  public void playGame() {

    // TODO make the controller initialise or start the view according to the MVC principles.
    worldView.setVisibleAboutDialog();
    worldView.setVisibleMain();
  }

  @Override
  public void loadNewGame(String worldFileName, int maxTurns) {
    this.maxTurns = maxTurns;
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
    Player currentPlayer = worldModel.getCurrentPlayer();
    if (currentPlayer.isComputerControlled()) {
      result = simulateAction(currentPlayer);
      System.out.println(result);
      advanceTargetCharacter();
      return result;
    }

    return result;
  }

  @Override
  public String processInput(String action, String[] parameters) {

    if (!worldModel.hasGameEnded(this.maxTurns)) {
      try {
        String additionalParameters = String.join("\n", parameters);
        Scanner sc = new Scanner(additionalParameters);

        GameOperationCommand command = this.supportedOperations.get(action).apply(sc);
        return command.execute();
      } catch (IllegalArgumentException ex) {
        worldView.displayErrorDialog("ERROR", ex.getMessage());
      }
    } else {
      // end the game
      worldView.gameEnd();
    }
    return "";
  }

  @Override
  public boolean isValidMove(Player currentPlayer, Space clickedRoom) {
    Space currentPlayerSpace = worldModel.getCurrentPlayerSpace(currentPlayer);
    List<String> neighNames = worldModel.getNeighborsStrings();
    if (worldModel.isContainsNeighbor(clickedRoom.getSpaceName(), neighNames)) {
      return true;
    }

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

