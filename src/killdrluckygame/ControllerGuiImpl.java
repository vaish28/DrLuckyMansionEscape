package killdrluckygame;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import killdrluckygame.commands.AddComputerPlayerCommand;
import killdrluckygame.commands.AddHumanPlayerCommand;
import killdrluckygame.commands.AttackPlayerCommand;
import killdrluckygame.commands.DisplayPlayerInformationCommand;
import killdrluckygame.commands.DisplaySpaceInformationCommand;
import killdrluckygame.commands.GameOperationCommand;
import killdrluckygame.commands.LookAroundCommand;
import killdrluckygame.commands.MovePlayerCommand;
import killdrluckygame.commands.PickItemCommand;
import killdrluckygame.view.WorldViewInterface;


/**
 * The ControllerGuiImpl class implements the ControllerGuiInterface and represents
 * the controller component of the Dr. Lucky game's GUI.
 */
public class ControllerGuiImpl implements ControllerGuiInterface {
  private World worldModel;
  private final Appendable out;
  private WorldViewInterface worldView;
  private int maxTurns;
  private CustomRandomInterface random;
  private final Map<String, Function<Scanner, GameOperationCommand>> supportedOperations;
  private String filePath;


  /**
   * Constructs a ControllerGuiImpl object.
   *
   * @param random     The CustomRandomInterface implementation.
   * @param worldModel The World model of the game.
   * @param worldView  The WorldViewInterface implementation for GUI.
   * @param maxTurns   The maximum number of turns for the game.
   * @param filePath   The file path for game data.
   * @param out        The output stream for displaying game messages.
   */
  public ControllerGuiImpl(CustomRandomInterface random,
                           World worldModel, WorldViewInterface worldView, int maxTurns,
                           String filePath, Appendable out) {
    this.worldModel = worldModel;
    this.worldView = worldView;
    this.random = random;
    this.maxTurns = maxTurns;
    this.filePath = filePath;
    this.out = out;
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

      // Check if the room name exists in the space list
      if (worldModel.getSpaceFromSpaceName(roomName) == null) {
        throw new IllegalArgumentException("Room does not exist: " + roomName);
      }

      command = new AddHumanPlayerCommand(worldModel, name, maxTurns,
              roomName, out);
    } catch (NoSuchElementException ex) {
      throw new NoSuchElementException("Enter valid values!");
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Enter a valid value for max capacity!");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Enter valid room name");
    }
    return command;
  }


  private GameOperationCommand processAddComputerPlayerAction(Scanner sc) {
    int maxCapacity = generateRandomMaxCapacity();
    int spaceRandomIndex = generateRandomFirstSpace();
    GameOperationCommand command = new AddComputerPlayerCommand(worldModel, out,
            maxCapacity, spaceRandomIndex);
    return command;
  }

  private GameOperationCommand processDisplayPlayerInformation(Scanner sc) {
    String playerName = sc.nextLine();
    GameOperationCommand command = new DisplayPlayerInformationCommand(worldModel, playerName,
            out);
    return command;
  }

  private GameOperationCommand processMoveHumanPlayer(Scanner sc) {
    GameOperationCommand command = new MovePlayerCommand(worldModel, sc.nextLine(), out);
    return command;
  }

  private GameOperationCommand processPickItem(Scanner sc) {
    GameOperationCommand command = new PickItemCommand(worldModel, sc.nextLine(), out,
            true);
    return command;
  }

  private GameOperationCommand processAttack(Scanner sc) {
    GameOperationCommand command = new AttackPlayerCommand(worldModel, sc.nextLine(), out);
    return command;
  }

  private GameOperationCommand processLookAround(Scanner sc) {
    GameOperationCommand command = new LookAroundCommand(worldModel, out);
    return command;
  }

  private GameOperationCommand processViewSpaceInformation(Scanner sc) {

    GameOperationCommand command = null;
    try {
      String spaceName = sc.nextLine();
      if (worldModel.getSpaceFromSpaceName(spaceName) == null) {
        throw new IllegalArgumentException("Enter valid space name");
      }
      command = new DisplaySpaceInformationCommand(worldModel, spaceName, out);

    } catch (IllegalArgumentException ex) {
      throw ex;
    }
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

    this.supportedOperations.put("spaceinfo", (Scanner sc) -> processViewSpaceInformation(sc));
  }

  @Override
  public void playGame() {
    worldView.setVisibleAboutDialog();
    worldView.setVisibleMain();
  }


  @Override
  public void loadNewGame(String worldFileName, int maxTurns) {
    this.maxTurns = maxTurns;
    try {
      Readable readable = new FileReader(worldFileName);
      this.worldModel = this.worldModel.reload(readable);
    } catch (IOException e) {
      // Handle file reading or parsing errors
      String.format("An error occurred while reading the file: " + e.getMessage());
      e.printStackTrace();
    }
    worldView.setWorld(worldModel);
  }

  @Override
  public void resetGame() {
    try {
      Readable readable = new FileReader(filePath);
      this.worldModel = this.worldModel.reload(readable);
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
      if (randomValue < moveProbability
              && (worldModel.getPrevActionOfComputer() != ActionType.MOVE)) {

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

  @Override
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
      } catch (NumberFormatException ex) {
        worldView.displayErrorDialog("ERROR", ex.getMessage());
      } catch (IllegalArgumentException ex) {
        worldView.displayErrorDialog("ERROR", ex.getMessage());
      } catch (NoSuchElementException ex) {
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

  @Override
  public boolean checkIfPlayerDescription(Space clickedRoom) {
    Player currentPlayer = worldModel.getCurrentPlayer();
    List<Player> playersInClickedRoom = worldModel.getMappingOfSpaceAndPlayer().get(clickedRoom);

    if (currentPlayer.isHumanControlled()) {
      if (playersInClickedRoom != null && (playersInClickedRoom.contains(currentPlayer))) {
        return true;
      }
    }
    return false;

  }


}

