package killdrluckygame;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import killdrluckygame.commands.AddComputerPlayerCommand;
import killdrluckygame.commands.AddHumanPlayerCommand;
import killdrluckygame.commands.AttackPlayerCommand;
import killdrluckygame.commands.DisplayPlayerInformationCommand;
import killdrluckygame.commands.GameOperationCommand;
import killdrluckygame.commands.LookAroundCommand;
import killdrluckygame.commands.MovePlayerCommand;
import killdrluckygame.commands.PickItemCommand;
import killdrluckygame.view.WorldViewInterface;

public class DummyController implements ControllerGuiInterface {
  private World worldModel;
  private WorldViewInterface worldView;
  private final int maxTurns;
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

  private void initialise() {

    this.supportedOperations.put("human", (Scanner sc) -> {
      GameOperationCommand command = new AddHumanPlayerCommand(worldModel, sc.nextLine(),
              Integer.parseInt(sc.nextLine()),
              sc.nextLine(), System.out);
      return command;

    });

    this.supportedOperations.put("computer", (Scanner sc) -> {

      int maxCapacity = generateRandomMaxCapacity();
      int spaceRandomIndex = generateRandomFirstSpace();
      GameOperationCommand command = new AddComputerPlayerCommand(worldModel, System.out,
              maxCapacity, spaceRandomIndex);
      return command;
    });

    this.supportedOperations.put("playerinfo", (Scanner sc) -> {
      GameOperationCommand command = new DisplayPlayerInformationCommand(worldModel, sc.nextLine(),
              System.out);
      return command;
    });

    this.supportedOperations.put("move", (Scanner sc) -> {
      GameOperationCommand command = new MovePlayerCommand(worldModel, sc.nextLine(), System.out);
      return command;
    });


    this.supportedOperations.put("pickitem", (Scanner sc) -> {
      GameOperationCommand command = new PickItemCommand(worldModel, sc.nextLine(), System.out,
              true);
      return command;
    });

    this.supportedOperations.put("attack", (Scanner sc) -> {
      GameOperationCommand command = new AttackPlayerCommand(worldModel, sc.nextLine(), System.out);
      return command;
    });

    this.supportedOperations.put("lookaround", (Scanner sc) -> {
      GameOperationCommand command = new LookAroundCommand(worldModel, System.out);
      return command;
    });

  }

  @Override
  public void playGame() {

    // TODO make the controller initialise or start the view according to the MVC principles.
    worldView.setVisibleAboutDialog();
    worldView.setVisibleMain();
  }

  @Override
  public void loadNewGame(String worldFileName) {
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
    if (worldModel.getCurrentPlayer().isComputerControlled()) {
      result = simulateAction(worldModel.getCurrentPlayer());
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
//        if (action.toLowerCase().equals("playerinfo")) {
//          GameOperationCommand command = new DisplayPlayerInformationCommand
//                  (worldModel,sc.nextLine(), System.out);
//          return  command.execute();
////          return worldModel.getPlayerDescriptionFromUsername(sc.nextLine());
//        } else if (action.toLowerCase().equals("pickitem")) {
//          String[] pick = additionalParameters.split("\n");
//          GameOperationCommand command = new PickItemCommand(worldModel, pick[0], System.out, true);
//          return command.execute();
////          return pickItem(pick[0]);
//        } else if (action.toLowerCase().equals("attack")) {
//          String[] attack = additionalParameters.split("\n");
//          GameOperationCommand command = new AttackPlayerCommand(worldModel, attack[0],System.out);
//          return command.execute();
//        } else if (action.toLowerCase().equals("move")) {
//          String[] move = additionalParameters.split("\n");
//          GameOperationCommand command = new MovePlayerCommand(worldModel,move[0],System.out);
//          return command.execute();
//        } else if (action.toLowerCase().equals("lookaround")) {
//          GameOperationCommand command = new LookAroundCommand(worldModel,System.out);
//          return command.execute();
//        } else {
        GameOperationCommand command = this.supportedOperations.get(action).apply(sc);
        return command.execute();
//        }
      } catch (Exception ex) {
          System.out.println(ex.getMessage());
      }
    } else {
      // end the game
      worldView.gameEnd();
    }
    return "";
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

