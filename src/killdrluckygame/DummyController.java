package killdrluckygame;

import killdrluckygame.commands.*;
import killdrluckygame.view.WorldViewInterface;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

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
    initializeListeners();
    worldView.addListener(this);
    initialise();
  }

  private void initialise() {

    this.supportedOperations.put("human", (Scanner sc) -> {
    //  validateHumanPlayerInformation(game, sc);
      GameOperationCommand command = new AddHumanPlayerCommand(worldModel, sc.nextLine(), Integer.parseInt(sc.nextLine()),
              sc.nextLine(), System.out);
      //playAfterInput = true;
      return command;

    });

    this.supportedOperations.put("computer", (Scanner sc) -> {

      int maxCapacity = generateRandomMaxCapacity();
      int spaceRandomIndex = generateRandomFirstSpace();
      GameOperationCommand command = new AddComputerPlayerCommand(worldModel, System.out, maxCapacity,
              spaceRandomIndex);
      return command;
    });


    this.supportedOperations.put("playerinfo", (Scanner sc) -> {

    //  String playerName = validatePlayerName(game, sc);
      GameOperationCommand command = new DisplayPlayerInformationCommand(worldModel, sc.nextLine(), System.out);
      return command;
    });


//    this.supportedOperations.put("move", (Scanner sc) -> {
//      GameOperationCommand command = null;
//
//      if (playAfterInput) {
//
//        if (validateMoveInformation(game, out, sc)) {
//          command = new MovePlayerCommand(game, spaceName, out);
//        } else {
//          throw new IllegalArgumentException("Cannot move to this space:! Enter valid space name");
//        }
//      } else {
//        throw new IllegalStateException("Player not added yet!");
//      }
//      return command;
//    });
//
//    this.supportedOperations.put("pickitem", (Scanner sc) -> {
//
//      GameOperationCommand command = null;
//
//      if (playAfterInput) {
//        if (checkSpaceHasItems(game)) {
//          if (validateItemInformation(game, out, sc)) {
//            command = new PickItemCommand(game, itemName, out, true);
//          } else {
//            throw new IllegalArgumentException("Cannot pick this item! Enter valid item name");
//          }
//        } else {
//          command = new PickItemCommand(game, itemName, out, false);
//        }
//
//      } else {
//        throw new IllegalStateException("Player not added yet!");
//      }
//      return command;
//    });
//
//    this.supportedOperations.put("lookaround", (Scanner sc) -> {
//      GameOperationCommand command = null;
//      if (playAfterInput) {
//        if (game.getCurrentPlayer().isHumanControlled()) {
//          command = new LookAroundCommand(game, out);
//        } else {
//          throw new IllegalArgumentException("Cannot look around from current space");
//        }
//      } else {
//        throw new IllegalStateException("Player not added yet");
//      }
//      return command;
//    });
//
//    this.supportedOperations.put("movepet", (Scanner sc) -> {
//      GameOperationCommand command = null;
//      if (playAfterInput) {
//        String spaceName = validPetMoveSpaceName(game, sc);
//        if (spaceName != null) {
//          command = new PetMoveCommand(game, out, spaceName);
//        } else {
//          throw new IllegalArgumentException("Cannot move the pet");
//        }
//      } else {
//        throw new IllegalStateException("Player not added successfully");
//      }
//      return command;
//    });
//    this.supportedOperations.put("attack", (Scanner sc) -> {
//      GameOperationCommand command = null;
//      if (playAfterInput) {
//        if (game.getCurrentPlayer().isHumanControlled()) {
//          if (game.getCurrentPlayer().getItems().size() != 0) {
//            String itemToAttack = attackByItem(game, sc);
//            if (itemToAttack != null) {
//              command = new AttackPlayerCommand(game, itemToAttack, out);
//            } else {
//              throw new IllegalArgumentException("Illegal Argument Exception");
//            }
//          } else {
//            command = new AttackPlayerCommand(game, "", out);
//          }
//        }
//
//      } else {
//        throw new IllegalStateException("Player not added ");
//      }
//      return command;
//    });

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

  public void processHumanUserInfoClick(String name, int maxCapacity, String roomName) {


    worldModel.addHumanPlayer(name, maxCapacity, roomName);

    Map<Space, List<Player>> mapping = worldModel.getMappingOfSpaceAndPlayer();

    if (mapping != null) {
      // Iterate through the mapping and display the details
      for (Map.Entry<Space, List<Player>> entry : mapping.entrySet()) {
        Space space = entry.getKey();
        List<Player> players = entry.getValue();

        // Iterate through the list of players for the current space
        for (Player currentplayer : players) {
          System.out.println("Space: " + space.getSpaceName() + " | Player: " +
                  currentplayer.getName());
        }
      }
    }
  }


  public void processComputerClick() {
    // Do something with the user input, e.g., create a new player with the provided data
    // and add it to the model.
    // Example:
    int maxCapacity = generateRandomMaxCapacity();
    int spaceRandomIndex = generateRandomFirstSpace();
    worldModel.addComputerPlayer(maxCapacity,spaceRandomIndex);


    Map<Space, List<Player>> mapping = worldModel.getMappingOfSpaceAndPlayer();

    if (mapping != null) {
      // Iterate through the mapping and display the details
      for (Map.Entry<Space, List<Player>> entry : mapping.entrySet()) {
        Space space = entry.getKey();
        List<Player> players = entry.getValue();

        // Iterate through the list of players for the current space
        for (Player currentplayer : players) {
          System.out.println("Space: " + space.getSpaceName() + " | Player: " +
                  currentplayer.getName());
        }
      }
    }
  }

  @Override
  public String pickItem(String pickedItem) {
    if(worldModel!=null) {
      if(worldModel.pickItem(pickedItem)) {
        worldModel.moveTargetCharacter();
        worldModel.nextTurn();
        return "Item picked successfully";
      }
      else {
        return "Item not picked successfully";
      }
    }

    return "";
  }

  @Override
  public String lookAround() {
    if(worldModel!=null) {
      if(worldModel.lookAround()!=null) {
        worldModel.moveTargetCharacter();
        worldModel.nextTurn();
        return worldModel.lookAround();
      }
      else {
        return "Look around not successful";
      }
    }
    return "";
  }

  @Override
  public String attemptOnTargetCharacter(String itemName) {
    if(worldModel!=null) {
      if(worldModel.attackHuman(itemName)) {
        worldModel.moveTargetCharacter();
        worldModel.nextTurn();
        return "Attack successful";
      }
      else {
        return "Attack not successful! Target Character not in same room";
      }
    }
    return "";
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
  public String movePlayerToRoom(Player currentPlayer, Space clickedRoom) {
    if(worldModel.move(clickedRoom.getSpaceName())){
      worldModel.moveTargetCharacter();
      worldModel.nextTurn();
      return "moved successfully";
    }

    return "move not successful";
  }

  @Override
  public String getPlayerDescription(String currentPlayerName) {
    return worldModel.getPlayerDescriptionFromUsername(currentPlayerName);
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
    String result="";
    if(worldModel.getCurrentPlayer().isComputerControlled()) {
      result = simulateAction(worldModel.getCurrentPlayer());
      System.out.println(result);
    }
    advanceTargetCharacter();
    return result;
  }

  @Override
  public String processInput(String action, String[] parameters) {
    if (!worldModel.hasGameEnded(this.maxTurns)) {
      try {
        String additionalParameters = String.join("\n", parameters);
        Scanner sc = new Scanner(additionalParameters);
        if (action.toLowerCase().equals("playerinfo")) {
          return worldModel.getPlayerDescriptionFromUsername(sc.nextLine());
        } else {
          GameOperationCommand command = this.supportedOperations.get(action).apply(sc);
          command.execute();
        }
      } catch (Exception ex) {

      }
    } else {
      // end the game
    }
    return  "";
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

